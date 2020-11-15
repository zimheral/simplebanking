package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.entities.Transaction;
import com.zimheral.simplebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    void processTransaction() {
        //GIVEN
        BigDecimal amount = BigDecimal.TEN;
        Account account = Account.builder().customerId(1L).currentAccount("BE12345").build();
        Transaction transaction = Transaction.builder().amount(amount).account(account).build();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        //WHEN
        transactionService.processTransaction(amount,account);

        //THEN
        verify(transactionRepository).save(transactionCaptor.capture());
        Transaction capturedVal = transactionCaptor.getValue();
        Assertions.assertEquals(transaction.getAmount(), capturedVal.getAmount());
        Assertions.assertEquals(transaction.getAccount().getCurrentAccount(), capturedVal.getAccount().getCurrentAccount());
        Assertions.assertEquals(transaction.getAccount().getCustomerId(), capturedVal.getAccount().getCustomerId());

    }
}