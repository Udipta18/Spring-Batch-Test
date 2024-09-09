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
import org.springframework.batch.item.database.JdbcCursorItemReader;
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
@ContextConfiguration(classes = {BatchTestConfig.class,JobService.class,CoffeeProcessor.class,CoffeeProcessorListener.class,MysqlConfig.class,
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
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//
//        FlatFileItemReader<Coffee> reader = (FlatFileItemReader<Coffee>) stepExecution
//                .getExecutionContext()
//                .get("coffeeItemReader");
//
//        // Act & Assert
//        assertNotNull(reader);
//        reader.open(new ExecutionContext());
//
//        Coffee coffee = reader.read();
//        assertNotNull(coffee);
//        assertEquals("Brand1", coffee.getBrand());
//        assertEquals("Origin1", coffee.getOrigin());
//        assertEquals("Characteristics1", coffee.getCharacteristics());

//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//
//        // Diagnostic: Print job execution status
//        System.out.println("Job Execution Status: " + jobExecution.getStatus());
//
//        // Diagnostic: Print step executions
//        for (StepExecution execution : jobExecution.getStepExecutions()) {
//            System.out.println("Step Name: " + execution.getStepName());
//            System.out.println("Step Status: " + execution.getStatus());
//            System.out.println("Read Count: " + execution.getReadCount());
//            System.out.println("Write Count: " + execution.getWriteCount());
//        }
//
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//
//        // Diagnostic: Print execution context keys
//        System.out.println("Execution Context Keys: " + stepExecution.getExecutionContext().entrySet());
//
//        Object readerObject = stepExecution.getExecutionContext().get("myEntityReader");
//
//        // Diagnostic: Print reader object
//        System.out.println("Reader Object: " + readerObject);
//
//        // Assert
//        assertNotNull(readerObject, "Reader should not be null");
//        assertTrue(readerObject instanceof JdbcCursorItemReader, "Reader should be an instance of JdbcCursorItemReader");
//
//        JdbcCursorItemReader<Coffee> reader = (JdbcCursorItemReader<Coffee>) readerObject;
//
//        // Act & Assert
//        reader.open(new ExecutionContext());
//
//        Coffee coffee = reader.read();
//        assertNotNull(coffee, "First coffee item should not be null");
//
//        // Clean up
//        reader.close();

        JdbcCursorItemReader<Coffee> reader = jobService.reader();

        // Assert
        assertNotNull(reader, "Reader should not be null");

        // Act & Assert
        reader.open(new ExecutionContext());

        Coffee coffee = reader.read();
        assertNotNull(coffee, "First coffee item should not be null");
        // Add more specific assertions based on your data
        assertNotNull(coffee.getBrand(), "Coffee brand should not be null");
        assertNotNull(coffee.getOrigin(), "Coffee origin should not be null");
        assertNotNull(coffee.getCharacteristics(), "Coffee characteristics should not be null");

        // Test reading multiple items
        for (int i = 0; i < 9; i++) {  // We've already read one, so read 9 more
            Coffee nextCoffee = reader.read();
            assertNotNull(nextCoffee, "Coffee item " + (i+2) + " should not be null");
        }

        // The 11th read should be null, as we've read all 10 items
        assertNull(reader.read(), "11th read should be null, indicating end of data");

        // Clean up
        reader.close();
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
