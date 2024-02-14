package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        LambdaQueryWrapper<IncomeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IncomeInfo::getUsername, request.getUsername());
        queryWrapper.like(IncomeInfo::getSecondClass, request.getSecondClass() == null ? "" : request.getSecondClass());
        queryWrapper.like(IncomeInfo::getDetail, request.getDetail() == null ? "" : request.getDetail());
        if (!StringUtils.isEmpty(request.getBeginDate())) {
            queryWrapper.ge(IncomeInfo::getIncomeDate, request.getBeginDate());
        }
        if (!StringUtils.isEmpty(request.getBeginDate())) {
            queryWrapper.le(IncomeInfo::getIncomeDate, request.getEndDate());
        }
        if (request.orderByDesc()) {
            queryWrapper.orderByDesc(IncomeInfo::getIncomeDate);
        } else {
            queryWrapper.orderByAsc(IncomeInfo::getIncomeDate);
        }

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public void addIncomeInfo(AddRequestModel request) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, request.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, TypeEnum.INCOME.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        IncomeInfo incomeInfo = IncomeInfo.build(request, classInfo.getTopClass());
        incomeInfoMapper.insert(incomeInfo);
    }

    public IncomeInfo getIncomeInfo(QueryRequestModel request) {
        LambdaQueryWrapper<IncomeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IncomeInfo::getUuid, request.getUuid());

        return incomeInfoMapper.selectOne(queryWrapper);
    }

    public void updateIncomeInfo(UpdateRequestModel request) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, request.getSecondClass());
        queryWrapper.eq(ClassInfo::getType, TypeEnum.INCOME.getTypeCode());
        ClassInfo classInfo = classInfoMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<IncomeInfo> incomeInfoWrapper = new LambdaQueryWrapper<>();
        incomeInfoWrapper.eq(IncomeInfo::getUuid, request.getUuid());

        IncomeInfo incomeInfo = incomeInfoMapper.selectOne(incomeInfoWrapper);
        incomeInfo.update(request, classInfo.getTopClass());
        incomeInfoMapper.update(incomeInfo, incomeInfoWrapper);
    }

    public void deleteIncomeInfo(DeleteRequestModel request) {
        LambdaQueryWrapper<IncomeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IncomeInfo::getUuid, request.getUuid());
        queryWrapper.eq(IncomeInfo::getUsername, request.getUsername());

        incomeInfoMapper.delete(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByClass(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportBySecondClass(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, secondClass, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.like("DATE_FORMAT(incomeDate, '%Y-%m-%d %H:%i:%s')", reportDate);
        queryWrapper.groupBy("topClass, secondClass");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByDate(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("topClass, DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("topClass", "DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportByDateSum(String username, String reportDate) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
    }

    public List<IncomeInfo> getIncomeReportSum(String username) {
        QueryWrapper<IncomeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DATE_FORMAT(incomeDate, '%Y') as remark, sum(amount) as amount");
        queryWrapper.eq("username", username);
        queryWrapper.groupBy("DATE_FORMAT(incomeDate, '%Y')");

        return incomeInfoMapper.selectList(queryWrapper);
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
