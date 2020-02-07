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
    INVALID_FILE_TYPE(400, "文件类型错误"),
    UPLOAD_FILE_ERROR(500, "上传文件错误"),
    SPECGROP_NOT_FOND(404, "规格组没查到"),
    SPEC_INFO_NOT_FOND(404, "规格信息没查到"),
    GOODS_NOT_FOND(404, "商品信息没查到"),
    GOODS_SAVE_ERROR(500, "添加品牌错误"),
    GOODS_DETAIL_NOT_FOND(404, "商品详情信息没查到"),
    GOODS_SKU_NOT_FOND(404, "商品SKU信息没查到"),
    GOODS_UPDATE_ERROR(500, "更新商品错误"),
    GOODS_ID_NOT_FOUND(400, "商品ID没有查到"),
    ;

    private int code;

    private String msg;

}
