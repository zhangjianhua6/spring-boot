package com.jason.web.service;

import com.jason.web.entity.Test;
import com.jason.web.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    @Transactional
    public void update(Test test){
        Test test1 = testMapper.selectById(test.getId());
        System.err.println("update name before: " + test.getName());
        testMapper.update(test);
        System.err.println("update name after: " + test.getName());
    }
}
