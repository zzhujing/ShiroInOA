package com.bdth.oa.exception;

import lombok.*;

/**
 * @author hj
 * @create 2019-04-24 20:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorTip {

    private String message;
    private Integer errorCode;
}
