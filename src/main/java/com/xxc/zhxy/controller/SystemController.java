package com.xxc.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxc.zhxy.pojo.Admin;
import com.xxc.zhxy.pojo.LoginForm;
import com.xxc.zhxy.pojo.Student;
import com.xxc.zhxy.pojo.Teacher;
import com.xxc.zhxy.service.AdminService;
import com.xxc.zhxy.service.StudentService;
import com.xxc.zhxy.service.TeacherService;
import com.xxc.zhxy.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:45
 * @since JDK8
 */
@Api(tags = "无分类控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;


    @ApiOperation("验证码更新")
    @GetMapping("/getVerifiCodeImage")
    public void getVerificodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());

        //将验证码文本放入session域中
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);

        //将验证码图片相应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @ApiOperation("登录信息验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVer = loginForm.getVerifiCode();
        if ("".equals(loginVer) || null == sessionVerifiCode) {
            return Result.fail().message("验证码已失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVer)) {
            return Result.fail().message("验证码输入有误，请重新输入");
        }
        //从session中移除验证码
        session.removeAttribute("verifiCode");
        //分用户进行验证
        Integer userType = loginForm.getUserType();

        //创建一个Map用于存放相应数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin != null){
                        //找到了，间用户数据转化为密文，以token返回给客户端
                        String token = JwtHelper.createToken(admin.getId().longValue(),1);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (student != null){
                        //找到了，间用户数据转化为密文，以token返回给客户端
                        String token = JwtHelper.createToken(student.getId().longValue(),2);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null){
                        //找到了，间用户数据转化为密文，以token返回给客户端
                        String token = JwtHelper.createToken(teacher.getId().longValue(),3);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("用户没找到");
    }

    @ApiOperation("检查用户登录的权限返回token")
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader String token){
        //检查token是否过期
        boolean b = JwtHelper.isExpiration(token);
        if (b){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //解析出id
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        System.out.println("userType = " + userType);
        System.out.println("userId = " + userId);
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                System.out.println("admin = " + admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }

        return Result.ok(map);
    }

    @ApiOperation("文件上传同一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("要上传的头像文件") @RequestPart("multipartFile") MultipartFile multipartFile,
            HttpServletRequest request
    ){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String name = multipartFile.getOriginalFilename();
        name = uuid + name.substring(name.lastIndexOf('.'),name.length());

        //保存文件  将文件发送到第三方独立的图片服务器上
//        request.getServletContext().getRealPath("public/upload");
        String path = "D:/java/daima/zhxy/target/classes/public/upload/".concat(name);
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //相应图片路径
        String returnPath = "upload/" + name;

        return Result.ok(returnPath);
    }

    @ApiOperation("修改密码的处理器")
    @PostMapping("/updatePwd/{owd}/{nwd}")
    public Result updatePwd(
            @ApiParam("旧密码") @PathVariable("owd")String owd,
            @ApiParam("新密码") @PathVariable("nwd")String nwd,
            @ApiParam("登录后保存的token") @RequestHeader("token")String token
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.fail().message("token失效，请先重新登陆！");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);

        String oldpwd = MD5.encrypt(owd);
        String newpwd = MD5.encrypt(nwd);


        Map map = new HashMap();
        switch (userType){
            case 1:
                QueryWrapper<Admin> wrapper = new QueryWrapper<>();
                wrapper.eq("id",userId.intValue());
                wrapper.eq("password", oldpwd);
                Admin admin = adminService.getOne(wrapper);
                if (admin!=null){
                    admin.setPassword(newpwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("id",userId.intValue());
                wrapper1.eq("password", oldpwd);
                Student student = studentService.getOne(wrapper1);
                if (student!=null){
                    student.setPassword(newpwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                QueryWrapper<Teacher> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("id",userId.intValue());
                wrapper2.eq("password", oldpwd);
                Teacher teacher = teacherService.getOne(wrapper2);
                if (teacher!=null){
                    teacher.setPassword(newpwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }
}
