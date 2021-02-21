package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.expend.ReportRequestModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收入信息表 服务类
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class IncomeInfoService {

    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<IncomeInfo> getIncomeInfos(QueryListRequestModel request) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.like("secondClass", request.getSecondClass() == null ? "" : request.getSecondClass());
        queryWrapper.like("detail", request.getDetail() == null ? "" : request.getDetail());
        if (!StringUtils.isEmpty(request.getBeginDate())) {
            queryWrapper.ge("incomeDate", request.getBeginDate());
        }
        if (!StringUtils.isEmpty(request.getBeginDate())) {
            queryWrapper.le("incomeDate", request.getEndDate());
        }
        if (request.orderByDesc()) {
            queryWrapper.orderByDesc("incomeDate");
        } else {
            queryWrapper.orderByAsc("incomeDate");
        }

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public void addIncomeInfo(AddRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        queryWrapper.eq("type", ClassInfo.TYPE_INCOME);
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        IncomeInfo incomeInfo = IncomeInfo.build(request, classInfo.getTopClass());
        incomeInfoMapper.insert(incomeInfo);
    }

    public IncomeInfo getIncomeInfo(QueryRequestModel request) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", request.getUuid());

        return incomeInfoMapper.selectOne(queryWrapper);
    }

    public void updateIncomeInfo(UpdateRequestModel request) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secondClass", request.getSecondClass());
        queryWrapper.eq("type", ClassInfo.TYPE_INCOME);
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        QueryWrapper<IncomeInfo> incomeInfoWrapper = new QueryWrapper<>();
        incomeInfoWrapper.eq("uuid", request.getUuid());

        IncomeInfo incomeInfo = incomeInfoMapper.selectOne(incomeInfoWrapper);
        incomeInfo.update(request, classInfo.getTopClass());
        incomeInfoMapper.update(incomeInfo, incomeInfoWrapper);
    }

    public void deleteIncomeInfo(DeleteRequestModel request) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", request.getUuid());
        queryWrapper.eq("username", request.getUsername());

        incomeInfoMapper.delete(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByClass(ReportRequestModel request) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("secondClass, sum(amount) as amount");
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", request.getReportDate());
        queryWrapper.groupBy("secondClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByDate(ReportRequestModel request) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("secondClass, DATE_FORMAT(incomeDate, '%Y-%m') as remark, sum(amount) as amount");
        queryWrapper.eq("username", request.getUsername());
        queryWrapper.groupBy("secondClass", "DATE_FORMAT(incomeDate, '%Y-%m')");

        return incomeInfoMapper.selectList(queryWrapper);
    }
}
