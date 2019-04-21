package com.ld.intercity.sys.shiro;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Date;
import java.util.Map;

@Slf4j
public class StatelessRealm extends AuthorizingRealm {

//    @Resource
//    private AccountService accountService;
//    @Resource
//    private AuthorityService authorityService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    /**
     * @param arg0 当前登陆的实体
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        AccountModel token = (AccountModel) arg0.getPrimaryPrincipal();
//        ResponseResult<List<JurisdictionModel>> result = authorityService.findByAutPosId2(token.getPositionModel().getUuid());
//        if (result.isSuccess()) {
//            result.getData().forEach(k -> {
//                info.addStringPermission(k.getJurFlag());
//            });
//        }
        return info;
    }

    /**
     * 登陆验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        String token = (String) authcToken.getPrincipal();
        if (token == null)
            throw new UnknownAccountException("令牌丢失!");
        Map<String, Claim> map = JWTUtils.verifToken(token);
        if (map == null)
            throw new UnknownAccountException("令牌过期，请从新登陆!");
        String iss = map.get("iss").asString();
        if (!iss.equals("ldtoken"))
            throw new UnknownAccountException("令牌签名不正确!");
        Date nowDate = new Date();
        Date iat = map.get("iat").asDate();
        if (nowDate.before(iat))
            throw new UnknownAccountException("令牌过期，请从新登陆!");
        Date exp = map.get("exp").asDate();
        if (exp.before(nowDate))
            throw new UnknownAccountException("令牌过期，请从新登陆!");

        String account = map.get("sub").asString();
//        try {
//            AccountModel model = new AccountModel();
//            model.setAccount(account);
//            ResponseResult<AccountModel> result = accountService.findByAccount(model.getAccount());
//            if (result.isSuccess()) {
//                AccountModel model1 = result.getData();
//                if (model1.getIsLogin() != 1) {
//                    throw new UnknownAccountException("当前账号不允许登陆!");
//                }
//                return new SimpleAuthenticationInfo(model1, token, getName());
//            }
//            throw new UnknownAccountException("当前用户已不存在!");
//        } catch (Exception e) {
//            throw new UnknownAccountException("服务器错误!");
//        }
        return null;
    }

}
