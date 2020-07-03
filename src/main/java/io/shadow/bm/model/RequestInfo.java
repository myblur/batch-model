package io.shadow.bm.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RequestInfo {

    private Long id;

    private String remoteAddress;

    private String requestUrl;

    private String requestMethod;

    private LocalDateTime requestTime;

}
