package com.ld.intercity.sys.shiro;

import com.ld.intercity.utils.JackJson;
import com.ld.intercity.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * filter all requests
 */
@Slf4j
public class StatelessAuthcFilter extends AccessControlFilter {

    /**
     * Only the current method returns to false,
     * and the following method will execute.
     *
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return false;
    }

    /**
     * the current metod returns to false to end the current request.
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
//        To get token,you can use head/Cokie or others.
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String lTokenD = request.getHeader("LTokenD");
        String account = request.getHeader("account");
        String orgid = request.getHeader("orgid");
        log.info("全局拦截器url:" + request.getRequestURI());
        log.info("全局拦截器token:" + lTokenD);
        if (lTokenD == null || lTokenD.isEmpty())
            tokenError(servletRequest, servletResponse);
        else {
            StatelessToken token = new StatelessToken();
            token.setToken(lTokenD);
            try {
                getSubject(servletRequest, servletResponse).login(token);
                String s = JWTUtils.creaToken(account, orgid, orgid);
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setHeader("LTokenD",s);
                response.setHeader("account",account);
                response.setHeader("orgid",orgid);
                return true;
            } catch (Exception e) {
                tokenError(servletRequest, servletResponse);
            }
        }
        return false;
    }

    //    Is no Token
    private void tokenError(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        ResponseResult<String> result = new ResponseResult<>(false, "logout");
        try {
            httpServletResponse.getWriter().write(Objects.requireNonNull(JackJson.beanToJson(result)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Is it Ajax
    private boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
