/*
 * Copyright (c) 2017-2025 null. All rights reserved.
 */

package cn.wbnull.hellobill.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 分类信息表
 * </p>
 *
 * @author null
 * @since 2025-03-02
 */
@Getter
@Setter
@TableName("class_info")
public class ClassInfo {

    /**
     * 主键ID
     */
    @TableId(value = "uuid", type = IdType.INPUT)
    private String uuid;

    /**
     * 一级分类
     */
    @TableField("top_class")
    private String topClass;

    /**
     * 二级分类
     */
    @TableField("second_class")
    private String secondClass;

    /**
     * 分类类型 0-支出; 1-收入
     */
    @TableField("type")
    private Byte type;

    /**
     * 序号
     */
    @TableField("serial_no")
    private Integer serialNo;

    /**
     * 状态 0-不启用; 1-启用
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
