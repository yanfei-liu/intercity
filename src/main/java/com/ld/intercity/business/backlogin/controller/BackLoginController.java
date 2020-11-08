package com.ld.intercity.business.backlogin.controller;

import com.ld.intercity.business.backlogin.model.LoginModel;
import com.ld.intercity.sys.shiro.JWTUtils;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

/**
 * @author LD
 */
@Api(tags = "后台用户登陆接口")
@Slf4j
@RestController
@RequestMapping("/backLogin")
public class BackLoginController {

//    @Autowired
//    private AccountService accountService;

//    @ApiOperation(value = "获取token")
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseResult<String> login(@ApiParam(value = "登陆实体", required = true)
//                                        @Valid @RequestBody LoginModel model, BindingResult bindingResult) throws Exception {
//        if (bindingResult.hasErrors()) {
//            return new ResponseResult<>(false, bindingResult.getAllErrors().get(0).getDefaultMessage());
//        }
//        String md5Password = DigestUtils.md5DigestAsHex(model.getPassword().getBytes(StandardCharsets.UTF_8));
//        ResponseResult<AccountModel> responseResult = accountService.findByAccount(model.getAccount());
//        if (responseResult.isSuccess()) {
//            if (responseResult.getData().getPassword().equals(md5Password)) {
//                String token = JWTUtils.creaToken(responseResult.getData().getAccount(), responseResult.getData().getUuid(), responseResult.getData().getPositionModel().getUuid());
//                return new ResponseResult<>(true, "成功", token);
//            }
//        }
//        return new ResponseResult<>(false, "账号或密码错误");
//    }

}
