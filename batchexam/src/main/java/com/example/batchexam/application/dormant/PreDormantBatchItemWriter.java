package com.example.batchexam.application.dormant;

import com.example.batchexam.batch.ItemWriter;
import com.example.batchexam.customer.Customer;
import com.example.batchexam.email.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreDormantBatchItemWriter implements ItemWriter<Customer> {

    private final EmailProvider emailProvider;

    @Autowired
    public PreDormantBatchItemWriter() {
        this.emailProvider = new EmailProvider.Fake();
    }

    public PreDormantBatchItemWriter(EmailProvider emailProvider){
        this.emailProvider = emailProvider;
    }

    @Override
    public void write(Customer customer) {
        emailProvider.send(
                customer.getEmail(),
                "곧 휴면 계정으로 전환이 됩니다.",
                "휴면 계정으로 사용되기를 원하지 않다면 1주일 내에 로그인을 해주세요."
        );
    }
}
