package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName SpecGrop
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 19:14
 * @Version 1.0
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGrop {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long cid;

    private String name;
}
