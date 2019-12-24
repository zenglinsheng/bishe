package com.zls.bishe.service;

import com.zls.bishe.pojo.Classes;

import java.util.List;

public interface IClassesService {

    Classes queryOne(Integer classId);

    List<Classes> queryAllClasses();

}
