package com.xxc.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxc.zhxy.mapper.StudentMapper;
import com.xxc.zhxy.pojo.Admin;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.pojo.Student;
import com.xxc.zhxy.service.StudentService;
import com.xxc.zhxy.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:36
 * @since JDK8
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userId);
        Student student = baseMapper.selectOne(wrapper);
        return student;
    }

    @Override
    public IPage getStudentByPage(Page<Student> page, Student student) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (student != null && student.getName() != null){
            wrapper.like("name",student.getName());
        }
        if (student != null && student.getClazzName() != null){
            wrapper.like("name",student.getClazzName());
        }
        wrapper.orderByDesc("id");

        Page<Student> studentPage = baseMapper.selectPage(page, wrapper);
        return studentPage;
    }
}
