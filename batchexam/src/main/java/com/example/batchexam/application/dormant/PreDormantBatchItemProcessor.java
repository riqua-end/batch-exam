package com.example.batchexam.application.dormant;

import com.example.batchexam.batch.ItemProcessor;
import com.example.batchexam.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PreDormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) {

        final LocalDate targetDate = LocalDate.now()
                .minusDays(365)
                .plusDays(7);

        if (targetDate.equals(customer.getLoginAt().toLocalDate())){
            return customer;
        } else {
            return null;
        }

    }
}
