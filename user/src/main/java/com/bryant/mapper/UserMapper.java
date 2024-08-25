package com.bryant.mapper;

import com.bryant.config.mysql.TenantIdInjectConfig;
import com.bryant.model.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    void insert(@Param("record") UserDetail user);

    @TenantIdInjectConfig(ignore = true)
    void updateById(@Param("record") UserDetail user);

    @TenantIdInjectConfig(ignore = true)
    void deleteById(@Param("id") Long id);

    UserDetail getById(@Param("id") Long id);

}
