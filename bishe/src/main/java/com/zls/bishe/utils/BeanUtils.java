package com.zls.bishe.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.zls.bishe.vo.StudentVO;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class BeanUtils {

    public static void copyProperties(Object dest, Object orig) {
        try {
            org.apache.commons.beanutils.PropertyUtils.copyProperties(dest, orig);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public static <K, V> Map<K, V> toMap(List<V> list, String keyName) {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_MAP;
        }

        Map<K, V> map = Maps.newHashMap();
        for (V v : list) {
            try {
                map.put((K) PropertyUtils.getProperty(v, keyName), v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <K, V> Map<K, V> toMap(List<V> list, Function<V,K> function) {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_MAP;
        }

        Map<K, V> map = Maps.newHashMap();
        for (V v : list) {
            try {
                map.put(function.apply(v), v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T> List<T> removeNullAndEmpty(List<? extends T> oldList) {
        // 临时集合
        if (oldList==null || oldList.isEmpty()){
            return new ArrayList<>();
        }
        List<T> listTemp = new ArrayList(oldList.size());
        for (int i = 0;i < oldList.size(); i++) {
            // 保存不为空的元素
            if (oldList.get(i) != null && !oldList.get(i).equals("")) {
                listTemp.add(oldList.get(i));
            }
        }
        return listTemp;
    }


    public static <V> List<V> toList(List<?> list, String keyName) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }

        List<V> newList = new ArrayList<>(list.size());
        for (Object o : list) {
            try {
                Object v = PropertyUtils.getProperty(o, keyName);
                if(v != null ){
                    newList.add((V)v );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newList;
    }

    public static <K, V> Map<K, List<V>> toGroup(List<V> list, String keyName) {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_MAP;
        }

        Map<K, List<V>> map = Maps.newHashMap();
        for (V v : list) {
            try {
                K k  = (K) PropertyUtils.getProperty(v, keyName);
                List<V> l = map.get(k);
                if(l == null ){
                    l = new ArrayList<>(5);
                }
                l.add(v);
                map.put( k , l );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <K, V> Map<K, List<V>> toLinkGroup(List<V> list, String keyName) {
        if (list == null || list.size() == 0) {
            return Maps.newLinkedHashMap();
        }

        Map<K, List<V>> map = Maps.newLinkedHashMap();
        for (V v : list) {
            try {
                K k  = (K) PropertyUtils.getProperty(v, keyName);
                List<V> l = map.get(k);
                if(l == null ){
                    l = new ArrayList<>(5);
                }
                l.add(v);
                map.put( k , l );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public static <K, V> Map<K, List<V>> toGroup(List<V> list, Function<V,K> function)  {

        if (list == null || list.size() == 0) {
            return Collections.EMPTY_MAP;
        }
        Map<K, List<V>> map = Maps.newHashMap();
        for (V v : list) {
            try {
                K k  = (K) function.apply(v);
                List<V> l = map.get(k);
                if(l == null ){
                    l = new ArrayList<>(5);
                }
                l.add(v);
                map.put( k , l );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public static void contrast(Object oldModel, Object newModel) {
        Class object = oldModel.getClass();
        Method[] methods = object.getDeclaredMethods();
        Map objMeMap = new HashMap(methods.length);
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            objMeMap.put(method.getName(), method);
        }

        for(Method one:methods) {
            String methodName = one.getName();
            if (methodName.indexOf("get") == 0) {
                try {
                    if (Objects.equals(one.invoke(oldModel), one.invoke(newModel)) || (one.invoke(newModel) == null)) {
                    /*String fieldName = methodName.substring(3,4).toLowerCase()+methodName.substring(4);
                    System.out.println("field:"+fieldName+" old:"+one.invoke(oldModel)+" new:"+one.invoke(newModel));*/

                        Object returnObj = one.invoke(oldModel, new Object[0]);
                        Method setmethod = (Method) objMeMap.get("set" + methodName.split("get")[1]);
                        if (returnObj != null) {
                            returnObj = null;
                        }
                        setmethod.invoke(oldModel, returnObj);
                        setmethod.invoke(newModel, returnObj);
                    }
                } catch (InvocationTargetException e) {
                        e.printStackTrace();
                }
                  catch (IllegalAccessException e) {
                        e.printStackTrace();
                }
            }
        }



    }

    public static void main(String[] args) throws Exception {
        StudentVO oldStudent = new StudentVO();
        StudentVO newStudent = new StudentVO();
        oldStudent.setTelephone("111111");
        oldStudent.setClassName("16软4");
        newStudent.setClassName("16软4");
        newStudent.setTelephone("222222");
        oldStudent.setEmail("wer");
        newStudent.setEmail("rerrtt");
        contrast(oldStudent,newStudent);
        System.out.println(JSONObject.toJSONString(oldStudent));
        System.out.println(JSONObject.toJSONString(newStudent));
        String s = JSONObject.toJSONString(Arrays.asList(oldStudent, newStudent));
        System.out.println(s);
    }

}
