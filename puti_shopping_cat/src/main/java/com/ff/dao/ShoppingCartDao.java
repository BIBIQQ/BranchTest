package com.ff.dao;

import com.ff.domain.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author 方某
 * @since 2021-11-20
 */
@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {

}
