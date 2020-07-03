package io.shadow.bm.tx;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;

public abstract class BatchTransactionCallback {

    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public String getName() {
        return "批量操作";
    }

    public <T> T getMapper(Class<T> mapperClass) {
        return sqlSessionTemplate.getMapper(mapperClass);
    }

    public abstract void callback();

}
