package com.xxc.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxc.zhxy.mapper.TeacherMapper;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.pojo.Teacher;
import com.xxc.zhxy.service.TeacherService;
import com.xxc.zhxy.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:37
 * @since JDK8
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userId);
        Teacher teacher = baseMapper.selectOne(wrapper);
        return teacher;
    }

    @Override
    public IPage getTeacherByPage(Page<Teacher> page1, Teacher teacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if (teacher != null && teacher.getName() != null) {
            wrapper.like("name",teacher.getName());
        }
        wrapper.orderByDesc("id");
        return baseMapper.selectPage(page1,wrapper);
    }
}
