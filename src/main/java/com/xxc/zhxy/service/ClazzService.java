package com.xxc.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxc.zhxy.pojo.Clazz;

import java.util.List;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:28
 * @since JDK8
 */
public interface ClazzService extends IService<Clazz> {
    IPage getClazzByPage(Page<Clazz> page, Clazz clazz);

    List<Clazz> getClazzs();
}
