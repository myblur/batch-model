package io.shadow.bm.service;

import io.shadow.bm.mapper.RequestDetailMapper;
import io.shadow.bm.mapper.RequestInfoMapper;
import io.shadow.bm.model.RequestDetail;
import io.shadow.bm.model.RequestInfo;
import io.shadow.bm.tx.BatchTransactionCallback;
import io.shadow.bm.tx.BatchTransactionExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    @Resource
    private RequestInfoMapper requestInfoMapper;

    @Resource
    private BatchTransactionExecutor batchTransactionExecutor;

    public void saveRequestInfos(final List<RequestInfo> requestInfos) {
        // 所有需要大批量插入的操作全部放这里面去操作，事务为单独新起的一个事务
        // 是否使用匿名类根据具体情况决定
        BatchTransactionCallback callback = new BatchTransactionCallback() {

            @Override
            public String getName() {
                return "批量请求数据插入";
            }

            @Override
            public void callback() {
                // 所有需要用到的mapper都通过getMapper方法获取
                RequestInfoMapper requestInfoMapper = getMapper(RequestInfoMapper.class);
                RequestDetailMapper requestDetailMapper = getMapper(RequestDetailMapper.class);

                for (RequestInfo requestInfo : requestInfos) {
                    requestInfoMapper.insert(requestInfo);
                }
                for (RequestInfo requestInfo : requestInfos) {
                    RequestDetail requestDetail = new RequestDetail();
                    requestDetail.setRequestBody("无内容");
                    requestDetail.setRequestInfoId(requestInfo.getId());
                    requestDetailMapper.insert(requestDetail);
                }

                // 删除操作也在这里做，就能保证是在一个事务里面
            }

        };
        batchTransactionExecutor.executeCallback(callback);
    }


}
