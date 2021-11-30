package com.ff.service.impl;

import com.ff.domain.Dish;
import com.ff.dao.DishDao;
import com.ff.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author 方某
 * @since 2021-11-20
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements IDishService {

}
