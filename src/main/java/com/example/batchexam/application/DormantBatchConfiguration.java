package com.example.batchexam.application;

import com.example.batchexam.batch.SimpleTasklet;
import com.example.batchexam.customer.Customer;
import com.example.batchexam.dormant.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(
            DormantBatchItemReader itemReader,
            DormantBatchItemProcessor itemProcessor,
            DormantBatchItemWriter itemWriter,
            DormantBatchJobExecutionListener dormantBatchJobExecutionListener
    ){
        return Job.builder()
                .itemReader(itemReader)
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .jobExecutionListener(dormantBatchJobExecutionListener)
                .build();
    }
}
