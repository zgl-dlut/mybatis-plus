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
	public void updateGoodsByIdVersion(int count) {
		int goodId = 1;
		Goods goods = getById(goodId);
		int surplus = goods.getSurplus() - count;
		if (surplus < 0) {
			logger.info(Thread.currentThread().getName() + "\t库存不足");
			return;
		}
		goods.setSurplus(surplus);
		int updateStatus = goodsMapper.updateGoodsByIdVersion(goods);
		/**
		 * updateStatus == 0 说明没有更新成功
		 */
		while (updateStatus == 0) {
			logger.info(Thread.currentThread().getName() + "\t尝试更新状态失败");
			goods = getById(goodId);
			surplus = goods.getSurplus() - count;
			if (surplus < 0) {
				logger.info(Thread.currentThread().getName() + "\t库存不足");
				return;
			}
			goods.setSurplus(surplus);
			updateStatus = goodsMapper.updateGoodsByIdVersion(goods);
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

	@Override
	public void updateGoodsByCAS(String name, int count) {
		LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Goods::getName, name);
		Goods goods = getOne(queryWrapper);
		Map<String, Object> paramMap = BeanMapUtil.beanToMap(goods);
		paramMap.put("count", count);
		int updateStatus = goodsMapper.updateGoodsByCAS(paramMap);
		while (updateStatus == 0) {
			goods = getOne(queryWrapper);
			if (goods.getSurplus() >= count) {
				logger.info(Thread.currentThread().getName() + "\t重新尝试");
				queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(Goods::getName, name);
				goods = getOne(queryWrapper);
				paramMap = BeanMapUtil.beanToMap(goods);
				paramMap.put("count", count);
				updateStatus = goodsMapper.updateGoodsByCAS(paramMap);
			} else {
				logger.info(Thread.currentThread().getName() + "\t库存不足");
				return;
			}
		}
		logger.info(Thread.currentThread().getName() + "\t扣减库存成功");
	}

	@Override
	public void selectForUpdateById(int count) {
		int goodId = 2;
		Goods goods = goodsMapper.selectForUpdateById(goodId);
		int surplus = goods.getSurplus();
		if (surplus >= count) {
			LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
			goods.setSurplus(surplus - count);
			wrapper.eq(Goods::getId, goodId);
			update(goods, wrapper);
			logger.info(Thread.currentThread().getName() + "\t扣减库存成功");
		} else {
			logger.info(Thread.currentThread().getName() + "\t库存不足");
		}
	}
}
