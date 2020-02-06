package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsService
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 21:40
 * @Version 1.0
 */
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询规格详细信息
     *
     * @param pageRequestParam
     * @return
     */
    public PageResult<Spu> querySpuByPage(PageRequestParam pageRequestParam) {
        // 分页
        PageHelper.startPage(pageRequestParam.getPage(), pageRequestParam.getRows());
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 过滤页面查询条件
        if (StringUtils.isNotBlank(pageRequestParam.getKey())) {
            criteria.andLike("title", "%" + pageRequestParam.getKey() + "%");
        }
        if (pageRequestParam.getSaleable() != null) {
            criteria.andEqualTo("saleable", pageRequestParam.getSaleable());
        }
        // 页面排序
        example.setOrderByClause("last_update_time DESC");
        // 查询sql
        List<Spu> list = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }
        // 查询品牌名称，商品分类名称
        loadCategoryAndBrandName(list);
        // 解析分页结果，查询总数
        PageInfo<Spu> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

    }

    /**
     * 查询品牌名称，分类名称
     *
     * @param list
     */
    private void loadCategoryAndBrandName(List<Spu> list) {
        for (Spu spu : list) {
            // 查询商品分类名称
            List<Long> ids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
            List<Category> categoryList = categoryService.queryCategoryByIds(ids);
            List<String> categoryNameList = categoryList.stream().map(e -> e.getName()).collect(Collectors.toList());
            // 分类名称
            spu.setCName(StringUtils.join(categoryNameList, "/"));
            // 品牌名称
            Brand brand = brandService.queryBrandById(spu.getBrandId());
            spu.setBName(brand.getName());
        }
    }
}
