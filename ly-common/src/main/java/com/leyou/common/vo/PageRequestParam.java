package com.leyou.common.vo;

import lombok.Data;

/**
 * @ClassName PageRequestParam
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 20:26
 * @Version 1.0
 */
@Data
public class PageRequestParam {

    private Integer page;

    private Integer rows;

    private String sortBy;

    private Boolean desc;

    private String key;

    private Boolean saleable;
}
