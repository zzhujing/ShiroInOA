package com.bdth.oa.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * @create 2019-04-22 10:40
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseEnum {

    SUCCESS(true,200,"操作成功！"),
    FAIL(false,404,"操作失败！"),
    UNAUTHENTICATED(false,401,"此操作需要登陆系统！"),
    UNAUTHORISE(false,403,"权限不足，无权操作！"),
    SERVER_ERROR(false,500,"抱歉，系统繁忙，请稍后重试！");

    private boolean isSuccess;
    private Integer code;
    private String message;
}
