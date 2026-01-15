package com.example.courses.music.service;

import com.baidu.aip.nlp.AipNlp;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Address;
import com.example.courses.music.model.request.NLPAddressRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 百度NLP服务
 */
@Service
public class BaiduNLPService {
    @Autowired
    private AipNlp nlp;

    /**
     * 收货地址文本解析
     * <p>
     * 例如：输入的信息为：四川省成都市天府新区牧华路远大中央公园9栋1单元801 李薇 131411112223
     * 返回为：
     * {
     * "name": "李薇",
     * "phone": "13141111222",
     * "province": "四川省",
     * "province_code": "510000",
     * "city": "成都市",
     * "city_code": "510100",
     * "area": "双流区",
     * "area_code": "510116",
     * "detail": "牧华路远大中央公园9栋1单元801",
     * "default_address": 0
     * }
     * <p>
     * 类似顺丰公众号（大部分快递都有类似的功能），收货地址解析功能
     * <p>
     * 提示：该接口并不是简单的字符串拆分，还会自动纠正省市区信息
     * <p>
     * https://cloud.baidu.com/doc/NLP/s/Nk6z52ci5#%E5%9C%B0%E5%9D%80%E8%AF%86%E5%88%AB%E6%8E%A5%E5%8F%A3
     *
     * @return
     */
    public Address address(NLPAddressRequest data) {
        //可以把结果解析为对象，然后在使用
        JSONObject result = nlp.address(data.getData(), null);

        if (result.has("error_code")) {
            //出错了

            //抛出异常
            throw CommonException.createExtra(String.valueOf(result.getInt("error_code")), result.getString("error_msg"));
        }

        return new Address(
                result.getString("person"),
                result.getString("phonenum"),
                result.getString("province"),
                result.getString("province_code"),
                result.getString("city"),
                result.getString("city_code"),
                result.getString("county"),
                result.getString("county_code"),
                result.getString("detail")
        );
    }
}