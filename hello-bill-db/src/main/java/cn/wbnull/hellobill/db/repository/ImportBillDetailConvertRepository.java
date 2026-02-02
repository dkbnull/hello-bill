package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ImportBillDetailConvert;
import cn.wbnull.hellobill.db.entity.ImportBillInfo;
import cn.wbnull.hellobill.db.mapper.ImportBillDetailConvertMapper;
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
public class ImportBillDetailConvertRepository {

    @Autowired
    private ImportBillDetailConvertMapper importBillDetailConvertMapper;

    public ImportBillDetailConvert getByDetail(String detail) {
        LambdaQueryWrapper<ImportBillDetailConvert> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillDetailConvert::getDetail, detail);
        queryWrapper.orderByDesc(ImportBillDetailConvert::getGmtCreate);
        queryWrapper.last("limit 1");

        return importBillDetailConvertMapper.selectOne(queryWrapper);
    }

    public void updateByImportBillInfo(ImportBillInfo importBillInfo) {
        if (importBillInfo.getDetail().equals(importBillInfo.getDetailConvert())) {
            return;
        }

        ImportBillDetailConvert importBillDetailConvert = getByDetail(importBillInfo.getDetail());
        if (importBillDetailConvert != null && importBillDetailConvert.getDetailConvert()
                .equals(importBillInfo.getDetailConvert())) {
            return;
        }

        if (importBillDetailConvert != null) {
            LambdaUpdateWrapper<ImportBillDetailConvert> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.set(ImportBillDetailConvert::getDetailConvert, importBillInfo.getDetailConvert());
            queryWrapper.eq(ImportBillDetailConvert::getDetail, importBillInfo.getDetail());

            importBillDetailConvertMapper.update(queryWrapper);
            return;
        }

        importBillDetailConvert = BeanUtils.copyProperties(importBillInfo, ImportBillDetailConvert.class);
        importBillDetailConvert.setGmtModified(null);

        importBillDetailConvertMapper.insert(importBillDetailConvert);
    }
}
