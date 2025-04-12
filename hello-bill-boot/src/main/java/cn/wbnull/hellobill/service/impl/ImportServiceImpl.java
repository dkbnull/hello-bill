package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.core.dto.ApiRequest;
import cn.wbnull.hellobill.common.core.dto.ApiResponse;
import cn.wbnull.hellobill.common.core.util.*;
import cn.wbnull.hellobill.db.entity.*;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillClassMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import cn.wbnull.hellobill.db.repository.ClassInfoRepository;
import cn.wbnull.hellobill.db.repository.ImportBillClassRepository;
import cn.wbnull.hellobill.db.repository.ImportBillDetailConvertRepository;
import cn.wbnull.hellobill.db.repository.ImportBillInfoRepository;
import cn.wbnull.hellobill.dto.common.request.DeleteRequest;
import cn.wbnull.hellobill.dto.common.request.QueryRequest;
import cn.wbnull.hellobill.dto.imp.request.ConfirmRequest;
import cn.wbnull.hellobill.dto.imp.request.UpdateRequest;
import cn.wbnull.hellobill.dto.imp.response.QueryResponse;
import cn.wbnull.hellobill.service.ImportService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 账单导入接口服务类
 *
 * @author null
 * @date 2025-01-25
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Autowired
    private ImportBillInfoRepository importBillInfoRepository;
    @Autowired
    private ImportBillClassRepository importBillClassRepository;
    @Autowired
    private ImportBillDetailConvertRepository importBillDetailConvertRepository;

    @Autowired
    private ExpendInfoMapper expendInfoMapper;
    @Autowired
    private IncomeInfoMapper incomeInfoMapper;
    @Autowired
    private ImportBillClassMapper importBillClassMapper;
    @Autowired
    private ImportBillInfoMapper importBillInfoMapper;

    @Override
    public ApiResponse<Object> billFile(MultipartFile file, String username) {
        File fileBill;
        try {
            fileBill = FileUtils.transfer("data", file);
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }

        try (FileReader fileReader = new FileReader(fileBill.getAbsoluteFile());
             CSVReader csvReader = new CSVReader(fileReader)) {
            String[] line = csvReader.readNext();
            // 微信支付账单格式，第一行为： 微信支付账单明细
            if (line[0].contains("微信")) {
                List<ImportBillInfo> importBillInfos = importWeixinBill(username, csvReader);
                importBillInfoMapper.insertBatch(importBillInfos);
            }
            // 支付宝账单格式，第一行为：支付宝交易记录明细查询
            else if (line[0].contains("支付宝")) {
                List<ImportBillInfo> importBillInfos = importAlipayBill(username, csvReader);
                importBillInfoMapper.insertBatch(importBillInfos);
            }
            // 京东账单格式，第一行为：导出信息：   第二行为：京东账号名
            else if (line[0].contains("导出信息") && csvReader.readNext()[0].contains("京东")) {
                List<ImportBillInfo> importBillInfos = importJdBill(username, csvReader);
                importBillInfoMapper.insertBatch(importBillInfos);
            }
        } catch (Exception e) {
            LoggerUtils.error("账单文件导入异常", e);
            return ApiResponse.fail("账单文件导入异常: " + e);
        }

        return ApiResponse.success("导入成功");
    }

    private List<ImportBillInfo> importWeixinBill(String username, CSVReader csvReader) throws Exception {
        List<ImportBillInfo> importBillInfos = new ArrayList<>();

        String[] line;
        // 标识是否到了账单部分
        boolean tag = false;
        while ((line = csvReader.readNext()) != null) {
            // 表头
            if ("交易时间".equals(line[0])) {
                tag = true;
                continue;
            }

            if (!tag) {
                continue;
            }

            // ----------------------微信支付账单明细列表--------------------
            // 交易时间	            交易类型	交易对方	商品	    收/支	金额(元)	支付方式	当前状态	交易单号	    商户单号    备注
            // 2025-01-19 14:21:00	商户消费	青岛***	青岛***	支出	    ¥10.00	零钱	    支付成功	"420000"	"202501"    /
            if ("/".equals(line[4])) {
                continue;
            }

            ImportBillInfo importBillInfo = new ImportBillInfo();
            importBillInfo.setUsername(username);

            if ("支出".equals(line[4])) {
                importBillInfo.setBillType((byte) ClassTypeEnum.EXPEND.getTypeCode());
            }
            if ("收入".equals(line[4])) {
                importBillInfo.setBillType((byte) ClassTypeEnum.INCOME.getTypeCode());
            }

            // 交易时间
            Date date = DateUtils.dateParse(line[0], "yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
            importBillInfo.setBillTime(localDateTime);

            importBillInfo.setDetail(line[2]);
            importBillInfo.setDetailConvert(convertDetail(line[2]));

            ImportBillClass importBillClass = importBillClassMapper.getByDetail(importBillInfo.getDetailConvert());
            if (importBillClass == null) {
                importBillClass = importBillClassMapper.getByDetail(line[3]);
            }
            if (importBillClass != null) {
                importBillInfo.setTopClass(importBillClass.getTopClass());
                importBillInfo.setSecondClass(importBillClass.getSecondClass());
            }

            importBillInfo.setAmount(new BigDecimal(line[5].replace("¥", "")));
            importBillInfo.setPayMode(line[6]);
            importBillInfo.setGmtCreate(LocalDateTime.now());
            importBillInfo.setGmtModified(LocalDateTime.now());

            importBillInfos.add(importBillInfo);
        }

        reverseImportBillInfos(importBillInfos);

        return importBillInfos;
    }

    private List<ImportBillInfo> importAlipayBill(String username, CSVReader csvReader) throws Exception {
        List<ImportBillInfo> importBillInfos = new ArrayList<>();

        String[] line;
        // 标识是否到了账单部分
        boolean tag = false;
        while ((line = csvReader.readNext()) != null) {
            // 表头
            if (line[0].contains("交易号")) {
                tag = true;
                continue;
            }

            if (!tag) {
                continue;
            }

            if ("------------------------------------------------------------------------------------".equals(line[0].trim())) {
                break;
            }

            // ---------------------------------交易记录明细列表------------------------------------
            // 交易号        商家订单号    交易创建时间           付款时间             最近修改时间           交易来源地    类型        交易对方   商品名称    金额（元）   收/支     交易状态    	服务费（元）   	成功退款（元）  	备注        资金状态
            // "20240901"   "231266"	2024-09-27 11:59:52 2024-09-27 11:59:52 2024-09-27 11:59:52 支付宝网站	即时到账交易  青岛***   青岛***     14.97     支出       交易成功    	0	            0	                    	已支出
            ImportBillInfo importBillInfo = new ImportBillInfo();
            importBillInfo.setUsername(username);

            if ("支出".equals(line[10].trim())) {
                importBillInfo.setBillType((byte) ClassTypeEnum.EXPEND.getTypeCode());
            }
            if ("收入".equals(line[10].trim())) {
                importBillInfo.setBillType((byte) ClassTypeEnum.INCOME.getTypeCode());
            }
            if ("不计收支".equals(line[10].trim()) && ("已支出".equals(line[15].trim()) || StringUtils.isEmpty(line[15].trim()))) {
                importBillInfo.setBillType((byte) ClassTypeEnum.EXPEND.getTypeCode());
            }
            if ("不计收支".equals(line[10].trim()) && "已收入".equals(line[15].trim())) {
                importBillInfo.setBillType((byte) ClassTypeEnum.INCOME.getTypeCode());
            }

            // 交易时间
            Date date = DateUtils.dateParse(line[2].trim(), "yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
            importBillInfo.setBillTime(localDateTime);

            // 交易对方列为脱敏商家名称，则使用商品名称
            String detail = line[7].contains("**") ? line[8].trim() : line[7].trim();
            importBillInfo.setDetail(detail);
            importBillInfo.setDetailConvert(convertDetail(detail));

            ImportBillClass importBillClass = importBillClassMapper.getByDetail(importBillInfo.getDetailConvert());
            if (importBillClass == null) {
                importBillClass = importBillClassMapper.getByDetail(line[8].trim());
            }
            if (importBillClass != null) {
                importBillInfo.setTopClass(importBillClass.getTopClass());
                importBillInfo.setSecondClass(importBillClass.getSecondClass());
            }

            importBillInfo.setAmount(new BigDecimal(line[9].trim()).subtract(new BigDecimal(line[13].trim())));
            importBillInfo.setGmtCreate(LocalDateTime.now());
            importBillInfo.setGmtModified(LocalDateTime.now());

            importBillInfos.add(importBillInfo);
        }

        reverseImportBillInfos(importBillInfos);

        return importBillInfos;
    }

    private List<ImportBillInfo> importJdBill(String username, CSVReader csvReader) throws Exception {
        List<ImportBillInfo> importBillInfos = new ArrayList<>();

        String[] line;
        // 标识是否到了账单部分
        boolean tag = false;
        while ((line = csvReader.readNext()) != null) {
            // 表头
            if ("交易时间".equals(line[0])) {
                tag = true;
                continue;
            }

            if (!tag) {
                continue;
            }

            // 交易时间	            商户名称	    交易说明    金额      收/付款方式	交易状态	收/支	交易分类	交易订单号	商家订单号	备注
            // 2024-09-29 21:56:11	京东平台商户	小鹿蓝蓝    29.28	    京东白条	    交易成功	支出	    母婴用品	2972***	    1156***	    计入10月账单，还款日2024-10-23
            if (!"交易成功".equals(line[5].trim())) {
                continue;
            }

            ImportBillInfo importBillInfo = new ImportBillInfo();
            importBillInfo.setUsername(username);

            if ("支出".equals(line[6])) {
                importBillInfo.setBillType((byte) ClassTypeEnum.EXPEND.getTypeCode());
            }
            if ("收入".equals(line[6])) {
                importBillInfo.setBillType((byte) ClassTypeEnum.INCOME.getTypeCode());
            }

            // 交易时间
            Date date = DateUtils.dateParse(line[0], "yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
            importBillInfo.setBillTime(localDateTime);

            importBillInfo.setDetail(line[2]);
            importBillInfo.setDetailConvert(convertDetail(line[2]));

            ImportBillClass importBillClass = importBillClassMapper.getByDetail(importBillInfo.getDetailConvert());
            if (importBillClass != null) {
                importBillInfo.setTopClass(importBillClass.getTopClass());
                importBillInfo.setSecondClass(importBillClass.getSecondClass());
            }

            String amount = line[3];
            // 部分退款格式：106.76(已退款35.54)
            // 全额退款格式：67.47(已全额退款)
            if (amount.contains("已退款")) {
                String[] amounts = amount.replace("(", "").replace(")", "").split("已退款");
                importBillInfo.setAmount(new BigDecimal(amounts[0]).subtract(new BigDecimal(amounts[1])));
            } else if (amount.contains("已全额退款")) {
                importBillInfo.setAmount(new BigDecimal(0));
            } else {
                importBillInfo.setAmount(new BigDecimal(amount));
            }

            importBillInfo.setPayMode(line[4]);
            importBillInfo.setGmtCreate(LocalDateTime.now());
            importBillInfo.setGmtModified(LocalDateTime.now());

            importBillInfos.add(importBillInfo);
        }

        reverseImportBillInfos(importBillInfos);

        return importBillInfos;
    }

    private String convertDetail(String detail) {
        detail = detail.replace("'", "");

        ImportBillDetailConvert importBillDetailConvert = importBillDetailConvertRepository.getByDetail(detail);
        if (importBillDetailConvert == null) {
            return detail;
        }

        return importBillDetailConvert.getDetailConvert();
    }

    private void reverseImportBillInfos(List<ImportBillInfo> importBillInfoList) {
        // 导出的账单为倒序，进行翻转后再生成ID
        Collections.reverse(importBillInfoList);
        for (ImportBillInfo importBillInfo : importBillInfoList) {
            long epochMilli = DateUtils.toEpochMilli(importBillInfo.getBillTime());
            importBillInfo.setId(SnowflakeUtils.getInstance().nextId(epochMilli));
        }
    }

    @Override
    public ApiResponse<List<QueryResponse>> queryList(ApiRequest<Object> request) {
        List<ImportBillInfo> importBillInfos = importBillInfoRepository.list(request.getUsername());
        List<QueryResponse> queryResponses = BeanUtils.copyToList(importBillInfos, QueryResponse.class);
        for (QueryResponse queryResponse : queryResponses) {
            queryResponse.setBillTypeName(ClassTypeEnum.getTypeName(queryResponse.getBillType()));
        }

        return ApiResponse.success(queryResponses);
    }

    @Override
    public ApiResponse<QueryResponse> query(ApiRequest<QueryRequest> request) {
        ImportBillInfo importBillInfo = importBillInfoMapper.selectById(request.getData().getId());
        QueryResponse queryResponse = BeanUtils.copyProperties(importBillInfo, QueryResponse.class);

        return ApiResponse.success(queryResponse);
    }

    @Override
    public ApiResponse<Object> update(ApiRequest<UpdateRequest> request) {
        UpdateRequest data = request.getData();
        ImportBillInfo importBillInfo = BeanUtils.copyProperties(data, ImportBillInfo.class);
        ClassInfo classInfo = classInfoRepository.getByTypeAndSecondClass(data.getBillType(),
                data.getSecondClass());
        importBillInfo.setTopClass(classInfo.getTopClass());

        importBillInfoRepository.updateImportBillInfo(importBillInfo);

        return ApiResponse.success("修改成功");
    }

    @Override
    public ApiResponse<Object> delete(ApiRequest<DeleteRequest> request) {
        importBillInfoMapper.deleteById(request.getData().getId());

        return ApiResponse.success("删除成功");
    }

    @Override
    public ApiResponse<Object> confirm(ApiRequest<ConfirmRequest> request) {
        ImportBillInfo importBillInfo = importBillInfoMapper.selectById(request.getData().getId());
        if (!StringUtils.areNotEmpty(importBillInfo.getUsername(), importBillInfo.getTopClass(),
                importBillInfo.getSecondClass(), importBillInfo.getDetail())) {
            return ApiResponse.fail("账单信息不完整");
        }

        if (ClassTypeEnum.INCOME.getTypeCode() == importBillInfo.getBillType()) {
            IncomeInfo incomeInfo = BeanUtils.copyProperties(importBillInfo, IncomeInfo.class);
            incomeInfo.setIncomeDate(importBillInfo.getBillTime().toLocalDate());
            incomeInfo.setDetail(importBillInfo.getDetailConvert());
            incomeInfo.setGmtModified(null);
            try {
                incomeInfoMapper.insert(incomeInfo);
            } catch (DuplicateKeyException e) {
                LoggerUtils.error("确认账单信息异常", e);
                long epochMilli = DateUtils.toEpochMilli(importBillInfo.getBillTime());
                incomeInfo.setId(SnowflakeUtils.getInstance().nextId(epochMilli));
                incomeInfoMapper.insert(incomeInfo);
            }
        } else {
            ExpendInfo expendInfo = BeanUtils.copyProperties(importBillInfo, ExpendInfo.class);
            expendInfo.setExpendTime(importBillInfo.getBillTime());
            expendInfo.setDetail(importBillInfo.getDetailConvert());
            expendInfo.setGmtModified(null);
            try {
                expendInfoMapper.insert(expendInfo);
            } catch (DuplicateKeyException e) {
                LoggerUtils.error("确认账单信息异常", e);
                long epochMilli = DateUtils.toEpochMilli(importBillInfo.getBillTime());
                expendInfo.setId(SnowflakeUtils.getInstance().nextId(epochMilli));
                expendInfoMapper.insert(expendInfo);
            }
        }

        // 保存或更新明细转换对应信息
        importBillDetailConvertRepository.updateByImportBillInfo(importBillInfo);
        // 保存或更新明细品类对应信息
        importBillClassRepository.updateByImportBillInfo(importBillInfo);
        importBillInfoMapper.deleteById(importBillInfo.getId());

        return ApiResponse.success("确认成功");
    }
}
