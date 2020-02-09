package com.fzd.security.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
public class ImageCode {
    private BufferedImage bufferedImage;

    private String code;

    private LocalDateTime expiredTime;

    public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime expiredTime) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expiredTime = expiredTime;
    }

    public ImageCode(BufferedImage bufferedImage, String code, int expireIn) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiredTime);
    }
}
