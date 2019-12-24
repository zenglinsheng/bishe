package com.zls.bishe.service.impl;

import com.zls.bishe.mapper.ClassesMapper;
import com.zls.bishe.pojo.Classes;
import com.zls.bishe.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesService implements IClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes queryOne(Integer classId) {
        Classes classes = classesMapper.selectByPrimaryKey(classId);
        return classes;
    }

    @Override
    public List<Classes> queryAllClasses() {
        return classesMapper.selectAll();
    }
}
