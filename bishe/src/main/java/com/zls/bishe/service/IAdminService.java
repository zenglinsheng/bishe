package com.zls.bishe.service;

import com.zls.bishe.pojo.Admin;

public interface IAdminService {

    Admin queryOne(String adminId);

    void updateOne(Admin admin);

}
