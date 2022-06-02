package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxc.zhxy.pojo.Grade;
import com.xxc.zhxy.service.GradeService;
import com.xxc.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author xiangcheng
 * @version 2022/5/31 14:39
 * @since JDK8
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    GradeService gradeService;

    @ApiOperation("分页查询年级")
    @GetMapping("/getGrades/{pageNo}/{PageSize}")
    public Result getGrades(
            @ApiParam("分页查询页码数")@PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小")@PathVariable("PageSize") Integer pageSize,
            @ApiParam("分页查询模糊匹配的名称")@RequestParam(value = "gradeName", required = false) String gradeName) {

        //分页
        Page<Grade> page = new Page<>(pageNo, pageSize);
        IPage<Grade> iPage = gradeService.getGradeByPage(page, gradeName);

        return Result.ok(iPage);
    }

    @ApiOperation("新增或修改年级")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("传入年级对象，有id则是修改，没有就是新增")@RequestBody Grade grade) {

        boolean b = gradeService.saveOrUpdate(grade);


        return Result.ok();
    }

    @ApiOperation("删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("要删除的所有id的json集合") @RequestBody List<Integer> ids) {

        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @GetMapping("/getGrades")
    @ApiOperation("返回班级管理中的年级名称")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();

        return Result.ok(grades);
    }
}
