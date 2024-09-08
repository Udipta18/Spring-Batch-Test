package com.example.batch.springBatch;

import com.example.batch.springBatch.config.JobService;
import com.example.batch.springBatch.domain.Coffee;
import com.example.batch.springBatch.listner.CoffeeProcessorListener;
import com.example.batch.springBatch.processor.CoffeeProcessor;
import com.example.batch.springBatch.repos.CoffeeRepo;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest
//@SpringBootTest(classes = {JobService.class, EntityDataConfig.class, CoffeeProcessor.class,CoffeeProcessorListener.class})
@TestPropertySource(properties = {
        "file.input=coffee-list-test.csv",
        "spring.batch.job.enabled=false"
})
@ComponentScan(basePackages = {"com.example.batch.springBatch.repos", "com.example.batch.springBatch.listeners"})
@ContextConfiguration(classes = {BatchTestConfig.class,JobService.class,CoffeeProcessor.class,CoffeeProcessorListener.class,PostGresConfig.class,
CoffeeRepo.class,Coffee.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BatchTest1 {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

//    @Mock
//    JobLauncher jobLauncher;
//    @Mock
//    JobRepository jobRepository;

//    @Autowired
//    JobRepository jobRepository;
//
//    @Mock
//    PlatformTransactionManager transactionManager;
//    @Mock
//    CoffeeProcessor itemProcessor;
//    @Mock
//    CoffeeProcessorListener coffeeProcessorListener;
////    @Autowired
////    EntityManagerFactory entityManagerFactory;
//
//    @Mock
//    LocalContainerEntityManagerFactoryBean postgresEntityManager;
//
//    @InjectMocks
//    JobService jobService;

    @Autowired
    JobService jobService;


    @Autowired
    private EntityManagerFactory entityManagerFactory;

//    @Autowired
//    LocalContainerEntityManagerFactoryBean h2Test;

    JobParameters jobParameters;



    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
         jobParameters = new JobParametersBuilder()
                .addString("file.input", "coffee-list-test.csv")
                .toJobParameters();

        //JobService spy = spy(new JobService());

        ReflectionTestUtils.setField(jobService, "fileInput", "coffee-list.csv");

        Job importUserJob =(Job) ReflectionTestUtils.invokeMethod(jobService, "importUserJob");

        jobLauncherTestUtils.setJob(importUserJob);
       // when(postgresEntityManager.getObject()).thenReturn(entityManagerFactory);
    }

    @Test
    void testJobExecution() throws Exception {
        // Arrange

        // Act
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Assert
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    void testJobStep() throws Exception {
        // Act
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");

        // Assert
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    void testReader() throws Exception {
        // Arrange
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

        FlatFileItemReader<Coffee> reader = (FlatFileItemReader<Coffee>) stepExecution
                .getExecutionContext()
                .get("coffeeItemReader");

        // Act & Assert
        assertNotNull(reader);
        reader.open(new ExecutionContext());

        Coffee coffee = reader.read();
        assertNotNull(coffee);
        assertEquals("Brand1", coffee.getBrand());
        assertEquals("Origin1", coffee.getOrigin());
        assertEquals("Characteristics1", coffee.getCharacteristics());
    }

    @Test
    void testProcessorAndWriter() throws Exception {
        // Arrange
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

        // Assert
        assertTrue(stepExecution.getReadCount() > 0);
        //assertEquals(stepExecution.getReadCount(), stepExecution.getWriteCount());
        assertEquals(0, stepExecution.getProcessSkipCount());
       // assertEquals(0, stepExecution.getWriteSkipCount());
    }
}
