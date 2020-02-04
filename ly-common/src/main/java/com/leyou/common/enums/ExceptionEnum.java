package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400, "价格不能为空"),
    CATEGORY_NOT_FOND(404, "商品分类没查到"),
    BRAND_NOT_FOND(404, "商品品牌没有找到"),
    BRAND_SAVE_ERROR(500, "添加品牌错误"),
    ;

    private int code;

    private String msg;

}
