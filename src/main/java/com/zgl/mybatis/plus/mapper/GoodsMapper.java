package com.zgl.mybatis.plus.mapper;

import com.zgl.mybatis.plus.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zgl
 * @since 2019-10-12
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

	int updateGoodsByIdVersion(Goods goods);
}