package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Brand
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 20:05
 * @Version 1.0
 */
@Data
@Table(name = "tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String image;

    private String letter;
}
