package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.model.bill.AddRequestModel;
import cn.wbnull.hellobill.common.model.bill.InfoRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.BillInfo;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.mapper.BillInfoMapper;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
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

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<BillInfo> getBillInfos(InfoRequestModel request) {
        QueryWrapper<BillInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.like("topClass", request.getTopClass() == null ? "" : request.getTopClass());
        queryWrapper.like("secondClass", request.getSecondClass() == null ? "" : request.getSecondClass());
        queryWrapper.like("detail", request.getDetail() == null ? "" : request.getDetail());
        if (!StringUtils.isEmpty(request.getBeginTime())) {
            queryWrapper.ge("billTime", request.getBeginTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(request.getEndTime())) {
            queryWrapper.le("billTime", request.getEndTime() + " 23:59:59");
        }

        return billInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getClassInfos() {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct secondClass");

        return classInfoMapper.selectList(queryWrapper);
    }

    public void addBillInfo(AddRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        BillInfo billInfo = BillInfo.build(request, classInfo.getTopClass());
        billInfoMapper.insert(billInfo);
    }
}
