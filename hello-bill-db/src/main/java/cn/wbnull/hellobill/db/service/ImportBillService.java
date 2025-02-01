package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.db.entity.ImportBillInfo;
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
}
