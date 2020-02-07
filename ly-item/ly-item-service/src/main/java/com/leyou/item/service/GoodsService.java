package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

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

    /**
     * 添加商品
     *
     * @param spu
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(Spu spu) {
        // 添加spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        // 添加spuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        this.saveSkuAndStock(spu);
    }

    /**
     * 根据spuId查询spu详情
     *
     * @param spuId
     * @return
     */
    public SpuDetail queryDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOND);
        }
        return spuDetail;
    }

    /**
     * 根据spuId查询sku
     *
     * @param id
     * @return
     */
    public List<Sku> querySkuBySpuId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        for (Sku e : skuList) {
            Long skuId = e.getId();
            Stock stock = stockMapper.selectByPrimaryKey(skuId);
            e.setStock(stock.getStock());
        }
        return skuList;
    }

    /**
     * 修改商品信息
     *
     * @param spu
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsInfo(Spu spu) {
        if (spu.getId() == null) {
            throw new LyException(ExceptionEnum.GOODS_ID_NOT_FOUND);
        }
        // 根据spuId删除之前的sku和stock
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)) {
            // 删除sku
            skuMapper.delete(sku);
            // 获取skuID
            List<Long> skuId = skuList.stream().map(e -> e.getId()).collect(Collectors.toList());
            // 删除库存stock
            stockMapper.deleteByIdList(skuId);
        }
        // 新增sku和stock
        this.saveSkuAndStock(spu);
        // 更新spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setCreateTime(null);
        spu.setLastUpdateTime(new Date());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        // 更新spuDetail
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
    }

    /**
     * 添加sku和stock
     *
     * @param spu
     */
    private void saveSkuAndStock(Spu spu) {
        // 添加sku
        List<Sku> skuList = spu.getSkus();
        List<Stock> stockList = new ArrayList<>();
        int count = 0;
        for (Sku sku : skuList) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            count = skuMapper.insert(sku);
            if (count != 1) {
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            // 获取stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        // 添加stock
        stockMapper.insertList(stockList);
    }
}
