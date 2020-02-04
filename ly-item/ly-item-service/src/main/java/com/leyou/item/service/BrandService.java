package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName BrandService
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 20:09
 * @Version 1.0
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询品牌列表
     *
     * @param pageRequestParam
     * @return
     */
    public PageResult<Brand> queryBrandByPage(PageRequestParam pageRequestParam){
        // pageHelper分页
        PageHelper.startPage(pageRequestParam.getPage(), pageRequestParam.getRows());
        // 创建Example页面筛选条件
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(pageRequestParam.getKey())) {
            example.createCriteria().orLike("name", "%" + pageRequestParam.getKey() + "%")
                    .orEqualTo("letter", pageRequestParam.getKey().toUpperCase());
        }
        // 页面排序
        if (StringUtils.isNotBlank(pageRequestParam.getSortBy())) {
            String sortByClause = pageRequestParam.getSortBy() + (pageRequestParam.getDesc() ? " DESC" : " ASC");
            example.setOrderByClause(sortByClause);
        }
        // 总查询sql
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }
        // 解析分页结果，处理结果总数
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), list);
    }

    /**
     * 添加品牌
     *
     * @param brand
     * @param categoryIds
     */
    public void saveBrand(Brand brand, List<Long> categoryIds){
        int count = 0;
        // 添加品牌表
        count = brandMapper.insertSelective(brand);
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        // 添加品牌-分类中间表
        for (Long cId : categoryIds) {
            count = brandMapper.insertBrandCategoryRelation(brand.getId(), cId);
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }
}
