package com.jinli.jinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinli.jinrui.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
