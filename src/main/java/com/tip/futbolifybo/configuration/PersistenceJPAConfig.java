package com.tip.futbolifybo.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.tip.futbolifybo"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    private ComboPooledDataSource generateHerokuDatabase(String remote){
        URI databaseURI = null;
        try {
            databaseURI = new URI("postgres://postgres:1q2w3E4R!@localhost:5432/futbolify");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String[] userInfo = databaseURI.getUserInfo().split(":");
        String username = userInfo[0];
        String password = userInfo[1];
        String url = "jdbc:postgresql://" + databaseURI.getHost() + ":" + databaseURI.getPort() + databaseURI.getPath();

        ComboPooledDataSource dataSource = new ComboPooledDataSource("db");
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public DataSource dataSource(){
        URI databaseURI = null;
        try {
            databaseURI = new URI("postgres://postgres:1q2w3E4R!@localhost:5432/futbolify");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String[] userInfo = databaseURI.getUserInfo().split(":");
        String username = userInfo[0];
        String password = userInfo[1];
        String url = "jdbc:postgresql://" + databaseURI.getHost() + ":" + databaseURI.getPort() + databaseURI.getPath();

        ComboPooledDataSource dataSource = new ComboPooledDataSource("db");
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        try {
            dataSource.setDriverClass("org.postgresql.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty("hibernate.connection.useUnicode", "true");
        properties.setProperty("hibernate.connection.characterEncoding", "UTF-8");
        properties.setProperty("hibernate.connection.charSet", "UTF-8");
        properties.setProperty("current_session_context_class", "thread");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.hibernate.connection.pool_size", "10");
        properties.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation","true");
        return properties;
    }
}
