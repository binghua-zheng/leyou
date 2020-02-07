package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Spu
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 21:28
 * @Version 1.0
 */
@Data
@Table(name = "tb_spu")
public class Spu {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String title;

    private String subTitle;

    private Long cid1;

    private Long cid2;

    private Long cid3;

    private Long brandId;

    private Boolean saleable;

    @JsonIgnore
    private Boolean valid;

    private Date createTime;

    @JsonIgnore
    private Date lastUpdateTime;

    @Transient
    private String bName;

    @Transient
    private String cName;

    @Transient
    private List<Sku> skus;

    @Transient
    private SpuDetail spuDetail;
}
