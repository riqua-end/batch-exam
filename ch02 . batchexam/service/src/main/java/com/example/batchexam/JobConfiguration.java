package com.example.batchexam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
@Slf4j
public class JobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job-chunk", jobRepository)
                .start(step)
                .build()
                ;
    }

    @Bean
    @JobScope // Bean 생성보다 주입이 늦기 때문에 JobScope 로 지연시켜서 주입
    public Step step (
            JobRepository jobRepository ,
            PlatformTransactionManager platformTransactionManager,
            @Value("#{jobParameters['name']}") String name
    ) {
        log.info("name : {} ", name);
        return new StepBuilder("step", jobRepository)
                .tasklet((a,b)-> RepeatStatus.FINISHED, platformTransactionManager)
                .build();
    }

    /*@Bean
    public Step step (JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){

        return new StepBuilder("step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step 실행");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .allowStartIfComplete(true)
                .startLimit(5)
                .build();
    }*/

    /*@Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        final Tasklet tasklet = new Tasklet() {

            private int count = 0;

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                count++;
                if (count == 15){
                    log.info("Tasklet FINISHED");
                    return RepeatStatus.FINISHED;
                }
                log.info("Tasklet CONTINUABLE {}", count);
                return RepeatStatus.CONTINUABLE;
            }
        };

        return new StepBuilder("step", jobRepository)
                .tasklet(tasklet, platformTransactionManager)
                .build();
    }*/

    /*@Bean
    public Step step (JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){

        final ItemReader<Integer> itemReader = new ItemReader<>() {

            private int count = 0;

            @Override
            public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                count++;

                log.info("Read {}", count);

                if (count == 20) {
                    return null;
                }

                *//*if (count >= 15) {
                    throw new IllegalStateException("예외가 발생했어요");
                }*//*

                return count;
            }

        };

        final ItemProcessor<Integer, Integer> itemProcessor = new ItemProcessor<>() {
            @Override
            public Integer process(Integer item) throws Exception {

                if (item == 15){
                    throw new IllegalStateException();
                }

                return item;
            }
        };

        return new StepBuilder("step", jobRepository)
                .<Integer, Integer>chunk(10, platformTransactionManager) // chunk 기반으로 진행되면 commit 횟수는 2번이 됨
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(read -> {})

                .faultTolerant()
//                .skip(IllegalStateException.class)
//                .skipLimit(3) // 3번 까지는 skip 되나 그 이후 부터는 Exception 에러가 뜨고 rollback 됨
//                .skipPolicy((t, skipCount) -> t instanceof IllegalStateException && skipCount < 5) // 5번의 skip count 가 넘어가서 rollback
//                .noRollback(IllegalStateException.class)
                .retry(IllegalStateException.class)
                .retryLimit(5)
                .build()
                ;
    }*/

    /**
     * 첫 번째 시도: item이 15일 때 IllegalStateException이 발생하여 롤백 (1번째 롤백).
     * 첫 번째 재시도: 다시 시도하지만 예외 발생, 롤백 (2번째 롤백).
     * 두 번째 재시도: 다시 시도하지만 예외 발생, 롤백 (3번째 롤백).
     * 세 번째 재시도: 다시 시도하지만 예외 발생, 롤백 (4번째 롤백).
     * 네 번째 재시도: 다시 시도하지만 예외 발생, 롤백 (5번째 롤백).
     * 다섯 번째 재시도: 마지막 재시도에서 예외 발생, 롤백 (6번째 롤백).
     *
     * 따라서 rollback count 는 6이 된다.
     */
}
