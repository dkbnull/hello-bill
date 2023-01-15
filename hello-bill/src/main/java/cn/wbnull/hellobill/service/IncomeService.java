package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import cn.wbnull.hellobill.model.expend.ReportRequestModel;
import cn.wbnull.hellobill.model.expend.ReportResponseModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 收入信息接口服务类
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class IncomeService {

    @Autowired
    private IncomeInfoService incomeInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    public ResponseModel<List<IncomeInfo>> queryList(QueryListRequestModel request) throws Exception {
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeInfos(request);

        return ResponseModel.success(incomeInfos);
    }

    public ResponseModel<List<String>> classQuery(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(TypeEnum.INCOME.getTypeCode());
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(AddRequestModel request) throws Exception {
        incomeInfoService.addIncomeInfo(request);

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<IncomeInfo> query(QueryRequestModel request) throws Exception {
        return ResponseModel.success(incomeInfoService.getIncomeInfo(request));
    }

    public ResponseModel<Object> update(UpdateRequestModel request) throws Exception {
        incomeInfoService.updateIncomeInfo(request);

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(DeleteRequestModel request) throws Exception {
        incomeInfoService.deleteIncomeInfo(request);

        return ResponseModel.success("删除成功");
    }

    public ResponseModel<Object> report(ReportRequestModel request) throws Exception {
        List<IncomeInfo> incomeInfos = incomeInfoService.getIncomeReportByClass(request.getUsername(),
                request.getReportDate());
        List<IncomeInfo> incomeInfosSecond = incomeInfoService.getIncomeReportBySecondClass(request.getUsername(),
                request.getReportDate());

        ReportResponseModel<IncomeInfo> response = ReportResponseModel.buildIncome(incomeInfos,
                incomeInfosSecond);

        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.now();

        List<IncomeInfo> incomeReportByDate = incomeInfoService.getIncomeReportByDate(request.getUsername(),
                request.getReportDate());

        JSONArray incomeReport = analysisIncomeReport(incomeReportByDate, beginDate, endDate);
        response.setReportDate(incomeReport);

        JSONArray date = new JSONArray();
        while (!beginDate.isAfter(endDate)) {
            date.add(beginDate.toString().substring(0, 4));
            beginDate = beginDate.plusYears(1);
        }
        response.setDate(date);

        List<IncomeInfo> incomeReportByDateSum = incomeInfoService.getIncomeReportByDateSum(request.getUsername(),
                request.getReportDate());
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
}
