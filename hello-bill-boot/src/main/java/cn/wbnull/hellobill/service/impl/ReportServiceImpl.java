package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.entity.ExpendInfo;
import cn.wbnull.hellobill.db.entity.IncomeInfo;
import cn.wbnull.hellobill.db.repository.ClassInfoRepository;
import cn.wbnull.hellobill.db.repository.ExpendInfoRepository;
import cn.wbnull.hellobill.db.repository.IncomeInfoRepository;
import cn.wbnull.hellobill.dto.report.request.ExpendClassRequest;
import cn.wbnull.hellobill.dto.report.request.ExpendDetailRequest;
import cn.wbnull.hellobill.dto.report.request.ReportRequest;
import cn.wbnull.hellobill.dto.report.response.QueryResponse;
import cn.wbnull.hellobill.dto.report.response.ReportClassResponse;
import cn.wbnull.hellobill.dto.report.response.ReportDetailResponse;
import cn.wbnull.hellobill.dto.report.response.ReportResponse;
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
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Autowired
    private ExpendInfoRepository expendInfoRepository;

    @Autowired
    private IncomeInfoRepository incomeInfoRepository;

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<Object> request) {
        List<ExpendInfo> expendInfos = expendInfoRepository.listReport(request.getUsername());
        List<IncomeInfo> incomeInfos = incomeInfoRepository.listReport(request.getUsername());

        return ApiResponse.success(QueryResponse.build(expendInfos, incomeInfos));
    }

    @Override
    public ApiResponse<QueryResponse> queryNet(ApiRequest<Object> request) {
        List<ExpendInfo> expendInfos = expendInfoRepository.listReportNet(request.getUsername());
        List<IncomeInfo> incomeInfos = incomeInfoRepository.listReportNet(request.getUsername());

        return ApiResponse.success(QueryResponse.build(expendInfos, incomeInfos));
    }

    @Override
    public ApiResponse<Object> expend(ApiRequest<ReportRequest> request) {
        ReportRequest data = request.getData();

        List<ExpendInfo> expendInfosDate = expendInfoRepository.listReportByDateAndClass(request.getUsername(),
                data.getReportDate(), data.getReportClass());
        List<ExpendInfo> expendInfosClass = expendInfoRepository.listReportWithClassByDateAndClass(request.getUsername(),
                data.getReportDate(), data.getReportClass());

        List<String> date = getReportDateMonth(data.getReportDate());

        ReportResponse<ExpendInfo> response = ReportResponse.buildExpend(date, expendInfosDate, expendInfosClass);

        return ApiResponse.success(response);
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
    public ApiResponse<Object> income(ApiRequest<ReportRequest> request) {
        ReportRequest data = request.getData();

        List<IncomeInfo> incomeReportDate = incomeInfoRepository.listReportByDate(request.getUsername(),
                data.getReportDate());
        List<IncomeInfo> incomeInfosClass = incomeInfoRepository.listReportWithClassByDate(request.getUsername(),
                data.getReportDate());

        List<String> date = getReportDateYear(data.getReportDate());

        ReportResponse<IncomeInfo> response = ReportResponse.buildIncome(date, incomeReportDate,
                incomeInfosClass);

        return ApiResponse.success(response);
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
    public ApiResponse<ReportClassResponse> expendClass(ApiRequest<ExpendClassRequest> request) {
        ExpendClassRequest data = request.getData();

        List<ExpendInfo> expendInfos = expendInfoRepository.listAmountByDateAndClass(request.getUsername(),
                data.getReportDate(), data.getTopClass());

        List<ClassInfo> classInfos = classInfoRepository.listSecondByTypeAndTop(ClassTypeEnum.EXPEND.getTypeCode(),
                data.getTopClass());

        return ApiResponse.success(ReportClassResponse.build(classInfos, expendInfos));
    }

    @Override
    public ApiResponse<ReportDetailResponse> expendDetail(ApiRequest<ExpendDetailRequest> request) {
        ExpendDetailRequest data = request.getData();

        List<ExpendInfo> expendInfos = expendInfoRepository.listDetailByDateAndClass(request.getUsername(),
                data.getReportDate(), data.getTopClass(), data.getSecondClass());

        return ApiResponse.success(ReportDetailResponse.build(expendInfos));
    }

    @Override
    public ApiResponse<ReportClassResponse> incomeClass(ApiRequest<ReportRequest> request) {
        ReportRequest data = request.getData();

        List<IncomeInfo> incomeInfos = incomeInfoRepository.listAmountByDate(request.getUsername(),
                data.getReportDate());

        List<ClassInfo> classInfos = classInfoRepository.listTopByType(ClassTypeEnum.INCOME.getTypeCode());

        return ApiResponse.success(ReportClassResponse.buildIncome(classInfos, incomeInfos));
    }

    @Override
    public ApiResponse<ReportDetailResponse> incomeDetail(ApiRequest<ReportRequest> request) {
        ReportRequest data = request.getData();

        List<IncomeInfo> incomeInfos = incomeInfoRepository.listDetailByDate(request.getUsername(),
                data.getReportDate());

        return ApiResponse.success(ReportDetailResponse.buildIncome(incomeInfos));
    }
}
