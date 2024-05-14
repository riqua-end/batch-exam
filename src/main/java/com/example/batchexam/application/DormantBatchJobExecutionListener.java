package com.example.batchexam.application;

import com.example.batchexam.batch.JobExecution;
import com.example.batchexam.batch.JobExecutionListener;
import com.example.batchexam.email.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchJobExecutionListener implements JobExecutionListener {

    private final EmailProvider emailProvider;

    public DormantBatchJobExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // 비지니스 로직
        emailProvider.send(
                "admin@admin.com",
                "배치 완료 알림",
                "Dormant BatchJob 수행 완료. status : " + jobExecution.getStatus()
        );
    }
}
