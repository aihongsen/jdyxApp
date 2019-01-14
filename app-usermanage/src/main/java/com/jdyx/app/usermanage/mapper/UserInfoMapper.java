package com.jdyx.app.usermanage.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdyx.app.bean.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    public UserInfo getUserByPhone(String phone);
}