package com.jason.web.mapper;

import com.jason.web.entity.Test;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    int insert(Test record);

    int insertSelective(Test record);

    int update(Test record);

    Test selectById(@Param("id")int id);
}