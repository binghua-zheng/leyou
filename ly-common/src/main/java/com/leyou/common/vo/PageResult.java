package com.leyou.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 20:15
 * @Version 1.0
 */
@Data
public class PageResult<T> {

    private Long total;

    private Integer totalPage;;

    private List<T> items;

    public PageResult(){}

    public PageResult(Long total, List<T> items){
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items){
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
