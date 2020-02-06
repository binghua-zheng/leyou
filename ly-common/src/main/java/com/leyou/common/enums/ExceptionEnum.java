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
    GOODS_NOT_FOND(404, "商品信息没查到")
    ;

    private int code;

    private String msg;

}
