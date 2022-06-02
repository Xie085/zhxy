package com.xxc.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxc.zhxy.mapper.AdminMapper;
import com.xxc.zhxy.pojo.Admin;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.service.AdminService;
import com.xxc.zhxy.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:28
 * @since JDK8
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("id",userId);
        Admin admin = baseMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public IPage getAdminByPage(Page<Admin> page, Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (admin!=null&&admin.getName()!=null){
            wrapper.like("name",admin.getName());
        }
        wrapper.orderByDesc("id");

        return baseMapper.selectPage(page,wrapper);
    }
}
