package com.zls.bishe.mapper;

import com.zls.bishe.pojo.Student;
import com.zls.bishe.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentMapper extends MyMapper<Student> {

    int insertStudentList(List<Student> list);

    int deleteStudents(List<String> list);

    int recoverStudents(List<String> list);

}