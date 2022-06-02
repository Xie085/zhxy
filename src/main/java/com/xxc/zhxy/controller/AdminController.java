package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxc.zhxy.pojo.Admin;
import com.xxc.zhxy.service.AdminService;
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
 * @version 2022/5/31 14:39
 * @since JDK8
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ApiOperation("分页带条件查询管理员")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页面数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页面大小") @PathVariable("pageSize") Integer pageSize,
            Admin admin
    ){

        Page<Admin> page = new Page<Admin>(pageNo, pageSize);
        IPage admins = adminService.getAdminByPage(page,admin);
        return Result.ok(admins);
    }

    @ApiOperation("保存和修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("Json传入admin信息") @RequestBody Admin admin){
        Integer id = admin.getId();
        if (id ==null||id==0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("删除或批量删除管理员")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("待删除用户id集合") @RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

}
