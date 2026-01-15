package com.example.courses.music.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.exception.NotFoundException;
import com.example.courses.music.model.Common;
import com.example.courses.music.model.ErrorDetail;
import com.example.courses.music.model.response.PageResponse;
import com.example.courses.music.model.response.Response;

import java.util.List;

/**
 * 响应工具类
 * <p>
 * 对返回的数据进行处理
 * 例如：包装；加密
 */
public class R {
    /**
     * 包装字符串数据
     *
     * @param id
     * @return
     */
    public static Object wrap(String id) {
        Common data = new Common();
        data.setId(id);

        return wrap(data);
    }

    public static Object wrap(Object data, boolean encrypt) {
        return wrap(data, encrypt,true);
    }

    /**
     * 包装对象
     * <p>
     * 可能是失败响应
     *
     * @param data
     * @return
     */
    public static Object wrap(Object data) {
        return wrap(data, false,true);
    }

    /**
     * 包装对象
     * <p>
     * 可能是失败响应
     *
     * @param data
     * @return
     */
    public static Object wrapNoPage(Object data) {
        return wrap(data, false,false);
    }

    /**
     * 包装对象
     * <p>
     * 可能是失败响应
     *
     * @param data
     * @return
     */
    public static Object wrap(Object data, boolean encrypt, boolean wrapPage) {
        if (data == null) {
            //如果为空

            //抛出找不到资源异常
            throw new NotFoundException();
        }

        if (data instanceof List && wrapPage) {
            //没有分页包裹

            //就对列表包裹，但一定要注意这种包装是让数据返回格式和分页一样，但接口本地还是不支持分页
            //这样实现的目的是为了方便后续支持分页
            data = PageResponse.create((List) data);
        }

        if (data instanceof IPage) {
            //MyBatis Plus分页对象

            //包裹为项目模型
            data = PageResponse.create((Page) data);
        }

        if (data instanceof PageInfo) {
            //MyBatis分页对象

            //包裹为项目模型
            data = PageResponse.create((PageInfo) data);
        }

        //判断是否要加密
        data = new Response(data);

        //判断是否要加密
        if (encrypt) {
            try {
                //转为字符串
                String dataString = JSONUtil.toJSON(data);

                //加密
                data = AESUtil.encrypt(dataString);
            } catch (Exception e) {
                throw new ArgumentException();
            }
        }

        return data;
    }

    /**
     * 错误响应
     *
     * @param status
     * @param message
     * @return
     */
    public static Response failed(int status, String message) {
        return new Response(status, message);
    }

    /**
     * 有详细错误提示的响应
     *
     * @param status
     * @param message
     * @param detail
     * @return
     */
    public static Response failed(int status, String message, List<ErrorDetail> detail) {
        return new Response(status, message, detail);
    }

    /**
     * 未知错误
     *
     * @return
     */
    public static Response failed() {
        return failed(Constant.ERROR_UNKNOWN, Constant.ERROR_UNKNOWN_MESSAGE);
    }

    public static Response failed(CommonException data) {
        Response response = R.failed(data.getStatus(), data.getMessage(), data.getDetail());
        response.setExtraStatus(data.getExtraStatus());
        response.setExtraMessage(data.getExtraMessage());

        return response;
    }

    /**
     * 成功响应
     * <p>
     * 没有数据
     *
     * @return
     */
    public static Object wrap() {
        return new Response();
    }

    /**
     * 返回空列表响应
     *
     * @return
     */
    public static Object wrapEmptyPage() {
        PageResponse pageResponse = new PageResponse();
        return R.wrap(pageResponse);
    }
}