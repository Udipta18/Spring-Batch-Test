package com.example.batch.springBatch.config;

import com.example.batch.springBatch.domain.Coffee;
import com.example.batch.springBatch.listner.CoffeeProcessorListener;
import com.example.batch.springBatch.processor.CoffeeProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.Objects;


@Configuration
@Slf4j
public class JobService {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    @Qualifier("transactionManager")
    PlatformTransactionManager transactionManager;
    @Autowired
    CoffeeProcessor itemProcessor;
    @Autowired
    CoffeeProcessorListener coffeeProcessorListener;
//    @Autowired
//    EntityManagerFactory entityManagerFactory;

    @Autowired
    @Qualifier("postgresEntityManager")
    LocalContainerEntityManagerFactoryBean postgresEntityManager;

    @Value("${file.input}")
    private String fileInput;


    @Scheduled(fixedRate = 120000)
    public void launchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Inside launch job");
        JobExecution jobExecution = jobLauncher
                .run(importUserJob(), new JobParametersBuilder()
                        .addDate("launchDate", new Date()).toJobParameters());
        log.info("JOb Completed with status" + jobExecution.getStatus());

    }

    //@Bean
    private Job importUserJob() {
        log.info("INSIDE JOB BUILDER ");
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

  //  @Bean
    private Step step1() {
        log.info("INSIDE STEP BUILDER ");
        return new StepBuilder("step1", jobRepository)
                .<Coffee, Coffee>chunk(10, transactionManager)
                .reader(reader())
                .processor(itemProcessor)
                .listener(coffeeProcessorListener)
                .writer(writer())
                .faultTolerant().skip(Exception.class).skipLimit(20)
                .build();

    }

   // @Bean
    private JpaItemWriter<Coffee> writer() {
        log.info("INSIDE WRITER ");
        JpaItemWriter<Coffee> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(Objects.requireNonNull(postgresEntityManager.getObject()));
        return writer;
    }

//    @Bean
//    @StepScope
    private FlatFileItemReader<Coffee> reader() {
        log.info("INSIDE READER");
        return new FlatFileItemReaderBuilder<Coffee>().name("coffeeItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[]{"brand", "origin", "characteristics"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Coffee>() {{
                    setTargetType(Coffee.class);
                }})
                .build();
    }


}
