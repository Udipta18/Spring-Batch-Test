//package com.example.batch.springBatch.config;
//
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//import jakarta.persistence.EntityManagerFactory;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.example.batch.springBatch.repos",
//        entityManagerFactoryRef = "postgresEntityManager",
//        transactionManagerRef = "postgresTransactionManager"
//)
//@EntityScan(basePackages = {"com.example.batch.springBatch.domain"})
//public class MysqlConfig {
//
//    @Bean(name = "mySqlDataSource")
//    @Primary
//    public DataSource mySqlDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/Spring_Batch_Two_DataBase?autoReconnect=true&useSSL=false");
//        dataSource.setUsername("root");
//        dataSource.setPassword("Udipta@18");
//
//        return dataSource;
//    }
//
//    @Bean(name = "postgresEntityManager")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean mySqlEntityManager(
//            @Qualifier("mySqlDataSource") DataSource dataSource, Environment env) {
//
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.example.batch.springBatch.domain");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.put("hibernate.show_sql", "true");
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    @Bean(name = "postgresTransactionManager")
//    @Primary
//    public JpaTransactionManager transactionManager(
//            @Qualifier("postgresEntityManager") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
