package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.common.core.util.DateUtils;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.constants.ExpendConstants;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import cn.wbnull.hellobill.db.param.QueryListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public ExpendInfo getEarliestByUsername(String username) {
        LambdaQueryWrapper<ExpendInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpendInfo::getUsername, username);
        queryWrapper.orderByAsc(ExpendInfo::getExpendTime);
        queryWrapper.last("LIMIT 1");

        return expendInfoMapper.selectOne(queryWrapper);
    }

    public ExpendInfo getByExpendTime(String username, LocalDate expendTime) {
        LocalDateTime startDate = expendTime.atStartOfDay();
        LocalDateTime endDate = expendTime.plusMonths(1).atStartOfDay();

        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(amount) as amount")
                .lambda()
                .eq(ExpendInfo::getUsername, username)
                .ge(ExpendInfo::getExpendTime, startDate)
                .lt(ExpendInfo::getExpendTime, endDate);

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
        queryWrapper.notIn("top_class", ExpendConstants.EXCLUDED_EXPEND_CLASSES);
        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y')");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y')");

        return expendInfoMapper.selectList(queryWrapper);
    }

    public List<ExpendInfo> listReportByDateAndClass(String username, String reportDate, String reportClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(expend_time, '%Y-%m') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);

        addDateAndClassConditions(queryWrapper, reportDate, reportClass);

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

        addDateAndClassConditions(queryWrapper, reportDate, reportClass);

        queryWrapper.groupBy("DATE_FORMAT(expend_time, '%Y-%m'), top_class, second_class");
        queryWrapper.orderByAsc("DATE_FORMAT(expend_time, '%Y-%m'), second_class");

        return expendInfoMapper.selectList(queryWrapper);
    }

    private void addDateAndClassConditions(QueryWrapper<ExpendInfo> queryWrapper, String reportDate, String reportClass) {
        if (StringUtils.isEmpty(reportDate)) {
            queryWrapper.ge("expend_time", DateUtils.atStartOfMonth(11).atStartOfDay());
        } else {
            queryWrapper.apply("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s') LIKE {0}", reportDate + "%");
        }

        switch (reportClass) {
            case "1":
                queryWrapper.eq("top_class", ExpendConstants.CLASS_DAILY);
                break;
            case "2":
                queryWrapper.eq("top_class", ExpendConstants.CLASS_LIFE);
                break;
            case "3":
                queryWrapper.eq("top_class", ExpendConstants.CLASS_CHILDREN);
                break;
            case "4":
                queryWrapper.eq("top_class", ExpendConstants.CLASS_CHILDREN_EDU);
                break;
            default:
                break;
        }
    }

    public List<ExpendInfo> listAmountByDateAndClass(String username, String reportDate, String topClass) {
        QueryWrapper<ExpendInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("second_class, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.eq("top_class", topClass);
        queryWrapper.apply("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s') LIKE {0}", reportDate + "%");
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

        queryWrapper.apply("DATE_FORMAT(expend_time, '%Y-%m-%d %H:%i:%s') LIKE {0}", reportDate + "%");
        queryWrapper.groupBy("detail");
        queryWrapper.orderByDesc("amount");

        return expendInfoMapper.selectList(queryWrapper);
    }
}
