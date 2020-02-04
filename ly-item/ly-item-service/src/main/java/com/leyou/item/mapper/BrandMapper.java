package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 添加品牌分类中间表
     *
     * @param brandId
     * @param categoryId
     */
    @Insert("INSERT INTO tb_category_brand VALUE(#{categoryId}, #{brandId});")
    public int insertBrandCategoryRelation(@Param("brandId") Long brandId, @Param("categoryId") Long categoryId);
}
