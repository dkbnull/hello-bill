package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ImportBillClass;
import cn.wbnull.hellobill.db.entity.ImportBillInfo;
import cn.wbnull.hellobill.db.mapper.ImportBillClassMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 导入账单信息表服务
 *
 * @author null
 * @date 2025-01-25
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Repository
public class ImportBillClassRepository {

    @Autowired
    private ImportBillClassMapper importBillClassMapper;

    public ImportBillClass getByDetail(String detail) {
        LambdaQueryWrapper<ImportBillClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillClass::getDetail, detail);
        queryWrapper.orderByDesc(ImportBillClass::getGmtCreate);
        queryWrapper.last("limit 1");

        return importBillClassMapper.selectOne(queryWrapper);
    }

    public void updateByImportBillInfo(ImportBillInfo importBillInfo) {
        ImportBillClass importBillClass = getByDetail(importBillInfo.getDetailConvert());
        if (importBillClass != null && importBillClass.getTopClass().equals(importBillInfo.getTopClass()) &&
                importBillClass.getSecondClass().equals(importBillInfo.getSecondClass())) {
            return;
        }

        if (importBillClass != null) {
            LambdaUpdateWrapper<ImportBillClass> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.set(ImportBillClass::getTopClass, importBillInfo.getTopClass());
            queryWrapper.set(ImportBillClass::getSecondClass, importBillInfo.getSecondClass());
            queryWrapper.eq(ImportBillClass::getDetail, importBillInfo.getDetailConvert());

            importBillClassMapper.update(null, queryWrapper);
            return;
        }

        importBillClass = BeanUtils.copyProperties(importBillInfo, ImportBillClass.class);
        importBillClass.setDetail(importBillInfo.getDetailConvert());
        importBillClass.setGmtModified(null);

        importBillClassMapper.insert(importBillClass);
    }
}
