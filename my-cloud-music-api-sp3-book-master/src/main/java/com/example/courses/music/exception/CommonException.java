package com.example.courses.music.exception;

import com.example.courses.music.model.ErrorDetail;
import com.example.courses.music.util.Constant;

import java.util.List;

/**
 * 全局自定义异常
 * 目的是对错误进行封装,以方便全局出错
 */
public class CommonException extends RuntimeException {
    /**
     * 状态码
     */
    private int status;

    /**
     * 消息
     */
    private String message;

    /**
     * 扩展状态码
     * <p>
     * 例如：第三方服务出错后，具体的错误码
     */
    private String extraStatus;

    /**
     * 扩展消息
     * <p>
     * 例如：第三方服务出错后，具体的错误消息
     */
    private String extraMessage;

    /**
     * 详细错误信息
     */
    private List<ErrorDetail> detail;

    /**
     * 构造方法
     */
    public CommonException() {
    }

    /**
     * 构造方法
     *
     * @param status
     * @param message
     */
    public CommonException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonException(int status, String message, Throwable e) {
        super(e);
        this.status = status;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param status
     * @param message
     * @param detail
     */
    public CommonException(int status, String message, List<ErrorDetail> detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    /**
     * 创建扩展错误信息对象
     *
     * @param status
     * @param message
     * @return
     */
    public static CommonException createExtra(String status, String message) {
        CommonException result = new CommonException(Constant.ERROR_THIRD_PARTY_SERVICE, Constant.ERROR_THIRD_PARTY_SERVICE_MESSAGE);
        result.setExtraStatus(status);
        result.setExtraMessage(message);
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ErrorDetail> detail) {
        this.detail = detail;
    }

    public String getExtraStatus() {
        return extraStatus;
    }

    public void setExtraStatus(String extraStatus) {
        this.extraStatus = extraStatus;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", extraStatus=" + extraStatus +
                ", extraMessage='" + extraMessage + '\'' +
                ", detail=" + detail +
                '}';
    }

}