package com.zls.bishe.mapper;

import com.zls.bishe.pojo.Teacher;
import com.zls.bishe.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TeacherMapper extends MyMapper<Teacher> {

    int insertTeacherList(List<Teacher> list);

    int deleteTeachers(List<String> list);

    int recoverTeachers(List<String> list);

}