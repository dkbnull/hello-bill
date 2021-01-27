package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.*;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ExpendInfoService;
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
        List<ClassInfo> classInfos = classInfoService.getClassInfos(ClassInfo.TYPE_EXPEND);
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
        List<ExpendInfo> expendReportByClass = expendInfoService.getExpendReportByClass(request);
        List<ExpendInfo> expendReportByDate = expendInfoService.getExpendReportByDate(request);

        LocalDate localReportDate = DateUtils.localDateParse(request.getReportDate() + "-01");
        LocalDate localDate = LocalDate.now();
        assert localReportDate != null;
        LocalDate beginDate = LocalDate.of(localReportDate.getYear(), localReportDate.getMonth(), 1);
        LocalDate endDate;
        if (beginDate.getYear() == localDate.getYear() && beginDate.getMonth() == localDate.getMonth()) {
            endDate = localDate;
        } else {
            endDate = beginDate.plusMonths(1).minusDays(1);
        }

        JSONArray expendReport = analysisExpendReport(expendReportByDate, beginDate, endDate);

        ReportResponseModel<ExpendInfo> response = new ReportResponseModel<>();
        response.setReportClass(expendReportByClass);
        response.setReportDate(expendReport);

        JSONArray date = new JSONArray();
        while (!beginDate.isAfter(endDate)) {
            date.add(beginDate.toString());
            beginDate = beginDate.plusDays(1);
        }
        response.setDate(date);

        return ResponseModel.success(response);
    }

    private JSONArray analysisExpendReport(List<ExpendInfo> expendReportByDate, LocalDate beginDate, LocalDate endDate) throws Exception {
        JSONObject expendReportTemp = new JSONObject();
        for (ExpendInfo expendInfo : expendReportByDate) {
            if (expendReportTemp.containsKey(expendInfo.getSecondClass())) {
                JSONObject reportDateItem = expendReportTemp.getJSONObject(expendInfo.getSecondClass());
                reportDateItem.put(expendInfo.getRemark(), expendInfo.getAmount());
            } else {
                JSONObject reportDateItem = new JSONObject();
                reportDateItem.put(expendInfo.getRemark(), expendInfo.getAmount());
                expendReportTemp.put(expendInfo.getSecondClass(), reportDateItem);
            }
        }

        JSONArray expendReport = new JSONArray();
        Set<String> secondClasses = expendReportTemp.keySet();
        for (String secondClass : secondClasses) {
            JSONArray expendReportItem = new JSONArray();
            JSONObject expendReportTempItem = expendReportTemp.getJSONObject(secondClass);

            LocalDate beginDateTemp = LocalDate.ofEpochDay(beginDate.toEpochDay());
            while (!beginDateTemp.isAfter(endDate)) {
                if (expendReportTempItem.containsKey(beginDateTemp.toString())) {
                    expendReportItem.add(expendReportTempItem.getString(beginDateTemp.toString()));
                } else {
                    expendReportItem.add("0.00");
                }

                beginDateTemp = beginDateTemp.plusDays(1);
            }

            JSONObject report = new JSONObject();
            report.put("secondClass", secondClass);
            report.put("report", expendReportItem);
            expendReport.add(report);
        }

        return expendReport;
    }
}
