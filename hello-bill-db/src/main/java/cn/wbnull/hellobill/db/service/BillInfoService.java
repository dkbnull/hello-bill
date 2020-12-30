package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.BillInfo;
import cn.wbnull.hellobill.db.mapper.BillInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账单信息表 服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class BillInfoService {

    @Autowired
    private BillInfoMapper billInfoMapper;

    public List<BillInfo> getBillInfos(String username, String topClass, String secondClass, String detail,
                                       String beginTime, String endTime) {
        QueryWrapper<BillInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.like("topClass", topClass == null ? "" : topClass);
        queryWrapper.like("secondClass", secondClass == null ? "" : secondClass);
        queryWrapper.like("detail", detail == null ? "" : detail);
        if (!StringUtils.isEmpty(beginTime)) {
            queryWrapper.gt("billTime", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            queryWrapper.lt("billTime", endTime);
        }

        return billInfoMapper.selectList(queryWrapper);
    }
}
