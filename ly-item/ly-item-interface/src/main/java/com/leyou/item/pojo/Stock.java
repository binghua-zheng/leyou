package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Stock
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/7 22:49
 * @Version 1.0
 */
@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    private Long skuId;

    private Integer seckillStock;

    private Integer seckillTotal;

    private Integer stock;
}
