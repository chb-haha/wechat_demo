package com.example.wechat.domain;

import lombok.Data;

/**
 * 微信服务器返回的token字符串
 */
@Data
public class Token {

    /**
     * 微信后端返回的json的字符串
     * "access_token":"ACCESS_TOKEN",
     "expires_in":7200,
     "refresh_token":"REFRESH_TOKEN",
     "openid":"OPENID",
     "scope":"SCOPE",
     "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     */

    // 接口调用凭证
    private String access_token;
    // access_token接口调用凭证超时时间，单位（秒）
    private String expires_in;
    // 用户刷新access_token
    private String refresh_token;
    // 授权用户唯一标识
    private String openid;
    // 用户授权的作用域，使用逗号（,）分隔
    private String scope;
    // 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段
    private String unionid;
}
