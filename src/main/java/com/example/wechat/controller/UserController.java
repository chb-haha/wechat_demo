package com.example.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.example.wechat.domain.Token;
import com.example.wechat.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("微信服务器回调...");
        // 获取到code值
        String code = request.getParameter("code");
        // 判断
        if(code == null){
            throw new RuntimeException("用户禁止授权...");
        }

        try {
            // 获取到了code值，回调没有问题
            // 定义地址
            String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7287a60bb700fd21" +
                    "&secret=1ef8755f92bebae8ad7bab432ba29cbf&code="+code+"&grant_type=authorization_code";
            // 发送请求
            cn.tx.utils.HttpClient client = new cn.tx.utils.HttpClient(token_url);
            // 发送get请求
            client.get();
            // 获取到请求的结果  json格式的字符串，把json格式的字符串转换成对象或者Map集合
            String token_content = client.getContent();
            // 把json字符串转换成对象
            Token token = JSON.parseObject(token_content, Token.class);

            // 获取到接口调用凭证
            // 获取个人的信息
            String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid();
            cn.tx.utils.HttpClient client1 = new cn.tx.utils.HttpClient(user_url);
            client1.get();
            String user_content = client1.getContent();
            // 解析json字符串
            User user = JSON.parseObject(user_content, User.class);

            System.out.println("微信用户信息："+user);

            // =====================================================
            // 公司自己的业务逻辑 存储数据库 redis ...

            // 跳转到项目的首页
            request.setAttribute("user",user);
            request.getRequestDispatcher("/admin/index.jsp").forward(request,response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信扫描登录异常...");
        }

    }

    @GetMapping("/test")
    public String test(){
        return "hell";
    }

}
