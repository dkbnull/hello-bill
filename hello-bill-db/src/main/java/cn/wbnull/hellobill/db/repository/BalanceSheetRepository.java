package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.util.SnowflakeUtils;
import cn.wbnull.hellobill.db.entity.BalanceSheet;
import cn.wbnull.hellobill.db.mapper.BalanceSheetMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 资产负债信息表
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Repository
public class BalanceSheetRepository {

    @Autowired
    private BalanceSheetMapper balanceSheetMapper;

    public void save(BalanceSheet balanceSheet) {
        long id = SnowflakeUtils.getInstance().nextId();
        balanceSheet.setId(id);

        balanceSheetMapper.insert(balanceSheet);
    }

    public BalanceSheet getLastByUsername(String username) {
        LambdaQueryWrapper<BalanceSheet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BalanceSheet::getUsername, username);
        queryWrapper.orderByDesc(BalanceSheet::getBalanceDate);
        queryWrapper.last("LIMIT 1");

        return balanceSheetMapper.selectOne(queryWrapper);
    }

    public IPage<BalanceSheet> pageByParam(String username, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<BalanceSheet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BalanceSheet::getUsername, username);
        queryWrapper.orderByDesc(BalanceSheet::getId);

        Page<BalanceSheet> page = new Page<>(pageNum, pageSize);
        return balanceSheetMapper.selectPage(page, queryWrapper);
    }
}
