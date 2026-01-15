package com.example.courses.music.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

/**
 * 模型基类
 * <p>
 * 整个实体返回的json字段格式转换为下划线格式
 * 真实项目中根据需求实现就行了
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Base implements Serializable {

}
