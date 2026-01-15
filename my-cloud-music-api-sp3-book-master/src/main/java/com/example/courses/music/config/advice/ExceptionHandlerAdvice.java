package com.example.courses.music.config.advice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.exception.NotFoundException;
import com.example.courses.music.model.response.Response;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.R;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    /**
     * 日志实例
     */
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 处理自定义异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    public @ResponseBody
    Response commonException(CommonException exception) {
        //自定义异常

        //获取异常信息
        return R.failed(exception);
    }

    /**
     * 访问了不存在的资源
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response notFoundException(Exception exception) {
        return R.failed(Constant.ERROR_NOT_FOUND, Constant.ERROR_NOT_FOUND_MESSAGE);
    }

    /**
     * 违反了唯一约束异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Response duplicateKeyException(DuplicateKeyException exception) {
        return R.failed(Constant.ERROR_DATA_EXIST, Constant.ERROR_DATA_EXIST_MESSAGE);
    }

    /**
     * 未登录异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {NotLoginException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response notLoginException(NotLoginException exception) {
        return R.failed(Constant.ERROR_UNAUTHORIZED, Constant.ERROR_UNAUTHORIZED_MESSAGE);
    }

    /**
     * 没有该角色，权限
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {NotRoleException.class, NotPermissionException.class})
    public Response notRoleException(SaTokenException exception) {
        return R.failed(Constant.ERROR_FORBIDDEN, Constant.ERROR_FORBIDDEN_MESSAGE);
    }

    /**
     * 请求方式错误
     * 例如：该接口只支持get请求，发送post就会触发这个异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Response notRoleException(HttpRequestMethodNotSupportedException exception) {
        return R.failed(Constant.ERROR_METHOD, Constant.ERROR_METHOD_MESSAGE);
    }

    /**
     * 数据库异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {MyBatisSystemException.class})
    public Response myBatisSystemException(MyBatisSystemException exception) {
        exception.printStackTrace();
        return R.failed(Constant.ERROR_DATABASE, Constant.ERROR_DATABASE_MESSAGE);
    }

    /**
     * 数据库外键错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public Response myBatisSystemException(DataIntegrityViolationException exception) {
        return R.failed(Constant.ERROR_DATABASE_FOREIGN_KEY, Constant.ERROR_DATABASE_FOREIGN_KEY_MESSAGE);
    }

    /**
     * 默认异常处理器
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    Response defaultHandleException(Exception exception) {
        //打印日志
        //方便调试
        log.error("defaultHandleException {}", exception);

        //其他异常
        return R.failed();
    }
}
