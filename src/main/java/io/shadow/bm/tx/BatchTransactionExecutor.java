package io.shadow.bm.tx;

import org.apache.ibatis.jdbc.SQL;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;

@Service
public class BatchTransactionExecutor implements ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionExecutor.class);

    private ApplicationContext applicationContext;

    private final SqlSessionTemplate sqlSessionTemplate;


    public BatchTransactionExecutor(@Qualifier("batchSqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 开启一个新的事务
     *
     * @param callback
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, timeout = 30,
            rollbackFor = Exception.class)
    public void executeCallback(BatchTransactionCallback callback) {
        callback.setSqlSessionTemplate(this.sqlSessionTemplate);
        log.info("【{}】开始", callback.getName());
        long start = System.currentTimeMillis();
        callback.callback();
        long elapsed = System.currentTimeMillis() - start;
        log.info("【{}】结束，耗时：{}ms", callback.getName(), elapsed);
    }
}
