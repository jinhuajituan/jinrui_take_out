package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.entity.Employee;
import com.jinli.jinrui.mapper.EmployeeMapper;
import com.jinli.jinrui.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
