//package com.example.batch.springBatch;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableAutoConfiguration
//@EnableBatchProcessing
//public class TestBatchConfig {
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
//        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
//                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//    }
//
//
////    @Bean
////    public JobLauncherTestUtils jobLauncherTestUtils() {
////        return new JobLauncherTestUtils();
////    }
//}
