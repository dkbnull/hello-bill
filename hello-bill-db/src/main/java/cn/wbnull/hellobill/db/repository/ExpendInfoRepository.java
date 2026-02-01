package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.util.DateUtils;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import cn.wbnull.hellobill.db.param.QueryListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 支出信息表 服务类
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Repository
public class ExpendInfoRepository {

    @Autowired
    private ExpendInfoMapper expendInfoMapper;

    public List<ExpendInfo> listByParam(String username, QueryListParam param) {
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUsername, username);
        queryWrapper.like(!StringUtils.isEmpty(param.getTopClass()), ExpendInfo::getTopClass,
                param.getTopClass());
        queryWrapper.like(!StringUtils.isEmpty(param.getSecondClass()), ExpendInfo::getSecondClass,
                param.getSecondClass());
        queryWrapper.like(!StringUtils.isEmpty(param.getDetail()), ExpendInfo::getDetail,
                param.getDetail());
        queryWrapper.ge(!StringUtils.isEmpty(param.getBeginDate()), ExpendInfo::getExpendTime,
                param.getBeginDate() + " 00:00:00");
        queryWrapper.le(!StringUtils.isEmpty(param.getEndDate()), ExpendInfo::getExpendTime,
                param.getEndDate() + " 23:59:59");
        queryWrapper.orderBy(true, param.orderByAsc(), ExpendInfo::getExpendTime, ExpendInfo::getId);

        return expendInfoMapper.selectList(queryWrapper);
    }

    public void insertExpendInfo(ExpendInfo expendInfo) {
        expendInfoMapper.insert(expendInfo);
    }

    public ExpendInfo getById(String id) {
        return expendInfoMapper.selectById(id);
    }

    public void updateExpendInfo(ExpendInfo expendInfo) {
        expendInfoMapper.updateById(expendInfo);
    }

    public void deleteById(String id) {
        expendInfoMapper.deleteById(id);
    }

    public ExpendInfo getEarliestByUsername(String username) {
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUsername, username);
        queryWrapper.orderByAsc(ExpendInfo::getExpendTime);
        queryWrapper.last("LIMIT 1");

        return expendInfoMapper.selectOne(queryWrapper);
    }

    public ExpendInfo getByExpendTime(String username, LocalDate expendTime) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(amount) as amount").lambda()
                .eq(ExpendInfo::getUsername, username)
                .ge(ExpendInfo::getExpendTime, expendTime.atStartOfDay())
                .lt(ExpendInfo::getExpendTime, expendTime.plusMonths(1).atStartOfDay());

        return expendInfoMapper.selectOne(queryWrapper);
    }

    public List<ExpendInfo> listReport(String username) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expend_time, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> listReportNet(String username) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expend_time, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        List<String> topClasses = new ArrayList<>();
        topClasses.add("人情往来");
        topClasses.add("五险一金");
        queryWrapper.notIn("top_class", topClasses);
        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> listReportByDateAndClass(String username, String reportDate, String reportClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expend_time, '%Y-%m') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate, reportClass);
        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y-%m')");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y-%m')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> listReportWithClassByDateAndClass(String username, String reportDate,
                                                              String reportClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expend_time, '%Y-%m') as remark, top_class, second_class, " +
                "sum(amount) as amount");
        queryWrapper.eq("username", username);
        convertQueryWrapper(queryWrapper, reportDate, reportClass);
        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y-%m')", "top_class", "second_class");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y-%m')", "second_class");

        return expendInfoMapper.selectList(queryWrapper);
    }

    private void convertQueryWrapper(QueryWrapper<ExpendInfo> queryWrapper, String reportDate, String reportClass) {
        if (StringUtils.isEmpty(reportDate)) {
            queryWrapper.ge("expend_time", DateUtils.atStartOfMonth(11).atStartOfDay());
        } else {
            queryWrapper.like("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s')", reportDate);
        }

        if ("1".equals(reportClass)) {
            queryWrapper.eq("top_class", "日常支出");
        }
        if ("2".equals(reportClass)) {
            queryWrapper.eq("top_class", "生活支出");
        }
        if ("3".equals(reportClass)) {
            queryWrapper.eq("top_class", "子女支出");
        }
        if ("4".equals(reportClass)) {
            queryWrapper.eq("top_class", "子女教育");
        }
    }

    public List<ExpendInfo> listAmountByDateAndClass(String username, String reportDate, String topClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("second_class, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.eq("top_class", topClass);
        queryWrapper.like("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("second_class");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> listDetailByDateAndClass(String username, String reportDate, String topClass,
                                                     String secondClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.eq("top_class", topClass);
        if (!StringUtils.isEmpty(secondClass)) {
            queryWrapper.eq("second_class", secondClass);
        }
        queryWrapper.like("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("detail");
        queryWrapper.orderByDesc("amount");

        return expendInfoMapper.selectList(queryWrapper);
    }
}
