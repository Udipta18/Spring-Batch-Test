package com.example.batch.springBatch;

import com.example.batch.springBatch.config.JobService;
import com.example.batch.springBatch.domain.Coffee;
import com.example.batch.springBatch.listner.CoffeeProcessorListener;
import com.example.batch.springBatch.processor.CoffeeProcessor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class JobTest {



    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CoffeeProcessor itemProcessor;

    @Mock
    private CoffeeProcessorListener coffeeProcessorListener;

    @Mock
    private EntityManagerFactory entityManagerFactory;


    @Mock
    PlatformTransactionManager platformTransactionManager;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jobService, "fileInput", "coffee-list.csv");
    }

    @Test
    void testLaunchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        // Arrange
        JobExecution mockJobExecution = mock(JobExecution.class);
        when(mockJobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(mockJobExecution);

        // Act
        jobService.launchJob();

        // Assert
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void testImportUserJob() {
        // Act
        Job job = (Job) ReflectionTestUtils.invokeMethod(jobService, "importUserJob");

        // Assert
        assertNotNull(job);
        assertEquals("importUserJob", job.getName());
    }

    @Test
    void testStep1() {
        // Act
        Step step = (Step) ReflectionTestUtils.invokeMethod(jobService, "step1");

        // Assert
        assertNotNull(step);
        assertEquals("step1", step.getName());
    }

//    @Test
//    void testWriter() {
//        // Act
//        JpaItemWriter<Coffee> writer = (JpaItemWriter<Coffee>) ReflectionTestUtils.invokeMethod(jobService, "writer");
//
//        // Assert
//        assertNotNull(writer);
//        verify(entityManagerFactory, times(1)).getEntityManagerFactory();
//    }

    @Test
    void testReader() {
        // Act
        FlatFileItemReader<Coffee> reader = (FlatFileItemReader<Coffee>) ReflectionTestUtils.invokeMethod(jobService, "reader");

        // Assert
        assertNotNull(reader);
        assertEquals("coffeeItemReader", reader.getName());
    }
}
