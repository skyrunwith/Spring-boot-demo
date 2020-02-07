package com.fzd.security.sms.code;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsCode {
    private String code;

    private LocalDateTime expiredTime;

    public SmsCode(String code, int expiredIn) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredIn);
    }

    public SmsCode(String code, LocalDateTime expiredTime) {
        this.code = code;
        this.expiredTime = expiredTime;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.expiredTime);
    }
}
