package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.model.expend.AddRequestModel;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支出信息表 服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ExpendInfoService {

    @Autowired
    private ExpendInfoMapper expendInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<ExpendInfo> getExpendInfos(QueryRequestModel request) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.like("topClass", request.getTopClass() == null ? "" : request.getTopClass());
        queryWrapper.like("secondClass", request.getSecondClass() == null ? "" : request.getSecondClass());
        queryWrapper.like("detail", request.getDetail() == null ? "" : request.getDetail());
        if (!StringUtils.isEmpty(request.getBeginTime())) {
            queryWrapper.ge("expendTime", request.getBeginTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(request.getEndTime())) {
            queryWrapper.le("expendTime", request.getEndTime() + " 23:59:59");
        }
        queryWrapper.orderByAsc("expendTime");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public void addExpendInfo(AddRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        ExpendInfo expendInfo = ExpendInfo.build(request, classInfo.getTopClass());
        expendInfoMapper.insert(expendInfo);
    }

    public void deleteExpendInfo(DeleteRequestModel request) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", request.getUuid());
        queryWrapper.eq("username", request.getUsername());

        expendInfoMapper.delete(queryWrapper);
    }
}
