package io.shadow.bm.service;

import io.shadow.bm.BatchModelApplication;
import io.shadow.bm.model.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatchModelApplication.class)
public class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Test
    public void saveRequestInfos() {
        List<RequestInfo> requestInfos = new ArrayList<RequestInfo>();
        Random random = new Random(System.currentTimeMillis());
        List<String> requestMap = new ArrayList<>();
        requestMap.add("/healthcheck,GET");
        requestMap.add("/nonbatch,POST");
        requestMap.add("/statuscheck,GET");
        for (int i = 0; i < 200; i++) {
            RequestInfo requestInfo = new RequestInfo();
            int number = random.nextInt(255);
            requestInfo.setRemoteAddress("172.28.16." + number);
            String[] requestDetails;
            if (number < 50) {
                requestDetails = requestMap.get(0).split(",");
            } else if (number < 150) {
                requestDetails = requestMap.get(1).split(",");
            } else {
                requestDetails = requestMap.get(2).split(",");
            }
            requestInfo.setRequestUrl(requestDetails[0]);
            requestInfo.setRequestMethod(requestDetails[1]);
            requestInfo.setRequestTime(LocalDateTime.now());
            requestInfos.add(requestInfo);
        }
        requestService.saveRequestInfos(requestInfos);
    }
}