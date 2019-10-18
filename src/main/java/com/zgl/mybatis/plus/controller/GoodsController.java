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
		for (int i = 0; i < 20; i++) {
			new Thread(() -> goodsService.updateGoodsByIdVersion(5), String.valueOf(i)).start();
		}
	}

	@GetMapping("/updateGoodsByNameSurplus")
	public void updateGoodsByNameSurplus() {
		for (int i = 0; i < 100; i++) {
			new Thread(() -> goodsService.updateGoodsByNameSurplus("篮球", 5), String.valueOf(i)).start();
		}
	}

	@GetMapping("/updateGoodsByCAS")
	public void updateGoodsByCAS() {
		for (int i = 0; i < 20; i++) {
			new Thread(() -> goodsService.updateGoodsByCAS("篮球", 5), String.valueOf(i)).start();
		}
	}

	@GetMapping("/selectForUpdateById")
	public void selectForUpdateById() {
		for (int i = 0; i < 20; i++) {
			new Thread(() -> goodsService.selectForUpdateById(5), String.valueOf(i)).start();
		}
	}
}

