package com.zgl.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.zgl.mybatis.plus.entity.Goods;
import com.zgl.mybatis.plus.mapper.GoodsMapper;
import com.zgl.mybatis.plus.service.GoodsService;
import com.zgl.mybatis.plus.util.BeanMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zgl
 * @since 2019-10-12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);


	@Resource
	private GoodsMapper goodsMapper;


	@Override
	public void updateGoodsByIdVersion() {
		int goodId = 1;
		Goods goods = getById(goodId);
		int updateStatus = goodsMapper.updateGoodsByIdVersion(goods);
		/**
		 * 多个请求每个都能能够更新状态值
		 */
		while (updateStatus == 0) {
			logger.info(Thread.currentThread().getName() + "\t尝试更新状态失败");
			updateStatus = goodsMapper.updateGoodsByIdVersion(getById(goodId));
		}
		logger.info(Thread.currentThread().getName() + "\t更新状态成功");
	}

	@Override
	public synchronized void updateGoodsByNameSurplus(String name, int count) {
		LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Goods::getName, name);
		Goods goods = getOne(queryWrapper);
		Map<String, Object> paramMap = BeanMapUtil.beanToMap(goods);
		paramMap.put("count", count);
		int updateStatus = goodsMapper.updateGoodsByNameSurplus(paramMap);
		if (updateStatus <= 0) {
			logger.info(Thread.currentThread().getName() + "\t库存不足");
		} else {
			logger.info(Thread.currentThread().getName() + "\t扣减库存成功");
		}
	}
}
