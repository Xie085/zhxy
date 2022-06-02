package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxc.zhxy.pojo.Clazz;
import com.xxc.zhxy.service.ClazzService;
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
@Api(tags = "班级管理")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("分页查询页码数") @PathVariable("pageNo")Integer pageNo,
            @ApiParam("分页查询大小") @PathVariable("pageSize")Integer pageSize,
            @ApiParam("分页查询条件") @RequestBody(required = false) Clazz clazz){

        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage iPage = clazzService.getClazzByPage(page,clazz);
        return Result.ok(iPage);

    }

    @ApiOperation("增加或修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("Json格式的班级信息") @RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("删除或批量删除班级")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @ApiParam("删除班级所用到的id") @RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("学生管理用到的查询所有班级")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzes = clazzService.getClazzs();
        return Result.ok(clazzes);
    }

}
