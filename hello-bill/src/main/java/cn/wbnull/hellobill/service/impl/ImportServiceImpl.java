package cn.wbnull.hellobill.service.impl;

import cn.wbnull.hellobill.common.constant.ClassTypeEnum;
import cn.wbnull.hellobill.common.model.RequestModel;
import cn.wbnull.hellobill.common.model.ResponseModel;
import cn.wbnull.hellobill.common.util.*;
import cn.wbnull.hellobill.db.entity.*;
import cn.wbnull.hellobill.db.mapper.ExpendInfoMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillClassMapper;
import cn.wbnull.hellobill.db.mapper.ImportBillInfoMapper;
import cn.wbnull.hellobill.db.mapper.IncomeInfoMapper;
import cn.wbnull.hellobill.db.service.ClassInfoService;
import cn.wbnull.hellobill.db.service.ImportBillService;
import cn.wbnull.hellobill.model.common.DeleteRequestModel;
import cn.wbnull.hellobill.model.common.QueryRequestModel;
import cn.wbnull.hellobill.model.imp.ConfirmRequestModel;
import cn.wbnull.hellobill.model.imp.ImportBillInfoModel;
import cn.wbnull.hellobill.model.imp.UpdateRequestModel;
import cn.wbnull.hellobill.service.ImportService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账单导入接口服务类
 *
 * @author null  2025-01-25
 * https://github.com/dkbnull/HelloBill
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ImportBillClassMapper importBillClassMapper;
    @Autowired
    private ImportBillInfoMapper importBillInfoMapper;
    @Autowired
    private ImportBillService importBillService;

    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ExpendInfoMapper expendInfoMapper;
    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Override
    public ResponseModel<Object> billFile(MultipartFile file, String username) {
        File fileBill;
        try {
            fileBill = FileUtils.transfer("data", file);
        } catch (Exception e) {
            return ResponseModel.fail(e.getMessage());
        }

        try (FileReader fileReader = new FileReader(fileBill.getAbsoluteFile());
             CSVReader csvReader = new CSVReader(fileReader)) {
            String[] line = csvReader.readNext();
            // 微信支付账单格式，第一行为： 微信支付账单明细
            if (line[0].contains("微信")) {
                List<ImportBillInfo> importBillInfoList = importWeixinBill(username, csvReader);
                importBillInfoMapper.insertBatch(importBillInfoList);
            }
            // 支付宝对账单格式，第一行为：支付宝交易记录明细查询
            else if (line[0].contains("支付宝")) {
                List<ImportBillInfo> importBillInfoList = importAlipayBill(username, csvReader);
                importBillInfoMapper.insertBatch(importBillInfoList);
            }
        } catch (Exception e) {
            LoggerUtils.error("账单文件导入异常", e);
        }

        return ResponseModel.success("导入成功");
    }

    private List<ImportBillInfo> importWeixinBill(String username, CSVReader csvReader) throws Exception {
        List<ImportBillInfo> importBillInfoList = new ArrayList<>();

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
            // 交易时间
            Date date = DateUtils.dateParse(line[0], "yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
            long epochMilli = DateUtils.toEpochMilli(localDateTime);

            importBillInfo.setId(SnowflakeUtils.getInstance().nextId(epochMilli));
            importBillInfo.setUsername(username);
            if ("支出".equals(line[4])) {
                importBillInfo.setBillType((byte) 0);
            }
            if ("收入".equals(line[4])) {
                importBillInfo.setBillType((byte) 1);
            }

            importBillInfo.setBillTime(localDateTime);

            ImportBillClass importBillClass = importBillClassMapper.getImportBillClass(line[2]);
            if (importBillClass == null) {
                importBillClass = importBillClassMapper.getImportBillClass(line[3]);
            }
            if (importBillClass != null) {
                importBillInfo.setTopClass(importBillClass.getTopClass());
                importBillInfo.setSecondClass(importBillClass.getSecondClass());
            }
            importBillInfo.setDetail(convertDetail(line[2]));
            importBillInfo.setAmount(new BigDecimal(line[5].replace("¥", "")));
            importBillInfo.setCreateTime(LocalDateTime.now());
            importBillInfo.setUpdateTime(LocalDateTime.now());

            importBillInfoList.add(importBillInfo);
        }

        return importBillInfoList;
    }

    private List<ImportBillInfo> importAlipayBill(String username, CSVReader csvReader) throws Exception {
        List<ImportBillInfo> importBillInfoList = new ArrayList<>();

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

            if ("不计收支".equals(line[10].trim()) || !"交易成功".equals(line[11].trim())) {
                continue;
            }

            // ---------------------------------交易记录明细列表------------------------------------
            // 交易号        商家订单号    交易创建时间           付款时间             最近修改时间           交易来源地    类型        交易对方   商品名称    金额（元）   收/支     交易状态    	服务费（元）   	成功退款（元）  	备注        资金状态
            // "20240901"   "231266"	2024-09-27 11:59:52 2024-09-27 11:59:52 2024-09-27 11:59:52 支付宝网站	即时到账交易  青岛***   青岛***     14.97     支出       交易成功    	0	            0	                    	已支出
            ImportBillInfo importBillInfo = new ImportBillInfo();
            // 交易时间
            Date date = DateUtils.dateParse(line[2].trim(), "yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(date);
            long epochMilli = DateUtils.toEpochMilli(localDateTime);

            importBillInfo.setId(SnowflakeUtils.getInstance().nextId(epochMilli));
            importBillInfo.setUsername(username);
            if ("支出".equals(line[10].trim())) {
                importBillInfo.setBillType((byte) 0);
            }
            if ("收入".equals(line[10].trim())) {
                importBillInfo.setBillType((byte) 1);
            }

            importBillInfo.setBillTime(localDateTime);

            ImportBillClass importBillClass = importBillClassMapper.getImportBillClass(line[7].trim());
            if (importBillClass == null) {
                importBillClass = importBillClassMapper.getImportBillClass(line[8].trim());
            }
            if (importBillClass != null) {
                importBillInfo.setTopClass(importBillClass.getTopClass());
                importBillInfo.setSecondClass(importBillClass.getSecondClass());
            }
            importBillInfo.setDetail(convertDetail(line[7].trim()));
            importBillInfo.setAmount(new BigDecimal(line[9].trim()));
            importBillInfo.setCreateTime(LocalDateTime.now());
            importBillInfo.setUpdateTime(LocalDateTime.now());

            importBillInfoList.add(importBillInfo);
        }

        return importBillInfoList;
    }

    private String convertDetail(String detail) {
        ImportBillDetailConvert importBillDetailConvert = importBillService.getImportBillDetailConvert(detail);
        if (importBillDetailConvert == null) {
            return detail;
        }

        return importBillDetailConvert.getDetailConvert();
    }

    @Override
    public ResponseModel<List<ImportBillInfoModel>> queryList(RequestModel<Object> request) {
        List<ImportBillInfo> importBillInfoList = importBillService.getImportBillInfos(request.getUsername());
        List<ImportBillInfoModel> importBillInfoModelList = BeanUtils.copyPropertyList(importBillInfoList, ImportBillInfoModel.class);
        for (ImportBillInfoModel importBillInfo : importBillInfoModelList) {
            if ("0".equals(importBillInfo.getBillType())) {
                importBillInfo.setBillTypeValue("支出");
            }
            if ("1".equals(importBillInfo.getBillType())) {
                importBillInfo.setBillTypeValue("收入");
            }
        }

        return ResponseModel.success(importBillInfoModelList);
    }

    @Override
    public ResponseModel<ImportBillInfoModel> query(RequestModel<QueryRequestModel> request) {
        ImportBillInfo importBillInfo = importBillInfoMapper.selectById(request.getData().getId());
        ImportBillInfoModel importBillInfoModel = BeanUtils.copyProperties(importBillInfo, ImportBillInfoModel.class);
        return ResponseModel.success(importBillInfoModel);
    }

    @Override
    public ResponseModel<Object> update(RequestModel<UpdateRequestModel> request) {
        UpdateRequestModel data = request.getData();
        ImportBillInfo importBillInfo = BeanUtils.copyProperties(data, ImportBillInfo.class);
        ClassInfo classInfo = classInfoService.getClassInfoBySecondClass(data.getBillType(),
                data.getSecondClass());
        importBillInfo.setTopClass(classInfo.getTopClass());

        importBillService.updateImportBillDetailConvert(importBillInfo);
        importBillService.updateImportBillInfo(importBillInfo);

        return ResponseModel.success("修改成功");
    }

    @Override
    public ResponseModel<Object> delete(RequestModel<DeleteRequestModel> request) {
        importBillInfoMapper.deleteById(request.getData().getId());
        return ResponseModel.success("删除成功");
    }

    @Override
    public ResponseModel<Object> confirm(RequestModel<ConfirmRequestModel> request) {
        ImportBillInfo importBillInfo = importBillInfoMapper.selectById(request.getData().getId());
        if (!StringUtils.areNotEmpty(importBillInfo.getUsername(), importBillInfo.getTopClass(),
                importBillInfo.getSecondClass(), importBillInfo.getDetail())) {
            return ResponseModel.fail("账单信息不完整");
        }

        if (ClassTypeEnum.INCOME.getTypeCode().equals(String.valueOf(importBillInfo.getBillType()))) {
            IncomeInfo incomeInfo = BeanUtils.copyProperties(importBillInfo, IncomeInfo.class);
            incomeInfo.setIncomeDate(importBillInfo.getBillTime().toLocalDate());
            incomeInfoMapper.insert(incomeInfo);
        } else {
            ExpendInfo expendInfo = BeanUtils.copyProperties(importBillInfo, ExpendInfo.class);
            expendInfo.setExpendTime(importBillInfo.getBillTime());
            expendInfoMapper.insert(expendInfo);
        }

        // 保存或更新明细品类对应信息
        ImportBillClass importBillClass = importBillService.getImportBillClass(importBillInfo.getDetail());
        if (importBillClass == null || !importBillClass.getTopClass().equals(importBillInfo.getTopClass()) ||
                !importBillClass.getSecondClass().equals(importBillInfo.getSecondClass())) {
            importBillClass = BeanUtils.copyProperties(importBillInfo, ImportBillClass.class);
            importBillClassMapper.insertOrUpdate(importBillClass);
        }

        importBillInfoMapper.deleteById(importBillInfo.getId());

        return ResponseModel.success("确认成功");
    }
}
