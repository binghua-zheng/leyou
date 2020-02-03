package com.leyou.item.controller;

import com.leyou.common.vo.PageRequestParam;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public PageResult<Brand> queryBrandByPage(PageRequestParam pageRequestParam){
        return brandService.queryBrandByPage(pageRequestParam);
    }
}
