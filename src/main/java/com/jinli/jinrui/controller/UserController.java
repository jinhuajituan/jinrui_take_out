package com.jinli.jinrui.controller;

import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.entity.User;
import com.jinli.jinrui.service.UserService;
import com.jinli.jinrui.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成4位随机的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}", code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("锦瑞点餐", "", phone, code);

            //需要将生成的验证码保存到Session里面
            session.setAttribute(phone, code);
            return Result.success("手机短信验证码发送成功");
        }
        return Result.error("短信验证码发送失败");
    }


}
