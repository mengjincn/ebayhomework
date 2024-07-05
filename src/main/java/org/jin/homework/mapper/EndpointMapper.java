package org.jin.homework.mapper;

import org.apache.ibatis.annotations.*;
import org.jin.homework.entity.Endpoint;

import java.util.List;

@Mapper
public interface EndpointMapper {
    @Select("SELECT * FROM endpoints WHERE userId = #{userId}")
    Endpoint getEndpointByUserId(Long userId);

    @Select("SELECT * FROM endpoints")
    List<Endpoint> getAllEndpoints();

    @Insert("INSERT INTO endpoints(userId, endpoint) VALUES(#{userId}, #{endpoint})")
    void insertEndpoint(@Param("userId") Long userId, @Param("endpoint") String endpoint);

    @Delete("DELETE FROM endpoints WHERE userId = #{userId}")
    void deleteEndpointsByUserId(Long userId);
}
