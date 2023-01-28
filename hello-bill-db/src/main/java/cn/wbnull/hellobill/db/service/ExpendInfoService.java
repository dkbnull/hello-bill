package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.expend.*;
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

    public List<ExpendInfo> getExpendInfos(QueryListRequestModel request) {
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
        if (request.orderByDesc()) {
            queryWrapper.orderByDesc("expendTime");
        } else {
            queryWrapper.orderByAsc("expendTime");
        }

        return expendInfoMapper.selectList(queryWrapper);
    }

    public void addExpendInfo(AddRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        queryWrapper.eq("type", TypeEnum.EXPEND.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        ExpendInfo expendInfo = ExpendInfo.build(request, classInfo.getTopClass());
        expendInfoMapper.insert(expendInfo);
    }

    public ExpendInfo getExpendInfo(QueryRequestModel request) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", request.getUuid());

        return expendInfoMapper.selectOne(queryWrapper);
    }

    public void updateExpendInfo(UpdateRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        queryWrapper.eq("type", TypeEnum.EXPEND.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        QueryWrapper<ExpendInfo> expendInfoWrapper = new QueryWrapper<>();
        expendInfoWrapper.eq("uuid", request.getUuid());

        ExpendInfo expendInfo = expendInfoMapper.selectOne(expendInfoWrapper);
        expendInfo.update(request, classInfo.getTopClass());
        expendInfoMapper.update(expendInfo, expendInfoWrapper);
    }

    public void deleteExpendInfo(DeleteRequestModel request) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", request.getUuid());
        queryWrapper.eq("username", request.getUsername());

        expendInfoMapper.delete(queryWrapper);
    }

    public List<ExpendInfo> getExpendReportByClass(String username, String reportDate) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> getExpendReportBySecondClass(String username, String reportDate) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, secondClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass, secondClass");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> getExpendReportByDate(String username, String reportDate) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, DATE_FORMAT(expendTime, '%Y-%m') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')",
                reportDate.substring(0, 4));
        queryWrapper.groupBy("topClass", "DATE_FORMAT(expendTime, '%Y-%m')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> getExpendReportByDateSum(String username, String reportDate) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expendTime, '%Y-%m') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')",
                reportDate.substring(0, 4));
        queryWrapper.groupBy("DATE_FORMAT(expendTime, '%Y-%m')");
        queryWrapper.orderByAsc("DATE_FORMAT(expendTime, '%Y-%m')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> getExpendReportSum(String username) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expendTime, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(expendTime, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(expendTime, '%Y')");

        return expendInfoMapper.selectList(queryWrapper);
    }
}
