package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.report.*;
import cn.wbnull.hellobill.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author null  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private ExpendInfoService expendInfoService;

    @Autowired
    private IncomeInfoService incomeInfoService;

    @Override
    public ResponseModel<QueryResponseModel> query(RequestModel<Object> request) {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendReport(request.getUsername());
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeReport(request.getUsername());

        return ResponseModel.success(QueryResponseModel.build(expendInfos, incomeInfos));
    }

    @Override
    public ResponseModel<QueryResponseModel> queryNet(RequestModel<Object> request) {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendReportNet(request.getUsername());
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeReportNet(request.getUsername());

        return ResponseModel.success(QueryResponseModel.build(expendInfos, incomeInfos));
    }

    @Override
    public ResponseModel<Object> expend(RequestModel<ReportRequestModel> request) {
        ReportRequestModel data = request.getData();

        List<ExpendInfo> expendInfosDate = expendInfoService.getExpendReportByDate(request.getUsername(),
                data.getReportDate(), data.getReportClass());
        List<ExpendInfo> expendInfosClass = expendInfoService.getExpendReportByClass(request.getUsername(),
                data.getReportDate(), data.getReportClass());

        List<String> date = getReportDateMonth(data.getReportDate());

        ReportResponseModel<ExpendInfo> response = ReportResponseModel.buildExpend(date, expendInfosDate, expendInfosClass);

        return ResponseModel.success(response);
    }

    private List<String> getReportDateMonth(String reportDate) {
        YearMonth beginMonth;
        YearMonth endMonth;
        if (StringUtils.isEmpty(reportDate)) {
            beginMonth = YearMonth.now().minusMonths(11);
            endMonth = YearMonth.now();
        } else {
            beginMonth = YearMonth.of(Integer.parseInt(reportDate), 1);
            endMonth = YearMonth.of(Integer.parseInt(reportDate), 12);
        }

        List<String> date = new ArrayList<>();
        while (!beginMonth.isAfter(endMonth)) {
            date.add(beginMonth.toString());
            beginMonth = beginMonth.plusMonths(1);
        }

        return date;
    }

    @Override
    public ResponseModel<Object> income(RequestModel<ReportRequestModel> request) {
        ReportRequestModel data = request.getData();

        List<IncomeInfo> incomeReportDate = incomeInfoService.getIncomeReportByDate(request.getUsername(),
                data.getReportDate());
        List<IncomeInfo> incomeInfosClass = incomeInfoService.getIncomeReportByClass(request.getUsername(),
                data.getReportDate());

        List<String> date = getReportDateYear(data.getReportDate());

        ReportResponseModel<IncomeInfo> response = ReportResponseModel.buildIncome(date, incomeReportDate,
                incomeInfosClass);

        return ResponseModel.success(response);
    }

    private List<String> getReportDateYear(String reportDate) {
        Year beginYear = StringUtils.isEmpty(reportDate) ?
                Year.now().minusYears(4) : Year.of(Integer.parseInt(reportDate));
        Year endYear = Year.now();
        List<String> date = new ArrayList<>();
        while (!beginYear.isAfter(endYear)) {
            date.add(beginYear.toString());
            beginYear = beginYear.plusYears(1);
        }

        return date;
    }

    @Override
    public ResponseModel<ExpendClassResponseModel> expendClass(RequestModel<ExpendClassRequestModel> request) {
        ExpendClassRequestModel data = request.getData();

        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfoByClass(request.getUsername(),
                data.getReportDate(), data.getTopClass());

        List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(ClassTypeEnum.EXPEND.getTypeCode(),
                data.getTopClass());

        return ResponseModel.success(ExpendClassResponseModel.build(classInfos, expendInfos));
    }

    @Override
    public ResponseModel<ExpendDetailResponseModel> expendDetail(RequestModel<ExpendDetailRequestModel> request) {
        ExpendDetailRequestModel data = request.getData();

        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfoByDetail(request.getUsername(),
                data.getReportDate(), data.getTopClass(), data.getSecondClass());

        return ResponseModel.success(ExpendDetailResponseModel.build(expendInfos));
    }

    @Override
    public ResponseModel<ExpendClassResponseModel> incomeClass(RequestModel<ReportRequestModel> request) {
        ReportRequestModel data = request.getData();

        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfoByClass(request.getUsername(),
                data.getReportDate());

        List<ClassInfo> classInfos = classInfoService.getTopClassInfos(ClassTypeEnum.INCOME.getTypeCode());

        return ResponseModel.success(ExpendClassResponseModel.buildIncome(classInfos, incomeInfos));
    }

    @Override
    public ResponseModel<ExpendDetailResponseModel> incomeDetail(RequestModel<ReportRequestModel> request) {
        ReportRequestModel data = request.getData();

        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfoByDetail(request.getUsername(),
                data.getReportDate());

        return ResponseModel.success(ExpendDetailResponseModel.buildIncome(incomeInfos));
    }
}
