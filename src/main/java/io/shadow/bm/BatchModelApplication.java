package io.shadow.bm;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(basePackages = "io.shadow.bm.mapper", sqlSessionTemplateRef = "simpleSqlSessionTemplate")
public class BatchModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchModelApplication.class, args);
    }

    @Bean(value = "batchSqlSessionTemplate")
    @ConditionalOnClass(SqlSessionFactory.class)
    public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

    @Bean(value = "simpleSqlSessionTemplate")
    @ConditionalOnClass(SqlSessionFactory.class)
    public SqlSessionTemplate simpleSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
    }

}
