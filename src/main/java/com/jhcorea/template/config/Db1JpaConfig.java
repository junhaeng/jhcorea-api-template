package com.jhcorea.template.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@EnableAutoConfiguration(
//        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
//)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.jhcorea.template.repository.writable"})
public class Db1JpaConfig {

    @Bean(destroyMethod = "close", name = "db1Datasource")
    @ConfigurationProperties(prefix = "heo-db1")
    public HikariDataSource db1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("db1Datasource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.jhcorea.template.repository.entity")
//                .persistenceUnit("template")
                .build();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
