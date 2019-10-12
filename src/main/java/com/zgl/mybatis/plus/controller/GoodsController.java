package com.zgl.mybatis.plus.controller;


import com.zgl.mybatis.plus.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zgl
 * @since 2019-10-12
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Resource
	private GoodsService goodsService;

	@GetMapping("/updateGoodsByIdVersion")
	public void updateGoodsByIdVersion() {
		goodsService.updateGoodsByIdVersion();
	}
}

