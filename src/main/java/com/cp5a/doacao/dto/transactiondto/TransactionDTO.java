package com.cp5a.doacao.dto.transactiondto;
import com.cp5a.doacao.dto.eventdto.BasicEventDTO;
import com.cp5a.doacao.dto.userdto.UserReceivedDonationDTO;
import com.cp5a.doacao.model.TransactionType;
import lombok.Data;
import java.util.Date;

@Data
public class TransactionDTO {
    private Double value;
    private Date date;
    private UserReceivedDonationDTO toUser;
    private BasicEventDTO toEvent;
    private TransactionType transactionType;
    private Double balance;

}
