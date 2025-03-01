package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.model.common.QueryListRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 收入信息表 服务类
 *
 * @author null  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class IncomeInfoService {

    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<IncomeInfo> getIncomeInfos(String username, QueryListRequestModel request) {
        LambdaQueryWrapper<IncomeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IncomeInfo::getUsername, username);
        queryWrapper.like(!StringUtils.isEmpty(request.getSecondClass()), IncomeInfo::getSecondClass, request.getSecondClass());
        queryWrapper.like(!StringUtils.isEmpty(request.getDetail()), IncomeInfo::getDetail, request.getDetail());
        queryWrapper.ge(!StringUtils.isEmpty(request.getBeginDate()), IncomeInfo::getIncomeDate, request.getBeginDate());
        queryWrapper.le(!StringUtils.isEmpty(request.getBeginDate()), IncomeInfo::getIncomeDate, request.getEndDate());
        queryWrapper.orderBy(true, request.orderByAsc(), IncomeInfo::getIncomeDate, IncomeInfo::getId);

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public void addIncomeInfo(String username, IncomeInfo incomeInfo) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, incomeInfo.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, ClassTypeEnum.INCOME.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        incomeInfo.build(username, classInfo.getTopClass());
        incomeInfoMapper.insert(incomeInfo);
    }

    public IncomeInfo getIncomeInfo(String id) {
        return incomeInfoMapper.selectById(id);
    }

    public void updateIncomeInfo(IncomeInfo incomeInfo) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, incomeInfo.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, ClassTypeEnum.INCOME.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        incomeInfo.setTopClass(classInfo.getTopClass());

        incomeInfoMapper.updateById(incomeInfo);
    }

    public void deleteIncomeInfo(String id) {
        incomeInfoMapper.deleteById(id);
    }

    public List<IncomeInfo> getIncomeReport(String username) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportNet(String username) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        List<String> topClassList = new ArrayList<>();
        topClassList.add("人情往来");
        topClassList.add("父母补贴");
        queryWrapper.notIn("topClass", topClassList);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByClass(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, topClass, secondClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')", "topClass", "secondClass");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')", "secondClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    private void convertQueryWrapper(QueryWrapper<IncomeInfo> queryWrapper, String reportDate) {
        List<String> topClassList = new ArrayList<>();
        topClassList.add("工资收入");
        topClassList.add("职外收入");
        queryWrapper.in("topClass", topClassList);
        if (StringUtils.isEmpty(reportDate)) {
            queryWrapper.ge("incomeDate", DateUtils.atStartOfYear(4));
        } else {
            queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        }
    }

    public List<IncomeInfo> getIncomeInfoByClass(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeInfoByDetail(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("detail");
        queryWrapper.orderByDesc("amount");

        return incomeInfoMapper.selectList(queryWrapper);
    }
}
