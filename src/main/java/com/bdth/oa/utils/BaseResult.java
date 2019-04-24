package com.bdth.oa.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * @create 2019-04-22 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResult {

    private BaseEnum baseEnum;
    private Object data;
    private Cursor cursor;


    /**
     * 执行默认成功，不带数据
     * @return
     */
    public static BaseResult ok() {
        return BaseResult.builder()
                .baseEnum(BaseEnum.SUCCESS)
                .build();
    }

    /**
     * 执行默认成功，不带数据
     * @return
     */
    public static BaseResult ok(Object data,Cursor cursor) {
        return BaseResult.builder()
                .baseEnum(BaseEnum.SUCCESS)
                .data(data)
                .cursor(cursor)
                .build();
    }


    /**
     * 执行默认成功+带数据
     */
    public static BaseResult ok(Object data) {
        return BaseResult.builder()
                .baseEnum(BaseEnum.SUCCESS)
                .data(data)
                .build();
    }

    /**
     * 执行成功，不带数据
     * @param baseEnum
     * @return
     */
    public static BaseResult ok(BaseEnum baseEnum) {
        return BaseResult.builder()
                .baseEnum(baseEnum)
                .build();
    }

    /**
     * 执行成功，带数据
     * @param baseEnum
     * @return
     */
    public static BaseResult ok(BaseEnum baseEnum,Object data) {
        return BaseResult.builder()
                .baseEnum(baseEnum)
                .data(data)
                .build();
    }

    /**
     * 执行成功，带数据 + 分页信息
     * @param baseEnum
     * @return
     */
    public static BaseResult ok(BaseEnum baseEnum,Object data,Cursor cursor) {
        return BaseResult.builder()
                .baseEnum(baseEnum)
                .data(data)
                .cursor(cursor)
                .build();
    }

    /**
     * 返回错误信息带数据
     * @param baseEnum
     * @param data
     * @return
     */
    public static BaseResult error(BaseEnum baseEnum, Object data) {
        return BaseResult.builder()
                .baseEnum(baseEnum)
                .data(data)
                .build();
    }

    /**
     * 返回默认错误信息+不带数据
     * @return
     */
    public static BaseResult error() {
        return BaseResult.builder()
                .baseEnum(BaseEnum.FAIL)
                .build();
    }


    @Data
    @JsonInclude
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public  static class Cursor {
        private Long page;
        private Long size;
        private Long total;
    }
}

