package com.ff.service;

import com.ff.domain.Dish;
import com.ff.domain.Result;
import com.ff.domain.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author 方某
 * @since 2021-11-20
 */
public interface IShoppingCartService extends IService<ShoppingCart> {
    /**
     * 查询购物车列表
     * @param userid
     * @return
     */
    Result findAllShopByUserId( Long userid);

    /**
     * 清空用户购物车
     * @param userid
     * @return
     */
    Result emptyShopByUserId( Long userid);

    /**
     * 用户添加购物车商品
     * @param userid
     * @param shoppingCart
     * @return
     */
    Result addFoodByUserId( Long userid,  ShoppingCart shoppingCart);
}
