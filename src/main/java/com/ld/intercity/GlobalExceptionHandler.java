package com.ld.intercity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.intercity.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

/**
 * @author LD
 * 全局错误处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseResult<String> unauthorizedException(Exception exception) {
        return new ResponseResult<>(false, "权限不足");
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseResult<String> authorizationException(Exception exception) {
        return new ResponseResult<>(false, "权限不足");
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseResult<String> fileNotFoundException(Exception exception) {
        return new ResponseResult<>(false, "文件不存在");
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseResult<String> httpMessageNotReadableException(Exception exception) {
        return new ResponseResult<>(false, "参数序列化错误");
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> httpRequestMethodNotSupportedException(Exception exception) {
        return new ResponseResult<>(false, "请求方式错误");
    }

//    @ExceptionHandler(value = EntityNotFoundException.class)
//    public ResponseResult<String> entityNotFoundException(Exception exception) {
//        return new ResponseResult<>(false, "数据不存在");
//    }

    @ExceptionHandler(value = ConnectException.class)
    public ResponseResult<String> connectException(Exception exception) {
        return new ResponseResult<>(false, "数据库可能无法正常通信");
    }

    /**
     * 此方法必须在最后
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<String> exception(Exception exception) {
        return new ResponseResult<>(false, "其它未定义的错误");
    }

    /**
     * 判断是否是ajax
     *
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * ajax的返回工具类
     *
     * @param response
     * @param str
     * @throws IOException
     */
    public void res(HttpServletResponse response,
                    String str) throws IOException {
        ResponseResult<String> result = new ResponseResult<>();
        result.setSuccess(false);
        result.setMessage(str);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);
        //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(json);
    }
}
