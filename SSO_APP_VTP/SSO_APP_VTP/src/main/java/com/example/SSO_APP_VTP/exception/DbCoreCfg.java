package com.example.SSO_APP_VTP.exception;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, MongoAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})

public class DbCoreCfg {

    @Autowired
    Environment env;


    @Bean(name = "coreSource")

    public DataSource getDataSource() throws Exception {
//        String host = env.getProperty("sys.dbUrl");
//        String username = env.getProperty("sys.dbUsername");
//        String password = env.getProperty("sys.dbPassword");
        String host = "jdbc:oracle:thin:@//10.60.117.73:1521/EVTP";
        String username = "vtp";
        String password = "Cntt#2018#";
        System.out.println("## getDataSource " + host + "/" + username + "/**" + (password == null ? "" : password.substring(password.length() - 3)));
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
        dataSource.setJdbcUrl(host);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(2);
        dataSource.setMaxPoolSize(35);
        dataSource.setMaxIdleTime(256);
        dataSource.setMaxIdleTimeExcessConnections(256);
        dataSource.setIdleConnectionTestPeriod(256);
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setTestConnectionOnCheckin(false);
        dataSource.setPreferredTestQuery("SELECT 1 FROM DUAL");
        dataSource.setAcquireRetryDelay(2000);
        dataSource.setAcquireRetryAttempts(999);
        dataSource.setAcquireIncrement(1);
        System.out.println("## getDataSource: " + dataSource);
        return dataSource;
    }

    @Autowired
    @Bean(name = "coreFactory")
    public SessionFactory getSessionFactory(@Qualifier("coreSource") DataSource dataSource) throws Exception {
        Properties properties = new Properties();

        // application.properties
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setHibernateProperties(properties);
        factoryBean.setDataSource(dataSource);
        factoryBean.afterPropertiesSet();
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }

    @Autowired
    @Bean(name = "coreTransactionManager")
    public HibernateTransactionManager getTransactionManager(@Qualifier("coreFactory") SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    @Bean("oracleEvtpJdbcTemplate")
    public JdbcTemplate createJdbcTemplate(@Autowired @Qualifier("coreSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
