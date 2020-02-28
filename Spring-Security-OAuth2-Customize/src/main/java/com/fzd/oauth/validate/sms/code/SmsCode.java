package com.fzd.oauth.validate.sms.code;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsCode {
    private String code;

    public SmsCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
