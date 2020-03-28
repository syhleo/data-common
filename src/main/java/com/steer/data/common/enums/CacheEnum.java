package com.steer.data.common.enums;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2018-03-04 23:23
 */
@Getter
public enum CacheEnum {
    REDIS_ENABLE(1, "Redis连接可用，存活"),
    REDIS_DISABLE(0, "Redis异常，不存活"),
    ;

    private Integer code;

    private String message;

    CacheEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
