package com.example.batchexam.application;

import com.example.batchexam.batch.ItemWriter;
import com.example.batchexam.customer.Customer;
import com.example.batchexam.customer.CustomerRepository;
import com.example.batchexam.email.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void write(Customer item) {
        customerRepository.save(item);

        emailProvider.send(item.getEmail(), "휴면 전환 안내 메일", "ㅎㅇ");
    }
}
