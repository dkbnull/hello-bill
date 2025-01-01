package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.report.*;
import cn.wbnull.hellobill.service.ReportService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 支出信息接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
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

        List<ExpendInfo> expendInfos = expendInfoService.getExpendReportByClass(request.getUsername(),
                data.getReportDate());
        List<ExpendInfo> expendInfosSecond = expendInfoService.getExpendReportBySecondClass(request.getUsername(),
                data.getReportDate());

        ReportResponseModel<ExpendInfo> response = ReportResponseModel.buildExpend(expendInfos,
                expendInfosSecond);

        LocalDate beginDate = LocalDate.of(Integer.parseInt(data.getReportDate()), 1, 1);
        LocalDate localDate = LocalDate.now();
        LocalDate endDate;
        if (beginDate.getYear() == localDate.getYear()) {
            endDate = localDate;
        } else {
            endDate = LocalDate.of(Integer.parseInt(data.getReportDate()), 12, 31);
        }

        List<ExpendInfo> expendInfosDate = expendInfoService.getExpendReportByDate(request.getUsername(),
                data.getReportDate());
        JSONArray expendReport = analysisExpendReport(expendInfosDate, beginDate, endDate);

        response.setReportDate(expendReport);

        JSONArray date = new JSONArray();
        while (!beginDate.isAfter(endDate)) {
            date.add(beginDate.toString().substring(0, 7));
            beginDate = beginDate.plusMonths(1);
        }
        response.setDate(date);

        List<ExpendInfo> expendInfosDateSum = expendInfoService.getExpendReportByDateSum(request.getUsername(),
                data.getReportDate());
        JSONArray total = new JSONArray();
        for (ExpendInfo expendInfo : expendInfosDateSum) {
            total.add(expendInfo.getAmount());
        }
        response.setTotal(total);

        return ResponseModel.success(response);
    }

    private JSONArray analysisExpendReport(List<ExpendInfo> expendInfos, LocalDate beginDate,
                                           LocalDate endDate) {
        JSONObject reportData = new JSONObject();
        for (ExpendInfo expendInfo : expendInfos) {
            if (reportData.containsKey(expendInfo.getTopClass())) {
                JSONObject reportDataItem = reportData.getJSONObject(expendInfo.getTopClass());
                reportDataItem.put(expendInfo.getRemark(), expendInfo.getAmount());
            } else {
                JSONObject reportDataItem = new JSONObject();
                reportDataItem.put(expendInfo.getRemark(), expendInfo.getAmount());
                reportData.put(expendInfo.getTopClass(), reportDataItem);
            }
        }

        JSONArray expendReport = new JSONArray();
        for (String topClass : reportData.keySet()) {
            JSONArray expendReportItem = new JSONArray();
            JSONObject reportDataItem = reportData.getJSONObject(topClass);

            LocalDate beginDateTemp = LocalDate.ofEpochDay(beginDate.toEpochDay());
            while (!beginDateTemp.isAfter(endDate)) {
                String date = beginDateTemp.toString().substring(0, 7);
                if (reportDataItem.containsKey(date)) {
                    expendReportItem.add(reportDataItem.getString(date));
                } else {
                    expendReportItem.add("0.00");
                }

                beginDateTemp = beginDateTemp.plusMonths(1);
            }

            JSONObject report = new JSONObject();
            report.put("topClass", topClass);
            report.put("report", expendReportItem);
            expendReport.add(report);
        }

        return expendReport;
    }

    @Override
    public ResponseModel<Object> income(RequestModel<ReportRequestModel> request) {
        ReportRequestModel data = request.getData();

        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeReportByClass(request.getUsername(),
                data.getReportDate());
        List<IncomeInfo> incomeInfosSecond = incomeInfoService.getIncomeReportBySecondClass(request.getUsername(),
                data.getReportDate());

        ReportResponseModel<IncomeInfo> response = ReportResponseModel.buildIncome(incomeInfos,
                incomeInfosSecond);

        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.now();

        List<IncomeInfo> incomeReportByDate = incomeInfoService.getIncomeReportByDate(request.getUsername(),
                data.getReportDate());

        JSONArray incomeReport = analysisIncomeReport(incomeReportByDate, beginDate, endDate);
        response.setReportDate(incomeReport);

        JSONArray date = new JSONArray();
        while (!beginDate.isAfter(endDate)) {
            date.add(beginDate.toString().substring(0, 4));
            beginDate = beginDate.plusYears(1);
        }
        response.setDate(date);

        List<IncomeInfo> incomeReportByDateSum = incomeInfoService.getIncomeReportByDateSum(request.getUsername(),
                data.getReportDate());
        JSONArray total = new JSONArray();
        for (IncomeInfo incomeInfo : incomeReportByDateSum) {
            total.add(incomeInfo.getAmount());
        }
        response.setTotal(total);

        return ResponseModel.success(response);
    }

    private JSONArray analysisIncomeReport(List<IncomeInfo> incomeInfos, LocalDate beginDate,
                                           LocalDate endDate) {
        JSONObject reportData = new JSONObject();
        for (IncomeInfo incomeInfo : incomeInfos) {
            if (reportData.containsKey(incomeInfo.getTopClass())) {
                JSONObject reportDataItem = reportData.getJSONObject(incomeInfo.getTopClass());
                reportDataItem.put(incomeInfo.getRemark(), incomeInfo.getAmount());
            } else {
                JSONObject reportDataItem = new JSONObject();
                reportDataItem.put(incomeInfo.getRemark(), incomeInfo.getAmount());
                reportData.put(incomeInfo.getTopClass(), reportDataItem);
            }
        }

        JSONArray incomeReport = new JSONArray();
        for (String topClass : reportData.keySet()) {
            JSONArray incomeReportItem = new JSONArray();
            JSONObject reportDataItem = reportData.getJSONObject(topClass);

            LocalDate beginDateTemp = LocalDate.ofEpochDay(beginDate.toEpochDay());
            while (!beginDateTemp.isAfter(endDate)) {
                String date = beginDateTemp.toString().substring(0, 4);
                if (reportDataItem.containsKey(date)) {
                    incomeReportItem.add(reportDataItem.getString(date));
                } else {
                    incomeReportItem.add("0.00");
                }

                beginDateTemp = beginDateTemp.plusYears(1);
            }

            JSONObject report = new JSONObject();
            report.put("topClass", topClass);
            report.put("report", incomeReportItem);
            incomeReport.add(report);
        }

        return incomeReport;
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
