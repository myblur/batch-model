package io.shadow.bm.mapper;

import io.shadow.bm.model.RequestDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface RequestDetailMapper {

    @SelectKey(statement = "SELECT nextval('seq_request_detail')", keyProperty = "id", before = true, resultType =
            long.class)
    @Insert("insert into t_request_detail (id, request_body, request_info_id) values (#{id,jdbcType=BIGINT}, " +
            "#{requestBody,jdbcType=VARCHAR}, #{requestInfoId,jdbcType=BIGINT})")
    int insert(RequestDetail requestDetail);

}
