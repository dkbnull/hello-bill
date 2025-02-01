package cn.wbnull.hellobill.db.mapper;

import cn.wbnull.hellobill.db.entity.ImportBillClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author null
 * @since 2025-01-25
 */
@Mapper
public interface ImportBillClassMapper extends BaseMapper<ImportBillClass> {

    ImportBillClass getImportBillClass(@Param("detail") String detail);
}
