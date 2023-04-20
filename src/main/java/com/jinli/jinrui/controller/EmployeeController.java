package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.entity.Employee;
import com.jinli.jinrui.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.function.LongConsumer;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //将页面提交的密码进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //如果没有查询到则返回登录失败结果
        if(emp == null){
            return Result.error("登录失败");
        }
        //密码比对,不一致就返回登录失败
        if(!emp.getPassword().equals(password)){
            return Result.error("登录失败");
        }
        //查看员工的状态是否被禁用
        if(emp.getStatus() == 0){
            return Result.error("该账号已禁用");
        }
        //登录成功,将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return Result.success(emp);
    }

    /***
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //清除Session中保存的当前的员工id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    @PostMapping
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工的数据：{}", employee);
        //初始密码“123456”，使用md5进行加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //创建时间
        employee.setCreateTime(LocalDateTime.now());
        //修改时间
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前登陆的id
        Long empId =(Long) request.getSession().getAttribute("employee");
        //创建人
        employee.setCreateUser(empId);
        //修改人
        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return Result.success("新增成功");
    }

}
