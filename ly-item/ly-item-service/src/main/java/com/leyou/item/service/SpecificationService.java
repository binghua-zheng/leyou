package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGropMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGrop;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName SpecificationService
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 19:18
 * @Version 1.0
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGropMapper specGropMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 查询规格分组信息
     *
     * @param cid
     * @return
     */
    public List<SpecGrop>queryGroupByCid(Long cid) {
        SpecGrop specGrop = new SpecGrop();
        specGrop.setCid(cid);
        List<SpecGrop> list = specGropMapper.select(specGrop);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPECGROP_NOT_FOND);
        }

        return list;
    }

    /**
     * 根据规格组ID/分类ID/是否搜索查询详细规格
     *
     * @param gid
     * @return
     */
    public List<SpecParam> queryParamByGid(Long gid, Long cid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPEC_INFO_NOT_FOND);
        }
        return list;
    }
}
