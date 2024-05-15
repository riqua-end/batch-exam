package com.example.batchexam.application.step;

import com.example.batchexam.batch.Step;
import com.example.batchexam.batch.StepJob;
import com.example.batchexam.batch.StepJobBuilder;
import com.example.batchexam.batch.Tasklet;
import com.example.batchexam.dormant.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class StepExampleBatchConfiguration {

    @Bean
    public Job stepExampleBatchJob(
            Step step1,
            Step step2,
            Step step3
    ){
        return new StepJobBuilder()
                .start(step1)
                .next(step2)
                .next(step3)
                .build()
                ;
    }

    @Bean
    public Step step1(){
        return new Step(
                () -> System.out.println("step1")
        );
    }
    @Bean
    public Step step2(){
        return new Step(
                () -> System.out.println("step2")
        );
    }
    @Bean
    public Step step3(){
        return new Step(
                () -> System.out.println("step3")
        );
    }
}
