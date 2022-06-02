package com.xxc.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxc.zhxy.pojo.Grade;

import java.util.List;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:33
 * @since JDK8
 */
public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByPage(Page<Grade> page, String gradeName);

    List<Grade> getGrades();
}
