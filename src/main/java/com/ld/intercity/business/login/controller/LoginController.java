package com.ld.intercity.business.login.controller;

import com.ld.intercity.business.login.entity.Login;
import com.ld.intercity.business.login.service.LoginService;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.sys.shiro.JWTUtils;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Api(value = "登陆接口", tags = "登陆操作")
@Slf4j
@RequestMapping("login")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "微信登陆")
    @RequestMapping(value = "Init",method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> Init(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
        String str = "";
        String identity = "1";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> map1 = new HashMap<>();
        try {
            //调用微信接口
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9616cb5f7cfbe837&secret=2797b46b7d86f643b6235b2a53312663&js_code=" + code + "&grant_type=authorization_code";
            //获得返回字符串
            str = sendGet(url, "", null);
            //解析返回字符串
            str = str.substring(1, str.length() - 2);
            String[] split = str.split("\",");
            for (String s:split){
                s = s.substring(1);
                String[] split1 = s.split("\":\"");
                map.put(split1[0],split1[1]);
            }
            System.out.println(map);
            if (map.size()>1){
                //根据返回的openId获取数据库中是否存在
                Login openId = loginService.getByOpenId(map.get("openid"));
                //如果不存在则新建保存用户
                if (openId==null){
                    openId = new Login();
                    openId.setUuid(UUID.randomUUID().toString());
                    openId.setOpenId(map.get("openid"));
                    openId.setSessionKey(map.get("session_key"));
                    loginService.save(openId);
                    UserModel userModel = new UserModel();
                    userModel.setUuid(UUID.randomUUID().toString());
                    userModel.setWeChatId(openId.getOpenId());
                    userModel.setIdentity("1");
                    userService.save(userModel);
                    map1.put("id",userModel.getUuid());
                    map1.put("code","0");
                    map1.put("message","缺少电话号码");
                }else {
                    UserModel byWeChatId = userService.getByWeChatId(openId.getOpenId());
//                    map1.put("identity",byWeChatId.getIdentity());
                    identity = byWeChatId.getIdentity();
                    map1.put("id",byWeChatId.getUuid());
                    map1.put("code","0");
                    map1.put("message","成功");
                }
                String token = JWTUtils.creaToken(openId.getOpenId(), openId.getUuid(), openId.getSessionKey());
                map1.put("data",identity + token);
            }else {
                map1.put("code","1002");
                map1.put("message","失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @RequestMapping("login")
    public ModelAndView login(String name, String password) {

        return null;
    }

    public static String sendGet(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url;
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header != null) {
        }
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
//        for (String key : map.keySet()) {
//            System.out.println(key + "--->" + map.get(key));
//        }
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }
}




