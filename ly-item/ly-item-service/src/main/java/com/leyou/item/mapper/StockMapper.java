package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName StockMapper
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/7 22:58
 * @Version 1.0
 */
public interface StockMapper extends Mapper<Stock>, InsertListMapper<Stock>, DeleteByIdListMapper<Stock, Long> {
}
