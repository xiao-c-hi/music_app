package com.example.courses.music.model.response;

import com.example.courses.music.model.Base;
import com.example.courses.music.model.ErrorDetail;

import java.util.List;


/**
 * 所有接口返回包装类
 */
public class Response extends Base {

    /**
     * 真实数据
     * 详情接口：就是一个对象
     * 列表接口：是分页对象
     */
    private Object data;

    /**
     * 响应码
     * 默认为0：表示成功
     */
    private int status;

    /**
     * 错误提示
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
     * 无参构造方法
     */
    public Response() {
    }

    /**
     * data构造方法
     *
     * @param data
     */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param status
     * @param message
     */
    public Response(int status, String message) {
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
    public Response(int status, String message, List<ErrorDetail> detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
}