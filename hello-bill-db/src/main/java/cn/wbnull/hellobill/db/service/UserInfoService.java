package cn.wbnull.hellobill.db.service;

import cn.wbnull.hellobill.db.entity.UserInfo;
import cn.wbnull.hellobill.db.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务类
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserInfo(String username) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username);

        return userInfoMapper.selectOne(queryWrapper);
    }

    public void updateUserInfo(String username, String password) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserInfo::getPassword, password);
        updateWrapper.eq(UserInfo::getUsername, username);

        userInfoMapper.update(null, updateWrapper);
    }
}
