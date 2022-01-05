package cn.wbnull.hellobill.service;

import cn.wbnull.hellobill.common.constant.TypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.model.expend.DeleteRequestModel;
import cn.wbnull.hellobill.common.model.expend.QueryRequestModel;
import cn.wbnull.hellobill.common.model.expend.ReportRequestModel;
import cn.wbnull.hellobill.common.model.expend.ReportResponseModel;
import cn.wbnull.hellobill.common.model.income.AddRequestModel;
import cn.wbnull.hellobill.common.model.income.QueryListRequestModel;
import cn.wbnull.hellobill.common.model.income.UpdateRequestModel;
import cn.wbnull.hellobill.common.util.DateUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.IncomeInfoService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        List<ClassInfo> classInfos = classInfoService.getClassInfos(TypeEnum.INCOME.getTypeCode());
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
        List<IncomeInfo> incomeReportByClass = incomeInfoService.getIncomeReportByClass(request);
        List<IncomeInfo> incomeReportByDate = incomeInfoService.getIncomeReportByDate(request);

        LocalDate localReportDate = DateUtils.localDateParse(request.getReportDate() + "-01-01");
        LocalDate localDate = LocalDate.now();
        assert localReportDate != null;
        LocalDate beginDate = LocalDate.of(localReportDate.getYear(), 1, 1);
        LocalDate endDate;
        if (beginDate.getYear() == localDate.getYear()) {
            endDate = localDate;
        } else {
            endDate = beginDate.plusYears(1).minusDays(1);
        }

        JSONArray incomeReport = analysisIncomeReport(incomeReportByDate, beginDate, endDate);

        ReportResponseModel<IncomeInfo> response = new ReportResponseModel<>();
        response.setReportClass(incomeReportByClass);
        response.setReportDate(incomeReport);

        JSONArray date = new JSONArray();
        while (!beginDate.isAfter(endDate)) {
            date.add(beginDate.toString().substring(0, 7));
            beginDate = beginDate.plusMonths(1);
        }
        response.setDate(date);

        return ResponseModel.success(response);
    }

    private JSONArray analysisIncomeReport(List<IncomeInfo> incomeReportByDate, LocalDate beginDate, LocalDate endDate) throws Exception {
        JSONObject incomeReportTemp = new JSONObject();
        for (IncomeInfo incomeInfo : incomeReportByDate) {
            if (incomeReportTemp.containsKey(incomeInfo.getSecondClass())) {
                JSONObject reportDateItem = incomeReportTemp.getJSONObject(incomeInfo.getSecondClass());
                reportDateItem.put(incomeInfo.getRemark(), incomeInfo.getAmount());
            } else {
                JSONObject reportDateItem = new JSONObject();
                reportDateItem.put(incomeInfo.getRemark(), incomeInfo.getAmount());
                incomeReportTemp.put(incomeInfo.getSecondClass(), reportDateItem);
            }
        }

        JSONArray incomeReport = new JSONArray();
        Set<String> secondClasses = incomeReportTemp.keySet();
        for (String secondClass : secondClasses) {
            JSONArray incomeReportItem = new JSONArray();
            JSONObject incomeReportTempItem = incomeReportTemp.getJSONObject(secondClass);

            LocalDate beginDateTemp = LocalDate.ofEpochDay(beginDate.toEpochDay());
            while (!beginDateTemp.isAfter(endDate)) {
                String date = beginDateTemp.toString().substring(0, 7);
                if (incomeReportTempItem.containsKey(date)) {
                    incomeReportItem.add(incomeReportTempItem.getString(date));
                } else {
                    incomeReportItem.add("0.00");
                }

                beginDateTemp = beginDateTemp.plusMonths(1);
            }

            JSONObject report = new JSONObject();
            report.put("secondClass", secondClass);
            report.put("report", incomeReportItem);
            incomeReport.add(report);
        }

        return incomeReport;
    }
}
