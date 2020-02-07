package com.leyou.item.controller;

import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GoodsController
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 21:42
 * @Version 1.0
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品列表分页
     *
     * @param pageRequestParam
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(PageRequestParam pageRequestParam){
        return ResponseEntity.ok(goodsService.querySpuByPage(pageRequestParam));
    }

    /**
     * 添加商品
     *
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuId查询spu详情
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailBySpuId(@PathVariable("id") Long spuId){
        return ResponseEntity.ok(goodsService.queryDetailBySpuId(spuId));
    }

    /**
     * 根据spuId查询sku集合
     *
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(id));
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoodsInfo(@RequestBody Spu spu){
        goodsService.updateGoodsInfo(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
