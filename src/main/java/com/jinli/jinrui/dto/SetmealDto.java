package com.jinli.jinrui.dto;

import com.jinli.jinrui.entity.Setmeal;
import com.jinli.jinrui.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
