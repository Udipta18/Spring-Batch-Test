package com.example.batch.springBatch;

import com.example.batch.springBatch.config.JobService;
import com.example.batch.springBatch.domain.Coffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestReader {

    @InjectMocks
    private JobService jobService;

    @Mock
    private LocalContainerEntityManagerFactoryBean postgresEntityManager;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(postgresEntityManager.getDataSource()).thenReturn(dataSource);
    }

    @Test
    void testReader() {
        // Act
        JdbcCursorItemReader<Coffee> reader = jobService.reader();

        // Assert
        assertNotNull(reader);
        assertEquals("myEntityReader", reader.getName());
        assertEquals("SELECT * FROM Spring_Batch_Two_DataBase.coffee", reader.getSql());
//        assertInstanceOf(BeanPropertyRowMapper.class, reader.getRowMapper());
//        assertEquals(Coffee.class, ((BeanPropertyRowMapper<?>) reader.getRowMapper()).getMappedClass());

        // Verify that getDataSource was called on postgresEntityManager
        verify(postgresEntityManager, times(1)).getDataSource();
    }
}
