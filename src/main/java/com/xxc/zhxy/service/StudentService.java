package com.xxc.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.pojo.Student;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:35
 * @since JDK8
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage getStudentByPage(Page<Student> page, Student student);
}
