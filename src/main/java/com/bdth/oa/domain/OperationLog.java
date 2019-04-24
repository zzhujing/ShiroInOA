package com.bdth.oa.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("operation_log")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日志类型
     */
    private String logtype;

    /**
     * 日志名称
     */
    private String logname;

    /**
     * 用户id
     */
    private Integer userid;

    /**
     * 类名称
     */
    private String classname;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 是否成功
     */
    private String succeed;

    /**
     * 备注
     */
    private String message;


}
