package com.zls.bishe.service;

import com.zls.bishe.utils.PagedResult;

public interface IInfoService {

    PagedResult selectStudentInfos(String start, String end, Integer pageNo, Integer pageSize);

    void reviewStudentInfo(Integer infoId);

    void reviewTeacherInfo(Integer infoId);

}
