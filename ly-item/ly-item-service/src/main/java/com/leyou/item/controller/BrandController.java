package com.leyou.item.controller;

import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BrandController
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 20:10
 * @Version 1.0
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 查询品牌列表
     *
     * @param pageRequestParam
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(PageRequestParam pageRequestParam){
        return ResponseEntity.ok(brandService.queryBrandByPage(pageRequestParam));
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param categoryIds
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> categoryIds){
        brandService.saveBrand(brand, categoryIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类ID查询品牌信息
     *
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }


}
