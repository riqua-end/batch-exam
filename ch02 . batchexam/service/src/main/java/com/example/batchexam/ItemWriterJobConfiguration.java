package com.example.batchexam;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class ItemWriterJobConfiguration {

    @Bean
    public Job job (
            JobRepository jobRepository,
            Step step
    ) {
        return new JobBuilder("itemWriterJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step (
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<User> flatFileItemReader,
            ItemWriter<User> jdbcBatchItemWriter
    ) {
        return new StepBuilder("step", jobRepository)
                .<User,User>chunk(2, platformTransactionManager)
                .reader(flatFileItemReader)
                .writer(jdbcBatchItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("users.txt"))
                .linesToSkip(2)
                .delimited().delimiter(",") // 구분자 default "," .delimiter() 를 안써도 됨
                .names("name","age","region","telephone")
                .targetType(User.class)
                .strict(true) // false 는 해당 file 이 없어도 정상적으로 실행, true 는 해당 file 이 없으면 IllegalStateException 발생함
                .build();
    }

    @Bean
    public ItemWriter<User> flatFileItemWriter() {
        return new FlatFileItemWriterBuilder<User>()
                .name("flatFileItemWriter")
                .resource(new PathResource("src/main/resources/new_user.txt"))
                .delimited().delimiter("__")
                .names("name","age","region","telephone")
                .build();
    }

    @Bean
    public ItemWriter<User> fotmattedFlatFileItemWriter() {
        return new FlatFileItemWriterBuilder<User>()
                .name("flatFileItemWriter")
                .resource(new PathResource("src/main/resources/new_formatted_user.txt"))
                .formatted()
                .format("%s의 나이는 %s입니다. 사는곳은 %s, 전화번호는 %s 입니다.")
                .names("name","age","region","telephone")
//                .shouldDeleteIfExists(false) // File already exists
//                .append(true) // 기존 파일에 데이터 추가
//                .shouldDeleteIfEmpty(true) // 내가 작성한게 없으면 파일을 삭제
                .build();
    }

    @Bean
    public JsonFileItemWriter<User> jsonFileItemWriter(){
        return new JsonFileItemWriterBuilder<User>()
                .name("jsonFileItemWriter")
                .resource(new PathResource("src/main/resources/new_user_json.json"))
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .build();
    }

    // 데이터 양이 적을때 사용
    @Bean
    public ItemWriter<User> jpaItemWriter(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    // 데이터 양이 많을때 사용
    @Bean
    public ItemWriter<User> jdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .sql("""
                        insert into
                            user(name, age, region, telephone)
                        values
                            (:name, :age, :region, :telephone)
                        """)
                .beanMapped()
                .build();
    }
}
