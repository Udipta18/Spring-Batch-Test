package com.example.batch.springBatch;

import com.example.batch.springBatch.config.JobService;
import com.example.batch.springBatch.listner.CoffeeProcessorListener;
import com.example.batch.springBatch.processor.CoffeeProcessor;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.example.batch.springBatch.config.JobService;
import com.example.batch.springBatch.domain.Coffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest(classes = {JobService.class, TestBatchConfig.class,
        CoffeeProcessor.class, CoffeeProcessorListener.class})
@TestPropertySource(properties = {
        "file.input=coffee-list-test.csv",
        "spring.batch.job.enabled=false"
})
public class BatchTest1 {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJobExecution() throws Exception {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("file.input", "coffee-list-test.csv")
                .toJobParameters();

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
                .get("FlatFileItemReader_1");

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
        assertEquals(stepExecution.getReadCount(), stepExecution.getWriteCount());
        assertEquals(0, stepExecution.getProcessSkipCount());
        assertEquals(0, stepExecution.getWriteSkipCount());
    }
}
