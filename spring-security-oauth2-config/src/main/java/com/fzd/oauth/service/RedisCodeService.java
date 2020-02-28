package com.fzd.oauth.service;

import com.fzd.oauth.validate.sms.code.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/28
 */
@Service
public class RedisCodeService {
    private final static String SMS_CODE_PREFIX = "SMS_CODE:";
    private final static Integer TIME_OUT = 300;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void save(ServletWebRequest request, String mobile, SmsCode smsCode) throws Exception {
        stringRedisTemplate.opsForValue().set(key(request, mobile), smsCode.getCode(), TIME_OUT, TimeUnit.SECONDS);
    }

    public String get(ServletWebRequest request, String mobile) throws Exception {
        return stringRedisTemplate.opsForValue().get(key(request, mobile));
    }

    public void remove(ServletWebRequest request, String mobile) throws Exception {
        stringRedisTemplate.delete(key(request, mobile));
    }

    private String key(ServletWebRequest request, String mobile) throws Exception {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isEmpty(deviceId)){
            throw new Exception("请求头中未设置deviceId");
        }
        return SMS_CODE_PREFIX + deviceId + ":" + mobile;
    }
}
