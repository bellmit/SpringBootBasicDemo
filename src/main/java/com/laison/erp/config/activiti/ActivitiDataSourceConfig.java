package com.laison.erp.config.activiti;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * #activiti配置
 * activiti:
 * check-process-definitions: false
 * db-identity-used: true
 * #自动生成activiti相关表，第一次生成后关闭
 * #flase-> 默认值。activiti在启动时，会对比数据库表中保存的版本，如果没有表或者版本不匹配，将抛出异常。（生产环境常用）
 * #true-> activiti会对数据库中所有表进行更新操作。如果表不存在，则自动创建。（开发时常用）
 * #create_drop-> 在activiti启动时创建表，在关闭时删除表（必须手动关闭引擎，才能删除表）。（单元测试常用）
 * #drop-create-> 在activiti启动时删除原来的旧表，然后在创建新表（不需要手动关闭引擎）
 * database-schema-update: true
 * #保存历史数据级别
 * history-level: full
 */
@Configuration
public class ActivitiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setHistoryLevel(HistoryLevel.FULL);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
        //id生成器
        //configuration.setIdGenerator(new MyUUIDgenerator());
        //第一生成表要启动下面配置，以后重新生成create改为drop-create
        //如果有表不存在就更新添加
        // configuration.setDatabaseSchemaUpdate("true");
        //设置Schema为ACT，他会字段添加表
        //configuration.setDatabaseSchema("ACT");
        return configuration;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }


}
