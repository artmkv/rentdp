package by.rower.model.config;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("by.rower.model")
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class DatabaseConfig {

    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_USERNAME_PROPERTY = "db.username";
    private static final String DB_PASS_PROPERTY = "db.password";
    private static final String DB_DRIVER_PROPERTY = "db.driver";
    public static final String PACKAGES_TO_SCAN_BEAN = "by.rower.model.entity";
    public static final String CLASSPATH_HIBERNATE_PROPERTIES = "classpath:hibernate.properties";

    @Bean
    public DataSource dataSource(Environment environment){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty(DB_URL_PROPERTY));
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DB_DRIVER_PROPERTY)));
        dataSource.setUsername(environment.getProperty(DB_USERNAME_PROPERTY));
        dataSource.setPassword(environment.getProperty(DB_PASS_PROPERTY));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN_BEAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public Properties hibernateProperties(@Value(CLASSPATH_HIBERNATE_PROPERTIES) Resource resource) throws IOException {
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        return properties;
    }

    @Bean
    public TransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}
