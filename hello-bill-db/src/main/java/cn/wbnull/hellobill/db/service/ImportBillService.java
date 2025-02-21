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
        queryWrapper.set(ImportBillInfo::getDetail, importBillInfo.getDetail());
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

    public ImportBillDetailConvert getImportBillDetailConvert(String detail) {
        LambdaQueryWrapper<ImportBillDetailConvert> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillDetailConvert::getDetail, detail);
        queryWrapper.orderByDesc(ImportBillDetailConvert::getUpdateTime);
        queryWrapper.last("limit 1");

        return importBillDetailConvertMapper.selectOne(queryWrapper);
    }

    public void updateImportBillDetailConvert(ImportBillInfo importBillInfo) {
        ImportBillInfo importBillInfoTemp = importBillInfoMapper.selectById(importBillInfo.getId());
        if (importBillInfoTemp.getDetail().equals(importBillInfo.getDetail())) {
            return;
        }

        ImportBillDetailConvert importBillDetailConvert = BeanUtils.copyProperties(importBillInfoTemp, ImportBillDetailConvert.class);
        importBillDetailConvert.setDetailConvert(importBillInfo.getDetail());

        importBillDetailConvertMapper.insertOrUpdate(importBillDetailConvert);
    }
}
