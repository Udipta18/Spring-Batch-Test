package com.example.batch.springBatch.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.batch.springBatch.repos",
        entityManagerFactoryRef = "postgresEntityManager",
        transactionManagerRef = "postgresTransactionManager"
)
public class BatchConfig {

    @Bean(name = "postgresDataSource")
    @Primary
    public DataSource postgresDataSource(Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/batch");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Virat@18");
        return dataSource;
    }

    @Bean(name = "postgresEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        return builder
                .dataSource(dataSource)
                .packages("com.example.batch.springBatch.domain") // Replace with your entity package
                .persistenceUnit("postgresPU")
                .build();
//        em.setDataSource(dataSource);
//         em.persistenceUnit("postgresPU");
//        em.setPackagesToScan("com.example.batch.springBatch.domain.*");
//        // Additional configurations if needed
//        return em;
    }

    @Bean(name = "postgresTransactionManager")
    @Primary
    public JpaTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManager") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }




}
