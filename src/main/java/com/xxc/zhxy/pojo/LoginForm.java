package com.xxc.zhxy.pojo;

import lombok.Data;

/**
 * @author xiangcheng
 * @version 2022/5/31 14:20
 * @since JDK8
 * 用户提交的表单
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
