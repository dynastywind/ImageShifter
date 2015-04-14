package cisc.awas.image_shifter.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "cisc.awas.image_shifter.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JpaConfig {
	
	private static final String DATABASE_DRIVER = "db.driver";
	private static final String DATABASE_URL = "db.url";
	private static final String DATABASE_USER = "db.user";
	private static final String DATABASE_PASSWORD = "db.password";
	private static final String PACKAGES_TO_SCAN = "packages.to.scan";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DruidDataSource source = new DruidDataSource();
		source.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
		source.setUrl(env.getRequiredProperty(DATABASE_URL));
		source.setUsername(env.getRequiredProperty(DATABASE_USER));
		source.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));
		return source;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		factory.setPackagesToScan(env.getRequiredProperty(PACKAGES_TO_SCAN));
		factory.setJpaProperties(hibernateProperties());
		factory.afterPropertiesSet();
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactory().getObject());
		return manager;
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
		properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
		return properties;
	}
	
}
