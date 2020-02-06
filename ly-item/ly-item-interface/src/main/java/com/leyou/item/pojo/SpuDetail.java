package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName SpuDetail
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 21:35
 * @Version 1.0
 */
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {

    @Id
    private Long spuId;

    private String description;

    private String genericSpec;

    private String specialSpec;

    private String packingList;

    private String afterService;

}
