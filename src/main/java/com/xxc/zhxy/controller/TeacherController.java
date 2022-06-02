package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxc.zhxy.pojo.Teacher;
import com.xxc.zhxy.service.TeacherService;
import com.xxc.zhxy.utils.MD5;
import com.xxc.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:40
 * @since JDK8
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @ApiOperation("分页带条件查询教师信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("页面数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页面大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("json传入条件") Teacher teacher
    ) {
        Page<Teacher> page1 = new Page<>(pageNo, pageSize);
        IPage<Teacher> page = teacherService.getTeacherByPage(page1, teacher);

        return Result.ok(page);

    }

    @ApiOperation("保存或修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("Json格式传入教师信息") @RequestBody Teacher teacher) {
        Integer id = null;
        if (teacher != null) {
            id = teacher.getId();
        }
        if (null == id || id == 0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("删除或批量删除教师")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("待删除教师id") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }


}
