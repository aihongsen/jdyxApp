package com.jdyx.app.service;
import com.jdyx.app.bean.UserInfo;
import java.util.List;

public interface UserInfoService {
    /**
     * 根据用户ID查找用户信息
     * @return
     */
    public UserInfo getById(Integer id);

    /**
     * 查看所有用户信息
     * @return
     */
    public List<UserInfo> findAll();

    /**
     * 根据手机号查询一条信息
     * @param userInfo
     * @return
     */
    public UserInfo getUserByPhone(UserInfo userInfo);

    /**
     * 根据用户信息创建一个用户
     * @param userInfo
     * @return
     */
    public Integer createUser(UserInfo userInfo);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public Object requestVerification(String phone);

    /**
     * 请求登录
     * @param phone
     * @param code
     * @return
     */
    public Object login(String phone, String code,String deviceId,String latitude,String longitude);
}
