package com.xxc.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxc.zhxy.pojo.Admin;
import com.xxc.zhxy.pojo.LoginForm;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:27
 * @since JDK8
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage getAdminByPage(Page<Admin> page, Admin admin);
}
