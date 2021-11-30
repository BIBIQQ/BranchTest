package com.ff.dao;

import com.ff.domain.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品管理 Mapper 接口
 * </p>
 *
 * @author 方某
 * @since 2021-11-20
 */
@Mapper
public interface DishDao extends BaseMapper<Dish> {

}
