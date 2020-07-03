package io.shadow.bm.mapper;

import io.shadow.bm.model.RequestInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface RequestInfoMapper {

    @SelectKey(statement = "SELECT nextval('seq_request_info')", keyProperty = "id", before = true, resultType =
            long.class)
    @Insert("insert into t_request_info (id, remote_address, request_url, request_method, request_time) values " +
            "(#{id,jdbcType=BIGINT}, #{remoteAddress,jdbcType=VARCHAR}, #{requestUrl,jdbcType=VARCHAR}, " +
            "#{requestMethod,jdbcType=VARCHAR}, #{requestTime,jdbcType=VARCHAR})")
    int insert(RequestInfo requestInfo);

}
