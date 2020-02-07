package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGrop;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SpecificationController
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 19:18
 * @Version 1.0
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类ID查询规格分组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGrop>> queryGroupByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    /**
     * 根据规格组ID/分类ID/是否搜索查询规格详细信息
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(@RequestParam(name = "gid", required = false) Long gid,
                                                           @RequestParam(name = "cid", required = false) Long cid,
                                                           @RequestParam(name = "searching", required = false) Boolean searching){
        return ResponseEntity.ok(specificationService.queryParamByGid(gid, cid, searching));
    }

}
