package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.*;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
import cn.wbnull.hellobill.model.expend.ReportRequestModel;
import cn.wbnull.hellobill.model.expend.ReportResponseModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 支出信息接口服务类
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
@Service
public class ExpendService {

    @Autowired
    private ExpendInfoService expendInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    public ResponseModel<List<ExpendInfo>> queryList(QueryListRequestModel request) throws Exception {
        List<ExpendInfo> expendInfos = expendInfoService.getExpendInfos(request);

        return ResponseModel.success(expendInfos);
    }

    public ResponseModel<List<String>> classQuery(RequestModel request) throws Exception {
        List<ClassInfo> classInfos = classInfoService.getSecondClassInfos(TypeEnum.EXPEND.getTypeCode());
        List<String> secondClasses = new ArrayList<>();
        for (ClassInfo classInfo : classInfos) {
            secondClasses.add(classInfo.getSecondClass());
        }

        return ResponseModel.success(secondClasses);
    }

    public ResponseModel<Object> add(AddRequestModel request) throws Exception {
        expendInfoService.addExpendInfo(request);

        return ResponseModel.success("记账成功");
    }

    public ResponseModel<ExpendInfo> query(QueryRequestModel request) throws Exception {
        return ResponseModel.success(expendInfoService.getExpendInfo(request));
    }

    public ResponseModel<Object> update(UpdateRequestModel request) throws Exception {
        expendInfoService.updateExpendInfo(request);

        return ResponseModel.success("修改成功");
    }

    public ResponseModel<Object> delete(DeleteRequestModel request) throws Exception {
        expendInfoService.deleteExpendInfo(request);

        return ResponseModel.success("删除成功");
    }

    public ResponseModel<Object> report(ReportRequestModel request) throws Exception {
        List<ExpendInfo> expendReports = expendInfoService.getExpendReportByClass(request.getUsername(),
                request.getReportDate());
        List<ExpendInfo> expendReportsSecond = expendInfoService.getExpendReportBySecondClass(request.getUsername(),
                request.getReportDate());

        ReportResponseModel<ExpendInfo> response = ReportResponseModel.buildExpend(expendReports,
                expendReportsSecond);

//        List<ExpendInfo> expendReportByDate = expendInfoService.getExpendReportByDate(request.getUsername(),
//                request.getReportDate());
//
//        LocalDate localReportDate = DateUtils.localDateParse(request.getReportDate() + "-01-01");
//        LocalDate localDate = LocalDate.now();
//        assert localReportDate != null;
//        LocalDate beginDate = LocalDate.of(localReportDate.getYear(), localReportDate.getMonth(), 1);
//        LocalDate endDate;
//        if (beginDate.getYear() == localDate.getYear()) {
//            endDate = localDate;
//        } else {
//            endDate = beginDate.plusMonths(1).minusDays(1);
//        }
//
//        beginDate = LocalDate.of(localReportDate.getYear(), 1, 1);
//        JSONArray expendReport = analysisExpendReport(expendReportByDate, beginDate, endDate);
//
//        response.setReportDate(expendReport);
//
//        JSONArray date = new JSONArray();
//        while (!beginDate.isAfter(endDate)) {
//            date.add(beginDate.toString().substring(0, 7));
//            beginDate = beginDate.plusMonths(1);
//        }
//        response.setDate(date);

        return ResponseModel.success(response);
    }

    private JSONArray analysisExpendReport(List<ExpendInfo> expendReportByDate, LocalDate beginDate,
                                           LocalDate endDate) {
        JSONObject expendReportTemp = new JSONObject();
        for (ExpendInfo expendInfo : expendReportByDate) {
            if (expendReportTemp.containsKey(expendInfo.getTopClass())) {
                JSONObject reportDateItem = expendReportTemp.getJSONObject(expendInfo.getSecondClass());
                reportDateItem.put(expendInfo.getRemark(), expendInfo.getAmount());
            } else {
                JSONObject reportDateItem = new JSONObject();
                reportDateItem.put(expendInfo.getRemark(), expendInfo.getAmount());
                expendReportTemp.put(expendInfo.getSecondClass(), reportDateItem);
            }
        }

        JSONArray expendReport = new JSONArray();
        Set<String> topClasses = expendReportTemp.keySet();
        for (String topClass : topClasses) {
            JSONArray expendReportItem = new JSONArray();
            JSONObject expendReportTempItem = expendReportTemp.getJSONObject(topClass);

            LocalDate beginDateTemp = LocalDate.ofEpochDay(beginDate.toEpochDay());
            while (!beginDateTemp.isAfter(endDate)) {
                String date = beginDateTemp.toString().substring(0, 7);
                if (expendReportTempItem.containsKey(date)) {
                    expendReportItem.add(expendReportTempItem.getString(date));
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
}
