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
 * 登录记录
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日志名称
     */
    private String logname;

    /**
     * 管理员id
     */
    private Integer userid;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 是否执行成功
     */
    private String succeed;

    /**
     * 具体消息
     */
    private String message;

    /**
     * 登录ip
     */
    private String ip;


}
