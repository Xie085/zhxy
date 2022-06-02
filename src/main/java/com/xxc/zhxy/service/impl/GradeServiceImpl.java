package com.xxc.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxc.zhxy.mapper.GradeMapper;
import com.xxc.zhxy.pojo.Grade;
import com.xxc.zhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:34
 * @since JDK8
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByPage(Page<Grade> page, String gradeName) {
        QueryWrapper wrapper = new QueryWrapper();
        if (null != gradeName){
            wrapper.like("name",gradeName);
        }
        wrapper.orderByDesc("id");
        Page page1 = baseMapper.selectPage(page, wrapper);
        return page1;
    }

    @Override
    public List<Grade> getGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}
