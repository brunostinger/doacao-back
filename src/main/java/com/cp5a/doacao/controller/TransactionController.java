package com.cp5a.doacao.controller;

import com.cp5a.doacao.dto.transactiondto.DonationDTO;
import com.cp5a.doacao.dto.transactiondto.TransactionDTO;
import com.cp5a.doacao.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
@Api(tags = {"Endpoint '/transaction'"})
public class TransactionController {

    TransactionService transactionService;

    @ApiOperation(value = "Realiza uma doação para uma instituição")
    @PostMapping("/donation/org")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void newDonation(@RequestBody @Validated DonationDTO donationDto){
        //Validação do cartão não implementada
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        transactionService.newDonation(donationDto);
    }

    @ApiOperation(value = "Realiza a compra de um ingresso para um evento")
    @PostMapping("/ticket/buy/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void buyTicket(@PathVariable Integer id){
        transactionService.buyTicket(id);
    }

    @ApiOperation(value = "Retorna uma lista contendo as  transações efetuadas pelo usuário logado")
    @GetMapping("/list/{page}/{qty}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionDTO> getTransactions(@PathVariable Integer page, @PathVariable Integer qty){
        return transactionService.getTransactions(page, qty);
    }


}
