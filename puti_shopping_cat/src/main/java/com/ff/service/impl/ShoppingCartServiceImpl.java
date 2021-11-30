package com.ff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ff.dao.DishDao;
import com.ff.dao.UserDao;
import com.ff.domain.Dish;
import com.ff.domain.Result;
import com.ff.domain.ShoppingCart;
import com.ff.dao.ShoppingCartDao;
import com.ff.domain.User;
import com.ff.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author 方某
 * @since 2021-11-20
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements IShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DishDao dishDao;


    /**
     * 通过用户id查询添加的购物车商品
     * @param userid
     * @return
     */
    @Override
    public Result findAllShopByUserId(Long userid) {
        //判断输入的用户Id是否存在购物车内容
        LambdaQueryWrapper<ShoppingCart> sclqw = new LambdaQueryWrapper<>();
        sclqw.eq(ShoppingCart::getUserId,userid);
        List<ShoppingCart> shoppingCartById = shoppingCartDao.selectList(sclqw);
        if (shoppingCartById == null){
            return  Result.error("该用户没有添加商品到购物车！");
        }
//        int i =1/0;
        //返回结果
        return Result.success(shoppingCartById,"查询成功");
    }

    /**
     * 清空购物车  通过用户id
     * @param userid
     * @return
     */
    @Override
    public Result emptyShopByUserId(Long userid) {

        //清空用户购物车
        LambdaQueryWrapper<ShoppingCart> sclqw = new LambdaQueryWrapper<>();
        sclqw.eq(ShoppingCart::getUserId,userid);
        //校验用户购物内是否有商品
        ShoppingCart shoppingCart = shoppingCartDao.selectOne(sclqw);
        if(shoppingCart == null){
            return Result.error("购物车内暂无商品，再去逛逛");
        }
        int falg = shoppingCartDao.delete(sclqw);
        return falg>0? Result.success(null,"删除成功"):Result.error("删除失败");
    }

    /**
     * 用户添加食物到购物车内
     * @param userid
     * @param shoppingCart
     * @return
     */
    @Override
    public Result addFoodByUserId(Long userid, ShoppingCart shoppingCart) {
        //校验参数准确性
        if(shoppingCart == null){
            return Result.error("参数错误");
        }
        //添加的商品是否存在
        Dish dishById = dishDao.selectById(shoppingCart.getDishId());
        if(dishById == null){
            return Result.error("商品不存在，请重新尝试");
        }
        //判断商品是否已经停售
        if(dishById.getStatus() == 0){
            return Result.error("商品已经停售,请选择其他商品");
        }

        //校验用户添加的食物是否在购物车内存在  (用户id  商品id  口味都一致) 存在数量+1 不存在 添加进购物车
        LambdaQueryWrapper<ShoppingCart> sclqw = new LambdaQueryWrapper<>();
        sclqw.eq(ShoppingCart::getUserId,userid);
        sclqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        sclqw.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        ShoppingCart shoppingCartBySC = shoppingCartDao.selectOne(sclqw);
        //相同商品数量加一
        int falg = 0;
        if(shoppingCartBySC !=null){
            shoppingCartBySC.setNumber(shoppingCartBySC.getNumber()+1);
            shoppingCartBySC.setAmount(shoppingCartBySC.getAmount().add(shoppingCart.getAmount()));
             falg = shoppingCartDao.updateById(shoppingCartBySC);

        }else {
            //不存在就添加
            shoppingCart.setUserId(userid);
            shoppingCart.setName(dishById.getName());
            shoppingCart.setCreateTime(LocalDateTime.now());
            falg = shoppingCartDao.insert(shoppingCart);
        }

        //返回参数
        return falg>0? Result.success(null,"添加成功"):Result.error("添加失败");
    }


}
