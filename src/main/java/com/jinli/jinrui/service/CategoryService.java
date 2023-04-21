package com.jinli.jinrui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinli.jinrui.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
