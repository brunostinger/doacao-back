package com.cp5a.doacao.service;

import com.cp5a.doacao.dto.eventdto.BasicEventDTO;
import com.cp5a.doacao.dto.transactiondto.DonationDTO;
import com.cp5a.doacao.dto.transactiondto.TransactionDTO;
import com.cp5a.doacao.dto.userdto.UserReceivedDonationDTO;
import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.model.Event;
import com.cp5a.doacao.model.Transaction;
import com.cp5a.doacao.model.TransactionType;
import com.cp5a.doacao.model.User;
import com.cp5a.doacao.repository.TransactionRepository;
import com.cp5a.doacao.security.AuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    TransactionRepository transactionRepository;
    UserService userService;
    EventService eventService;

    public List<TransactionDTO> getTransactions(Integer page, Integer qty){

        Pageable pageable = PageRequest.of(page, qty);
        List<Transaction> transactions = null;

        if(!AuthorizationFilter.getLoggedUserRole().equals("ROLE_ORG"))
            transactions =  transactionRepository.findByFromUserIdOrderByIdDesc(AuthorizationFilter.getLoggedUserId(), pageable).stream().collect(Collectors.toList());
        else
            transactions =  transactionRepository.findByToUserIdOrderByIdDesc(AuthorizationFilter.getLoggedUserId(), pageable).stream().collect(Collectors.toList());

        List<TransactionDTO> transactionsDTO =  new ArrayList<>();

        transactions.forEach(transaction ->{
            TransactionDTO newDto =  new TransactionDTO();

            BeanUtils.copyProperties(transaction, newDto);

            if(transaction.getToUser()!=null){
                newDto.setToUser(new UserReceivedDonationDTO());
                BeanUtils.copyProperties(transaction.getToUser(), newDto.getToUser());
            }
            if(transaction.getToEvent()!=null){
                newDto.setToEvent(new BasicEventDTO());
                BeanUtils.copyProperties(transaction.getToEvent(), newDto.getToEvent());
            }

            if(AuthorizationFilter.getLoggedUserRole().equals("ROLE_ORG"))
                newDto.setBalance(transaction.getToUserBalance());

            transactionsDTO.add(newDto);
        });

        return transactionsDTO;
    }

    public Transaction newDonation(DonationDTO donationDto){
        Integer toUserId = donationDto.getToUser();
        Double value = donationDto.getValue();

        User toUser = userService.getUserById(toUserId);
        if(toUser==null || toUser.getUserStatus().getId()!=1 || toUser.getUserType().getId()!=2)
            throw new FieldValidationException("","A instituição selecionada não é válida");

        Transaction transaction = new Transaction();
        transaction.setValue(value);

        User fromUser = new User();
        fromUser.setId(AuthorizationFilter.getLoggedUserId());

        TransactionType transactionType = new TransactionType();
        transactionType.setId(1);

        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setTransactionType(transactionType);

        User user = userService.getUserById(fromUser.getId());
        transaction.setBalance(user.getBalance() == null ?  Double.valueOf(value) : Double.valueOf(value)+user.getBalance());
        transaction.setToUserBalance(toUser.getBalance() == null ?  Double.valueOf(value) : Double.valueOf(value)+toUser.getBalance());

        transaction = transactionRepository.save(transaction);

        if(transaction!=null){
            userService.updateUserBalance(value, AuthorizationFilter.getLoggedUserId());
            userService.updateUserBalance(value, toUser.getId());
        }
        return transaction;
    }

    public void buyTicket(Integer eventId){

        User fromUser = new User();
        fromUser.setId(AuthorizationFilter.getLoggedUserId());

        Event event = eventService.checkSubscribedEventById(eventId);
        if(event!=null)
            throw new FieldValidationException("","Você já comprou esse ingresso");
        else
            event=null;

        event = eventService.getEventInfo(eventId);
        if(event.getEventStatus().getId()!=1)
            throw new FieldValidationException("","O evento selecionado não está disponível");

        User user = userService.getUserById(fromUser.getId());
        if(user.getBalance()==null)
            user.setBalance(0.0);

        if(event.getValue() > user.getBalance())
            throw new FieldValidationException("","Saldo insuficiente");


        Transaction transaction = new Transaction();
        transaction.setValue(event.getValue());
        TransactionType transactionType = new TransactionType();
        transactionType.setId(2);

        transaction.setFromUser(fromUser);
        transaction.setToEvent(event);
        transaction.setTransactionType(transactionType);

        transaction.setBalance(user.getBalance() - event.getValue());
        transaction = transactionRepository.save(transaction);
        if(transaction!=null){
            userService.updateUserBalance(event.getValue()*-1,AuthorizationFilter.getLoggedUserId());
        }
    }

}
