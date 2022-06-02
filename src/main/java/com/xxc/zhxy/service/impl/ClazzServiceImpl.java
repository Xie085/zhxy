package com.xxc.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxc.zhxy.mapper.ClazzMapper;
import com.xxc.zhxy.pojo.Clazz;
import com.xxc.zhxy.service.ClazzService;
import com.xxc.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:32
 * @since JDK8
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage getClazzByPage(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> wrapper = new QueryWrapper<>();
        String name = null;
        String gradeName = null;
        if (clazz != null) {
            name = clazz.getName();
            gradeName = clazz.getGradeName();
        }
        if (gradeName != null) {
            wrapper.like("grade_name", gradeName);
        }
        if (name != null) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("id");
        Page<Clazz> page1 = baseMapper.selectPage(page, wrapper);
        return page1;
    }

    @Override
    public List<Clazz> getClazzs() {
        List<Clazz> clazzes = baseMapper.selectList(null);
        return clazzes;
    }


}
