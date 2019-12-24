package com.zls.bishe.common;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.zls.bishe.utils.PagedResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
public class ReturnResult<T> {

    private boolean success;
    private String desc;
    private T data;
    private Integer code;

    public ReturnResult(boolean success, String desc ) {
        this(success, desc ,null);
    }

    public ReturnResult(boolean success, String desc , T data) {
        this.success = success;
        this.desc = desc;
        this.data = data;
    }
    public ReturnResult(boolean success, String desc , Integer code, T data) {
        this.success = success;
        this.desc = desc;
        this.code=code;
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static ReturnResult success() {
        return new ReturnResult(true, "OK");
    }

    public static ReturnResult success(String desc) {
        return new ReturnResult(true, desc);
    }

    public static <T> ReturnResult success(Page<T> page) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("totalPage", page.getPages());
        data.put("pageIndex", page.getPageNum());
        data.put("total", page.getTotal());
        data.put("list", page.getResult());
        return new ReturnResult(true, "查询成功" , data);
    }

    public static <T> ReturnResult success(T data) {
        return new ReturnResult(true, "处理成功" , data);
    }

    public static ReturnResult fail(String desc ) {
        return new ReturnResult(false, desc );
    }

    public static  <T> ReturnResult fail(String desc,Integer code,T data ) {
        return new ReturnResult(false, desc,code,data );
    }


    public static ReturnResult fail(String desc , Object data ) {
        return new ReturnResult(false, desc , data );
    }


    public String  toJsonString() {
        return JSONObject.toJSONString(this,true);
    }

    public static String ok() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count",0);
        map.put("data", Collections.EMPTY_LIST);
        map.put("code", 0);
        map.put("msg","success");
        return JSONObject.toJSONString(map);
    }

    public static String ok(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count",0);
        map.put("data", object);
        map.put("code", 0);
        map.put("msg","success");
        return JSONObject.toJSONString(map);
    }

    public static String success(PagedResult result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count",result.getRecords());
        map.put("data",result.getRows());
        map.put("code", 0);
        map.put("msg","success");
        return JSONObject.toJSONString(map);
    }
}