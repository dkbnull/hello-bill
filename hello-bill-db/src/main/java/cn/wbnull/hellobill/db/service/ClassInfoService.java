package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.db.entity.ClassInfo;
import cn.wbnull.hellobill.db.mapper.ClassInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public List<ClassInfo> getClassInfos(String type) {
        QueryWrapper<ClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct secondClass");
        queryWrapper.eq("type", type);
        queryWrapper.orderByAsc("uuid");

        return classInfoMapper.selectList(queryWrapper);
    }

}
