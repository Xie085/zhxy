package com.xxc.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.pojo.Teacher;

import java.util.Map;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:36
 * @since JDK8
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage getTeacherByPage(Page<Teacher> page1, Teacher teacher);
}
