package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.expend.*;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUsername, request.getUsername());
        queryWrapper.like(ExpendInfo::getTopClass, request.getTopClass() == null ? "" : request.getTopClass());
        queryWrapper.like(ExpendInfo::getSecondClass, request.getSecondClass() == null ? "" : request.getSecondClass());
        queryWrapper.like(ExpendInfo::getDetail, request.getDetail() == null ? "" : request.getDetail());
        if (!StringUtils.isEmpty(request.getBeginTime())) {
            queryWrapper.ge(ExpendInfo::getExpendTime, request.getBeginTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(request.getEndTime())) {
            queryWrapper.le(ExpendInfo::getExpendTime, request.getEndTime() + " 23:59:59");
        }
        if (request.orderByDesc()) {
            queryWrapper.orderByDesc(ExpendInfo::getExpendTime);
        } else {
            queryWrapper.orderByAsc(ExpendInfo::getExpendTime);
        }

        return expendInfoMapper.selectList(queryWrapper);
    }

    public void addExpendInfo(AddRequestModel request) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, request.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, TypeEnum.EXPEND.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        ExpendInfo expendInfo = ExpendInfo.build(request, classInfo.getTopClass());
        expendInfoMapper.insert(expendInfo);
    }

    public ExpendInfo getExpendInfo(QueryRequestModel request) {
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUuid, request.getUuid());

        return expendInfoMapper.selectOne(queryWrapper);
    }

    public void updateExpendInfo(UpdateRequestModel request) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, request.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, TypeEnum.EXPEND.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<ExpendInfo> expendInfoWrapper = new LambdaQueryWrapper<>();
        expendInfoWrapper.eq(ExpendInfo::getUuid, request.getUuid());

        ExpendInfo expendInfo = expendInfoMapper.selectOne(expendInfoWrapper);
        expendInfo.update(request, classInfo.getTopClass());
        expendInfoMapper.update(expendInfo, expendInfoWrapper);
    }

    public void deleteExpendInfo(DeleteRequestModel request) {
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUuid, request.getUuid());
        queryWrapper.eq(ExpendInfo::getUsername, request.getUsername());

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

    public List<ExpendInfo> getExpendInfoByClass(String username, String reportDate, String topClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("secondClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.eq("topClass", topClass);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("secondClass");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> getExpendInfoByDetail(String username, String reportDate, String topClass,
                                                  String secondClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.eq("topClass", topClass);
        queryWrapper.eq("secondClass", secondClass);
        queryWrapper.like("DATE_FORMAT(expendTime, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("detail");
        queryWrapper.orderByDesc("amount");

        return expendInfoMapper.selectList(queryWrapper);
    }
}
