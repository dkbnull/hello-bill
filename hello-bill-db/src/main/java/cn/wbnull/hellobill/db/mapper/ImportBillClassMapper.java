/*
 * Copyright (c) 2017-2025 null. All rights reserved.
 */

package cn.wbnull.hellobill.db.mapper;

import cn.wbnull.hellobill.db.entity.ImportBillClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账单导入分类表 Mapper 接口
 * </p>
 *
 * @author null
 * @since 2025-03-02
 */
@Mapper
public interface ImportBillClassMapper extends BaseMapper<ImportBillClass> {

    ImportBillClass getByDetail(@Param("detail") String detail);
}
