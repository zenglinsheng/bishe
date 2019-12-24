package com.zls.bishe.mapper;

import com.zls.bishe.pojo.Classes;
import com.zls.bishe.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClassesMapper extends MyMapper<Classes> {

    @Update("update classes set num = num + 1,update_time=NOW() where class_id = #{classId}")
    int increaseNum(@Param("classId") Integer classId);

    int updateClassList(List<Classes> list);

}