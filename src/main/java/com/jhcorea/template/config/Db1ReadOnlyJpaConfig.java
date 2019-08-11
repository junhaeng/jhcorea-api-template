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

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = {"com.jhcorea.template.repository.readonly"})
public class Db1ReadOnlyJpaConfig {

    @Bean(destroyMethod = "close", name = "db1ReadyOnlyDatasource")
    @ConfigurationProperties(prefix = "heo-db1-read-only")
    public HikariDataSource db1ReadOnlyDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryReadOnly")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryReadOnly(
            EntityManagerFactoryBuilder builder, @Qualifier("db1ReadyOnlyDatasource") DataSource db1ReadOnlyDataSource) {
        return builder
                .dataSource(db1ReadOnlyDataSource)
                .packages("com.jhcorea.template.repository.entity")
//                .persistenceUnit("template_read_only")
                .build();
    }

    @Primary
    @Bean(name = "db1ReadOnlyTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryReadOnly") EntityManagerFactory entityManagerFactoryReadOnly) {
        return new JpaTransactionManager(entityManagerFactoryReadOnly);
    }
}
