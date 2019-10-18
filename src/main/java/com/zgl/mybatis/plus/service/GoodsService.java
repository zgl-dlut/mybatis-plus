package com.zgl.mybatis.plus.service;

import com.zgl.mybatis.plus.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zgl
 * @since 2019-10-12
 */
public interface GoodsService extends IService<Goods> {

	/**
	 * 乐观锁-版本号方法
	 */
	void updateGoodsByIdVersion(int count);

	/**
	 * 扣减库存
	 */
	void updateGoodsByNameSurplus(String name, int count);

	void updateGoodsByCAS(String name, int count);

	/**
	 * 悲观锁
	 */
	void selectForUpdateById(int count);
}
