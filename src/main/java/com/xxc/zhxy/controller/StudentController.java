package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxc.zhxy.pojo.Student;
import com.xxc.zhxy.service.StudentService;
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
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;

    @ApiOperation("分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Student student
    ){

        Page<Student> page = new Page<>(pageNo,pageSize);
        IPage student1 = studentService.getStudentByPage(page,student);
        return Result.ok(student1);

    }

    @ApiOperation("修改或保存学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @ApiParam("Json格式传入学生信息") @RequestBody Student student){
        Integer id = student.getId();
        if (id==null||id==0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("删除或批量删除用户")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("待删除用户id的集合") @RequestBody List<Integer> ids
            ){
        studentService.removeByIds(ids);
        return Result.ok();
    }

}
