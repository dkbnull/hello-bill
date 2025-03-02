package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.util.DateUtils;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import cn.wbnull.hellobill.db.param.QueryListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 收入信息表 服务类
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Repository
public class IncomeInfoRepository {

    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<IncomeInfo> listByParam(String username, QueryListParam param) {
        LambdaQueryWrapper<IncomeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IncomeInfo::getUsername, username);
        queryWrapper.like(!StringUtils.isEmpty(param.getSecondClass()), IncomeInfo::getSecondClass, param.getSecondClass());
        queryWrapper.like(!StringUtils.isEmpty(param.getDetail()), IncomeInfo::getDetail, param.getDetail());
        queryWrapper.ge(!StringUtils.isEmpty(param.getBeginDate()), IncomeInfo::getIncomeDate, param.getBeginDate());
        queryWrapper.le(!StringUtils.isEmpty(param.getBeginDate()), IncomeInfo::getIncomeDate, param.getEndDate());
        queryWrapper.orderBy(true, param.orderByAsc(), IncomeInfo::getIncomeDate, IncomeInfo::getId);

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public void insertIncomeInfo(String username, IncomeInfo incomeInfo) {
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

    public List<IncomeInfo> listReport(String username) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> listReportNet(String username) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        List<String> topClasses = new ArrayList<>();
        topClasses.add("人情往来");
        topClasses.add("父母补贴");
        queryWrapper.notIn("topClass", topClasses);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> listReportByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> listReportWithClassByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, topClass, secondClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')", "topClass", "secondClass");
        queryWrapper.orderByAsc("DATE_FORMAT(incomeDate, '%Y')", "secondClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    private void convertQueryWrapper(QueryWrapper<IncomeInfo> queryWrapper, String reportDate) {
        List<String> topClasses = new ArrayList<>();
        topClasses.add("工资收入");
        topClasses.add("职外收入");
        queryWrapper.in("topClass", topClasses);
        if (StringUtils.isEmpty(reportDate)) {
            queryWrapper.ge("incomeDate", DateUtils.atStartOfYear(4));
        } else {
            queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        }
    }

    public List<IncomeInfo> listAmountByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> listDetailByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("detail");
        queryWrapper.orderByDesc("amount");

        return incomeInfoMapper.selectList(queryWrapper);
    }
}
