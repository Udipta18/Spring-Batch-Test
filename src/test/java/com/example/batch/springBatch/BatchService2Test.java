//
//package com.example.batch.springBatch;
//
//import com.example.batch.springBatch.config.BatchService2;
//import com.example.batch.springBatch.domain.Coffee;
//import com.example.batch.springBatch.listner.CoffeeProcessorListener;
//import com.example.batch.springBatch.processor.CoffeeProcessor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.batch.core.*;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.JobRepositoryTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBatchTest
//@SpringBootTest
//@ContextConfiguration(classes = {BatchService2.class, BatchTestConfig.class,
//        CoffeeProcessor.class, CoffeeProcessorListener.class})
//@TestPropertySource(properties = {
//        "file.input=coffee-list.csv",
//        "spring.batch.job.enabled=false"
//})
//class BatchService2Test {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    private JobRepositoryTestUtils jobRepositoryTestUtils;
//
//    @BeforeEach
//    void setUp() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }
//
//    @Test
//    void testImportUserJob() throws Exception {
//        // Arrange
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("file.input", "coffee-list-test.csv")
//                .toJobParameters();
//
//        // Act
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        // Assert
//        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//    }
//
//    @Test
//    void testStep1() throws Exception {
//        // Act
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//
//        // Assert
//        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//    }
//
//    @Test
//    void testReader() throws Exception {
//        // Arrange
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//        FlatFileItemReader<Coffee> reader = (FlatFileItemReader<Coffee>) jobLauncherTestUtils.getJobRepository()
//                .getLastStepExecution(jobExecution.getJobInstance(), "step1")
//                .getExecutionContext()
//                .get("reader");
//
//        // Act
//        reader.open(new ExecutionContext());
//        Coffee coffee = reader.read();
//
//        // Assert
//        assertNotNull(coffee);
//        assertEquals("Brand1", coffee.getBrand());
//        assertEquals("Origin1", coffee.getOrigin());
//        assertEquals("Characteristics1", coffee.getCharacteristics());
//    }
//
//    @Test
//    void testProcessor() throws Exception {
//        // This test assumes that your processor modifies the coffee object in some way
//        // Adjust the assertions based on your actual processor logic
//
//        // Arrange
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//
//        // Assert
//        assertTrue(stepExecution.getReadCount() > 0);
//        assertTrue(stepExecution.getWriteCount() > 0);
//        assertEquals(stepExecution.getReadCount(), stepExecution.getWriteCount());
//    }
//
//    @Test
//    void testWriter() throws Exception {
//        // This test assumes that your writer successfully writes to the database
//        // You may need to set up a test database or mock the database operations
//
//        // Arrange
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//
//        // Assert
//        assertTrue(stepExecution.getWriteCount() > 0);
//        assertEquals(0, stepExecution.getWriteSkipCount());
//    }
//}