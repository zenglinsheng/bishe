package com.zls.bishe.service.impl;

import com.zls.bishe.exception.BusException;
import com.zls.bishe.mapper.AdminMapper;
import com.zls.bishe.pojo.Admin;
import com.zls.bishe.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin queryOne(String adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        admin.setPassword(null);
        return admin;
    }

    @Transactional
    @Override
    public void updateOne(Admin admin) {
        int i = adminMapper.updateByPrimaryKeySelective(admin);
        if(i == 0){
            throw new BusException("修改个人信息有误！");
        }
    }
}
