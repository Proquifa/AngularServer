package com.proquifa.net;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
public class DataSource1Config {

	@Autowired
	private Environment env; // Contains Properties Load by @PropertySources
	
	@Primary
	@Bean(name="jdbc1")
	public DataSource ds1Datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean ds1EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds1Datasource());

		// Scan Entities in Package:
		em.setPackagesToScan(new String[] { "com.proquifa.net" });

		em.setPersistenceUnitName("SAP");
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
	public PlatformTransactionManager ds1TransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(ds1EntityManager().getObject());
		return transactionManager;
	}

}