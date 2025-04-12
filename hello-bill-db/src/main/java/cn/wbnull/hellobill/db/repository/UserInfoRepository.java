package cn.wbnull.hellobill.db.repository;

import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息表 服务类
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Repository
public class UserInfoRepository {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> list() {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserInfo::getUsername);

        return userInfoMapper.selectList(queryWrapper);
    }

    public UserInfo getByUsername(String username) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username);

        return userInfoMapper.selectOne(queryWrapper);
    }

    public void updatePasswordByUsername(String username, String password) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserInfo::getPassword, password);
        updateWrapper.eq(UserInfo::getUsername, username);

        userInfoMapper.update(null, updateWrapper);
    }
}
