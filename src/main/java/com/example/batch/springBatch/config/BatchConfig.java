//package com.example.batch.springBatch.config;
//
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.example.batch.springBatch.repos",
//        entityManagerFactoryRef = "postgresEntityManager",
//        transactionManagerRef = "postgresTransactionManager"
//)
//@EntityScan(basePackages = {"com.example.batch.springBatch.domain"})
//public class BatchConfig {
//
//    //Spring_Batch_Two_DataBase
////    spring.application.name=demo
////    spring.jpa.hibernate.ddl-auto=update
////    spring.datasource.url=jdbc:mysql://localhost:3306/Demo?autoReconnect=true&useSSL=false
////    spring.datasource.username=root
////    spring.datasource.password=Udipta@18
////    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
////    spring.datasource.driver-class-name =com.mysql.jdbc.Driver
//
//    @Primary
//    @Bean(name = "postgresDataSource")
////    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource postgresDataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/Spring_Batch_Two_DataBase?autoReconnect=true&useSSL=false");
//        config.setUsername("root");
//        config.setPassword("Udipta@18");
//
//        return new HikariDataSource(config);
//    }
//
////    @Bean(name = "postgresDataSource")
////    @Primary
////    public DataSource postgresDataSource(Environment env) {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://localhost:3306/Spring_Batch_Two_DataBase?autoReconnect=true&useSSL=false");
////        dataSource.setUsername("root");
////        dataSource.setPassword("Udipta@18");
////        return dataSource;
////    }
//
//    @Bean(name = "postgresEntityManager")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean postgresEntityManager(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("postgresDataSource") DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.batch.springBatch.domain") // Replace with your entity package
//                .persistenceUnit("mysqlPU")
//                .build();
////        em.setDataSource(dataSource);
////         em.setPersistenceUnitName("mysqlPU");
////        em.setPackagesToScan("com.example.batch.springBatch.domain");
//////        // Additional configurations if needed
////        return em;
//    }
//
//    @Bean(name = "postgresTransactionManager")
//    @Primary
//    public JpaTransactionManager postgresTransactionManager(
//            @Qualifier("postgresEntityManager") EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
//
//
//
//
//}
