package cn.wbnull.hellobill.db.mapper;

import cn.wbnull.hellobill.db.entity.ImportBillInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author null
 * @since 2025-01-25
 */
@Mapper
public interface ImportBillInfoMapper extends BaseMapper<ImportBillInfo> {

    int insertBatch(List<ImportBillInfo> importBillInfoList);
}
