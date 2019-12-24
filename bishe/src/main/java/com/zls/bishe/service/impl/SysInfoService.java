package com.zls.bishe.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.bishe.mapper.SysInfoMapper;
import com.zls.bishe.pojo.SysInfo;
import com.zls.bishe.service.ISysInfoService;
import com.zls.bishe.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysInfoService implements ISysInfoService {

    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Override
    public PagedResult queryReviewInfo(String userId, int pageNo, int pagesize) {
        PageHelper.startPage(pageNo, pagesize);
        Example example = new Example(SysInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("type",1);
        example.orderBy("updateTime").desc();
        List<SysInfo> sysInfoList = sysInfoMapper.selectByExample(example);
        PageInfo<SysInfo> pageInfo = new PageInfo<>(sysInfoList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNo);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setRows(sysInfoList);
        pagedResult.setTotal(pageInfo.getPages());

        return pagedResult;
    }

    @Override
    public PagedResult querySysInfo(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Example example = new Example(SysInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",0);
        example.orderBy("updateTime").desc();
        List<SysInfo> sysInfoList = sysInfoMapper.selectByExample(example);
        PageInfo<SysInfo> pageInfo = new PageInfo<>(sysInfoList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNo);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setRows(sysInfoList);
        pagedResult.setTotal(pageInfo.getPages());

        return pagedResult;
    }
}
