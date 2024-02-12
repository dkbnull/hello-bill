package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.StatusEnum;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类信息表 服务类
 *
 * @author dukunbiao(null)  2021-01-01
 */
@Service
public class ClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<ClassInfo> getClassInfos() {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("serialNo", "uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getClassInfos(String type) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.orderByAsc("serialNo", "uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getSecondClassInfosByType(String type) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct secondClass");
        queryWrapper.eq("type", type);
        queryWrapper.eq("status", StatusEnum.USABLE.getStatus());
        queryWrapper.orderByAsc("serialNo", "uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getTopClassInfos(String type) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct topClass");
        queryWrapper.eq("type", type);
        queryWrapper.eq("status", StatusEnum.USABLE.getStatus());
        queryWrapper.orderByAsc("serialNo", "uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getSecondClassInfos(String type, String topClass) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct secondClass");
        queryWrapper.eq("topClass", topClass);
        queryWrapper.eq("type", type);
        queryWrapper.eq("status", StatusEnum.USABLE.getStatus());
        queryWrapper.orderByAsc("serialNo", "uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

    public void updateClassInfo(String uuid, String key, String value) {
        UpdateWrapper<ClassInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(key, value);
        updateWrapper.eq("uuid", uuid);

        classInfoMapper.update(null, updateWrapper);
    }
}
