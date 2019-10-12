package com.zgl.mybatis.plus.service.impl;

import com.zgl.mybatis.plus.entity.Goods;
import com.zgl.mybatis.plus.mapper.GoodsMapper;
import com.zgl.mybatis.plus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zgl
 * @since 2019-10-12
 */
@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	@Resource
	private GoodsMapper goodsMapper;

	@Override
	public void updateGoodsByIdVersion() {
		int goodId = 1;
		//id = 1, version = 1, status = 1
		Goods goods1 = getById(goodId);
		Goods goods2 = getById(goodId);
		goods1.setStatus(2);
		//id = 1, version = 2, status = 2;
		int result1 = goodsMapper.updateGoodsByIdVersion(goods1);
		System.out.println("修改商品信息1" + (result1 == 1 ? "成功" : "失败"));

		goods2.setStatus(2);
		//id = 1, version = 2, 此时goods2中的version = 1, 更新失败
		int result2 = goodsMapper.updateGoodsByIdVersion(goods2);
		System.out.println("修改商品信息2" + (result2 == 1 ? "成功" : "失败"));
	}
}
