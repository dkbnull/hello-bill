package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.util.BeanUtils;
import cn.wbnull.hellobill.db.entity.ImportBillClass;
import cn.wbnull.hellobill.db.entity.ImportBillDetailConvert;
import cn.wbnull.hellobill.db.entity.ImportBillInfo;
import cn.wbnull.hellobill.db.mapper.ImportBillClassMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillDetailConvertMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导入账单信息表服务
 *
 * @author null  2025-01-25
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ImportBillService {

    @Autowired
    private ImportBillInfoMapper importBillInfoMapper;
    @Autowired
    private ImportBillClassMapper importBillClassMapper;
    @Autowired
    private ImportBillDetailConvertMapper importBillDetailConvertMapper;

    public List<ImportBillInfo> getImportBillInfos(String username) {
        LambdaQueryWrapper<ImportBillInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillInfo::getUsername, username);

        return importBillInfoMapper.selectList(queryWrapper);
    }

    public void updateImportBillInfo(ImportBillInfo importBillInfo) {
        LambdaUpdateWrapper<ImportBillInfo> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(ImportBillInfo::getTopClass, importBillInfo.getTopClass());
        queryWrapper.set(ImportBillInfo::getSecondClass, importBillInfo.getSecondClass());
        queryWrapper.set(ImportBillInfo::getDetailConvert, importBillInfo.getDetailConvert());
        queryWrapper.set(ImportBillInfo::getAmount, importBillInfo.getAmount());
        queryWrapper.set(ImportBillInfo::getRemark, importBillInfo.getRemark());
        queryWrapper.eq(ImportBillInfo::getId, importBillInfo.getId());

        importBillInfoMapper.update(null, queryWrapper);
    }

    public ImportBillClass getImportBillClass(String detail) {
        LambdaQueryWrapper<ImportBillClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillClass::getDetail, detail);
        queryWrapper.orderByDesc(ImportBillClass::getUpdateTime);
        queryWrapper.last("limit 1");

        return importBillClassMapper.selectOne(queryWrapper);
    }

    public void updateImportBillClass(ImportBillInfo importBillInfo) {
        ImportBillClass importBillClass = getImportBillClass(importBillInfo.getDetailConvert());
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
        importBillClass.setUpdateTime(null);

        importBillClassMapper.insert(importBillClass);
    }

    public ImportBillDetailConvert getImportBillDetailConvert(String detail) {
        LambdaQueryWrapper<ImportBillDetailConvert> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillDetailConvert::getDetail, detail);
        queryWrapper.orderByDesc(ImportBillDetailConvert::getUpdateTime);
        queryWrapper.last("limit 1");

        return importBillDetailConvertMapper.selectOne(queryWrapper);
    }

    public void updateImportBillDetailConvert(ImportBillInfo importBillInfo) {
        if (importBillInfo.getDetail().equals(importBillInfo.getDetailConvert())) {
            return;
        }

        ImportBillDetailConvert importBillDetailConvert = getImportBillDetailConvert(importBillInfo.getDetail());
        if (importBillDetailConvert != null && importBillDetailConvert.getDetailConvert()
                .equals(importBillInfo.getDetailConvert())) {
            return;
        }

        if (importBillDetailConvert != null) {
            LambdaUpdateWrapper<ImportBillDetailConvert> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.set(ImportBillDetailConvert::getDetailConvert, importBillInfo.getDetailConvert());
            queryWrapper.eq(ImportBillDetailConvert::getDetail, importBillInfo.getDetail());

            importBillDetailConvertMapper.update(null, queryWrapper);
            return;
        }

        importBillDetailConvert = BeanUtils.copyProperties(importBillInfo, ImportBillDetailConvert.class);
        importBillDetailConvert.setUpdateTime(null);

        importBillDetailConvertMapper.insert(importBillDetailConvert);
    }
}
