package com.example.courses.music.util;

import com.example.courses.music.exception.CommonException;
import com.example.courses.music.exception.NotFoundException;
import com.example.courses.music.model.ErrorDetail;
import com.example.courses.music.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数验证工具类
 */
public class ValidatorUtil {
    /**
     * 检查如果有错误，抛出异常
     *
     * @param bindingResult
     */
    public static void checkParam(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //创建列表保存错误详细信息
            //返回具体那个字段有错误，方便客户端根据字段自动显示对应输入框错误提示
            List<ErrorDetail> detail = new ArrayList<>();

            //获取所有错误信息
            List<ObjectError> errors = bindingResult.getAllErrors();

            //把错误提示添加到列表
            FieldError data;
            for (ObjectError d : errors) {
                data = (FieldError) d;
                detail.add(new ErrorDetail(data.getField(), data.getDefaultMessage()));
            }

            //抛出自定义异常
            //并传递详细的错误信息
//            throw new CommonException(Constant.ERROR_ARGUMENT, Constant.ERROR_ARGUMENT_MESSAGE, detail);

            //提示第一个错误，这样客户端即使不显示detail信息，也能看到第一个错误信息
            throw new CommonException(Constant.ERROR_ARGUMENT, detail.get(0).getMessage(), detail);
        }
    }

    /**
     * 用户存在，并且没有禁用才正常
     *
     * @param data
     */
    public static void checkUser(User data) {
        if (data == null) {
            throw new CommonException(Constant.ERROR_USER_NOT_FOUND, Constant.ERROR_USER_NOT_FOUND_MESSAGE);
        }

        //判断是否禁用了
        if (data.isDisable()) {
            throw new CommonException(Constant.ERROR_DISABLE, Constant.ERROR_DISABLE_MESSAGE);
        }
    }

    /**
     * 是否存在
     *
     * @param data
     */
    public static void checkExist(Object data) {
        if (data == null) {
            throw new NotFoundException();
        }
    }
}
