package com.jason.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jason.web.pojo.response.AopResponse;
import com.jason.web.pojo.response.InfoReturn;
import com.jason.web.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangjianhua
 * @date: 2019-11-16 15:27
 **/
@RestController
@RequestMapping("/web")
public class SysUserController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public InfoReturn<JSONObject> login(){
        InfoReturn<JSONObject> res =  new InfoReturn<JSONObject>();
        JSONObject obj = new JSONObject();
        res.setData(obj);

        String token = jwtUtils.generateToken(10);
        Claims claims = jwtUtils.getClaimByToken(token);
        obj.put("token", token);
        obj.put("userId", 10);
        obj.put("nickName", "jason");
        obj.put("expireTime", claims.getExpiration().getTime());

        return res;
    }

    @PostMapping("findByUserId")
    public InfoReturn<JSONObject> findByUserId(@RequestBody(required = false) Long userId){
        InfoReturn<JSONObject> res =  new InfoReturn<JSONObject>();
        if (userId == null){
            res.setCode(AopResponse.PARAM_ERROR);
            res.setMessage("用户ID不能为空");
            return res;
        }
        JSONObject obj = new JSONObject();
        res.setData(obj);
        return res;
    }
}
