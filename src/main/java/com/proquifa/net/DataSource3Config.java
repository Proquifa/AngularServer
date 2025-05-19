package com.proquifa.net;

import java.util.HashMap;

import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
 
@Configuration
// Load to Environment
// (@see resources/datasource-cfg.properties).
@PropertySources({ @PropertySource("classpath:application.properties") })
public class DataSource3Config {
 
    @Autowired
    private Environment env; // Contains Properties Load by @PropertySources
 
    @Bean(name="jdbc3")
    public DataSource ds3Datasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
        dataSource.setUrl(env.getProperty("third.datasource.url"));
        dataSource.setUsername(env.getProperty("third.datasource.username"));
        dataSource.setPassword(env.getProperty("third.datasource.password"));
 
        return dataSource;
    }
 
    @Bean
    public LocalContainerEntityManagerFactoryBean ds3EntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds3Datasource());
 
        // Scan Entities in Package:
        em.setPackagesToScan(new String[] { "com.proquifa.net" });
 
        em.setPersistenceUnitName("Checador");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
 
        HashMap<String, Object> properties = new HashMap<>();
        // JPA & Hibernate
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
 
        // Solved Error: PostGres createClob() is not yet implemented.
        // PostGres Only.
        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",  false);
 
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }
 
    @Bean
    public PlatformTransactionManager ds3TransactionManager() {
 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds3EntityManager().getObject());
        return transactionManager;
    }
 
}