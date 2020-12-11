package com.ld.intercity.business.userlogin.controller;

import com.ld.intercity.business.backlogin.model.LoginModel;
import com.ld.intercity.business.login.entity.Login;
import com.ld.intercity.business.login.service.LoginService;
import com.ld.intercity.sys.shiro.JWTUtils;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

/**
 * @author LD
 */
@Api(tags = "用户登陆接口，当用户打开小程序时自动登陆")
@Slf4j
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/Init")
    public ModelAndView Init(){
        return new ModelAndView("/pages/lyear_pages_login.html");
    }

    @ApiOperation(value = "获取token")
    @RequestMapping(value = "/login")
    public ModelAndView login(@ApiParam(value = "登陆实体", required = true)
                                  @RequestParam("account")String account,
                              @RequestParam("password")String password,
                              HttpServletRequest request,HttpServletResponse response,
                              ModelAndView modelAndView) throws Exception {
        if (StringUtils.isNotBlank(account)&&StringUtils.isNotBlank(password)){
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            Login byAccount = loginService.getByAccount(account);
            if (byAccount!=null){
                if (md5Password.equals(byAccount.getPassword())){
                    String token = JWTUtils.creaToken(byAccount.getAccount(), byAccount.getPassword(),byAccount.getUuid());
                    modelAndView.addObject("success",true);
                    modelAndView.addObject("msg","登陆成功");
                    modelAndView.addObject("token",token);
                    response.setHeader("LTokenD",token);
                    modelAndView.setViewName("/pages/home/home.html");
                    return modelAndView;
                }else {
                    modelAndView.addObject("msg","账号或密码错误");
                    modelAndView.addObject("success",false);
                    modelAndView.setViewName("/pages/backLogin.html");
                    return modelAndView;
                }
            }else {
                modelAndView.addObject("msg","该账号不存在");
                modelAndView.addObject("success",false);
                modelAndView.setViewName("/pages/backLogin.html");
                return modelAndView;
            }
        }else {
            modelAndView.addObject("msg","账号或密码不可为空");
            modelAndView.addObject("success",false);
            modelAndView.setViewName("/pages/backLogin.html");
            return modelAndView;
        }
    }

    @RequestMapping("/home")
    public ModelAndView home(){
        return new ModelAndView("/pages/home/lyear_main.html");
    }
}
