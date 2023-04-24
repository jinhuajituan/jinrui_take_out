package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.entity.User;
import com.jinli.jinrui.mapper.UserMapper;
import com.jinli.jinrui.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
