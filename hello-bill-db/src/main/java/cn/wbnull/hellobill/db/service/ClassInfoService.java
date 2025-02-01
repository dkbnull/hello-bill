package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.common.constant.StatusEnum;
import cn.wbnull.hellobill.common.util.StringUtils;
import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类信息表 服务类
 *
 * @author null  2021-01-01
 */
@Service
public class ClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public ClassInfo getClassInfoBySecondClass(String type, String secondClass) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassInfo::getSecondClass, secondClass);
        queryWrapper.eq(ClassInfo::getType, type);

        return classInfoMapper.selectOne(queryWrapper);
    }

    public List<ClassInfo> getClassInfos(String type) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(type), ClassInfo::getType, type);
        queryWrapper.orderByAsc(ClassInfo::getSerialNo, ClassInfo::getUuid);

        return classInfoMapper.selectList(queryWrapper);
    }

    public void updateClassInfo(String uuid, String key, String value) {
        UpdateWrapper<ClassInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(key, value).lambda()
                .eq(ClassInfo::getUuid, uuid);

        classInfoMapper.update(null, updateWrapper);
    }

    public List<ClassInfo> getTopClassInfos(String type) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct topClass").lambda()
                .eq(ClassInfo::getType, type)
                .eq(ClassInfo::getStatus, StatusEnum.USABLE.getStatus())
                .orderByAsc(ClassInfo::getSerialNo, ClassInfo::getUuid);

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getSecondClassInfos(String type) {
        LambdaQueryWrapper<ClassInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ClassInfo::getSecondClass);
        queryWrapper.eq(ClassInfo::getType, type);
        queryWrapper.eq(ClassInfo::getStatus, StatusEnum.USABLE.getStatus());
        queryWrapper.orderByAsc(ClassInfo::getSerialNo, ClassInfo::getUuid);

        return classInfoMapper.selectList(queryWrapper);
    }

    public List<ClassInfo> getSecondClassInfos(String type, String topClass) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct secondClass").lambda()
                .eq(ClassInfo::getTopClass, topClass)
                .eq(ClassInfo::getType, type)
                .eq(ClassInfo::getStatus, StatusEnum.USABLE.getStatus())
                .orderByAsc(ClassInfo::getSerialNo, ClassInfo::getUuid);

        return classInfoMapper.selectList(queryWrapper);
    }
}
