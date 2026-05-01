package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.db.entity.ImportBillInfo;
import cn.wbnull.hellobill.db.mapper.ImportBillInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class ImportBillInfoRepository {

    @Autowired
    private ImportBillInfoMapper importBillInfoMapper;

    public IPage<ImportBillInfo> pageByParam(String username, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ImportBillInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImportBillInfo::getUsername, username);

        Page<ImportBillInfo> page = new Page<>(pageNum, pageSize);
        return importBillInfoMapper.selectPage(page, queryWrapper);
    }

    public void updateImportBillInfo(ImportBillInfo importBillInfo) {
        LambdaUpdateWrapper<ImportBillInfo> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(ImportBillInfo::getTopClass, importBillInfo.getTopClass());
        queryWrapper.set(ImportBillInfo::getSecondClass, importBillInfo.getSecondClass());
        queryWrapper.set(ImportBillInfo::getDetailConvert, importBillInfo.getDetailConvert());
        queryWrapper.set(ImportBillInfo::getAmount, importBillInfo.getAmount());
        queryWrapper.set(ImportBillInfo::getRemark, importBillInfo.getRemark());
        queryWrapper.eq(ImportBillInfo::getId, importBillInfo.getId());

        importBillInfoMapper.update(queryWrapper);
    }
}
