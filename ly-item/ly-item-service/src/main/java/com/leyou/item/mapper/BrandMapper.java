package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 添加品牌分类中间表
     *
     * @param brandId
     * @param categoryId
     */
    @Insert("INSERT INTO tb_category_brand VALUE(#{categoryId}, #{brandId});")
    public int insertBrandCategoryRelation(@Param("brandId") Long brandId, @Param("categoryId") Long categoryId);

    @Select("SELECT b.*  FROM tb_brand b INNER JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    public List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
