package com.zls.bishe.utils;

import com.zls.bishe.common.annotation.*;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by wens on 16-11-30.
 */
public class ExampleUtils {

    private static final String CONDITION_PKG = Condition.class.getName().substring(0,Condition.class.getName().lastIndexOf("."));

    public static void  fillCondition(Example.Criteria criteria , Long areaTeamId, Long busTeamId) {
        if(areaTeamId != null && areaTeamId != 0 ){
            criteria.andEqualTo("areaTeamId", areaTeamId );
        }
        if(busTeamId != null && busTeamId != 0 ){
            criteria.andEqualTo("busTeamId", busTeamId );
        }
    }

    public static void fillCondition(Example.Criteria criteria, Object condition) {

        Field[] fields = getFields(condition.getClass());

        if (fields == null || fields.length == 0) {
            return;
        }

        for (Field field : fields) {

            if( field.getAnnotation(Ignore.class) != null ){
                continue;
            }

            String name = field.getName();
            Object value = getValue(condition, field);
            if (value == null) {
                continue;
            }

            if (value instanceof String) {
                String v = (String) value;
                if (StringUtils.isEmpty(v)) {
                    continue;
                }

                v = v.trim();

                Annotation[] annotations = filterCondition(field.getDeclaredAnnotations());

                if (annotations == null || annotations.length == 0) {
                    criteria.andLike(name, "%" + v + "%");
                } else {
                    Annotation annotation = annotations[0];

                    if (annotation instanceof Equal) {
                        Equal equal = (Equal) annotation;
                        criteria.andEqualTo(StringUtils.isEmpty(equal.name()) ? name : equal.name(), value);
                    } else if (annotation instanceof LeftLike) {
                        LeftLike leftLike = (LeftLike) annotation;
                        criteria.andLike(StringUtils.isEmpty(leftLike.name()) ? name : leftLike.name(), value + "%");
                    } else if (annotation instanceof RightLike) {
                        RightLike rightLike = (RightLike) annotation;
                        criteria.andLike(StringUtils.isEmpty(rightLike.name()) ? name : rightLike.name(), "%" + value);
                    } else if (annotation instanceof FullLike) {
                        FullLike fullLike = (FullLike) annotation;
                        criteria.andLike(StringUtils.isEmpty(fullLike.name()) ? name : fullLike.name(), "%" + v + "%");
                    } else if (annotation instanceof In){
                        In inList=(In) annotation;
                        String type=inList.type();
                        List list=null;
                        if ("Longs".equals(type)){
                            list=toLongIds((String)value);
                            if (list!=null && list.size()>0){
                                criteria.andIn(StringUtils.isEmpty(inList.name()) ? name : inList.name(),list);
                            }
                        }
                    } else if (annotation instanceof LessThan) {
                        LessThan lessThan = (LessThan) annotation;
                        criteria.andLessThan(StringUtils.isEmpty(lessThan.name()) ? name : lessThan.name(), value);
                    } else if (annotation instanceof GreatThan) {
                        GreatThan greatThan = (GreatThan) annotation;
                        criteria.andGreaterThan(StringUtils.isEmpty(greatThan.name()) ? name : greatThan.name(), value);
                    } else if (annotation instanceof GreatOrEqualThan) {
                        GreatOrEqualThan greatOrEqualThan = (GreatOrEqualThan) annotation;
                        criteria.andGreaterThanOrEqualTo(StringUtils.isEmpty(greatOrEqualThan.name()) ? name : greatOrEqualThan.name(), value);
                    } else if (annotation instanceof LessOrEqualThan) {
                        LessOrEqualThan lessOrEqualThan = (LessOrEqualThan) annotation;
                        criteria.andLessThanOrEqualTo(StringUtils.isEmpty(lessOrEqualThan.name()) ? name : lessOrEqualThan.name(), value);
                    }
                    else {
                        criteria.andLike(name, "%" + v + "%");
                    }
                }
            } else if (field.getType().isArray()) {
                List<Object> values = Arrays.asList((Object[]) value);
                if(!values.isEmpty()){
                    criteria.andIn(name, values );
                }else{
                    criteria.andCondition("1 <> 1");
                }

            } else if (value instanceof Collection) {
                Annotation[] annotations = filterCondition(field.getDeclaredAnnotations());
                if (annotations!=null && annotations.length>0){
                    Annotation annotation = annotations[0];
                    if (annotation instanceof In){
                        In inList=(In) annotation;
                        String type=inList.type();
                        List list=null;
                        if ("String".equals(type)){
                            list=(List<String>)value;
                        }else if ("Integer".equals(type)){
                            list=(List<Integer>)value;
                        }else if ("Long".equals(type)){
                            list=(List<Long>)value;
                        }else if ("Longs".equals(type)){
                            list=toLongIds((String)value);
                        }
                        if (list!=null && list.size()>0){
                            criteria.andIn(StringUtils.isEmpty(inList.name()) ? name : inList.name(),list);
                        }
                    }
                }else {
                    Collection values = (Collection) value;
                    if(!values.isEmpty()){
                        criteria.andIn(name, values );
                    }else{
                        criteria.andCondition("1 <> 1");
                    }
                }

            } else {
                Annotation[] annotations = filterCondition(field.getDeclaredAnnotations());
                if (annotations == null || annotations.length == 0) {
                    criteria.andEqualTo(name, value);
                } else {
                    Annotation annotation = annotations[0];
                    if (annotation instanceof Equal) {
                        Equal equal = (Equal) annotation;
                        criteria.andEqualTo(StringUtils.isEmpty(equal.name()) ? name : equal.name(), value);
                    } else if (annotation instanceof LessThan) {
                        LessThan lessThan = (LessThan) annotation;
                        criteria.andLessThan(StringUtils.isEmpty(lessThan.name()) ? name : lessThan.name(), value);
                    } else if (annotation instanceof GreatThan) {
                        GreatThan greatThan = (GreatThan) annotation;
                        criteria.andGreaterThan(StringUtils.isEmpty(greatThan.name()) ? name : greatThan.name(), value);
                    }else if (annotation instanceof GreatOrEqualThan) {
                        GreatOrEqualThan greatOrEqualThan = (GreatOrEqualThan) annotation;
                        criteria.andGreaterThanOrEqualTo(StringUtils.isEmpty(greatOrEqualThan.name()) ? name : greatOrEqualThan.name(), value);
                    }else if (annotation instanceof LessOrEqualThan) {
                        LessOrEqualThan lessOrEqualThan = (LessOrEqualThan) annotation;
                        criteria.andLessThanOrEqualTo(StringUtils.isEmpty(lessOrEqualThan.name()) ? name : lessOrEqualThan.name(), value);
                    }else{
                        criteria.andEqualTo(name, value);
                    }
                }
            }
        }
    }



    private static Object getValue(Object obj, Field field) {
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public static void fillConditionForAreaTeamPermission(Example.Criteria criteria, List<Long> areaTeamIds) {
        if(areaTeamIds != null && !areaTeamIds.isEmpty() ){
            criteria.andIn("areaTeamId" , areaTeamIds );
        }else{
            criteria.andCondition(" 1 <> 1 ");
        }
    }

    public static void fillConditionForBusPermission(Example.Criteria criteria, List<Long> busTeamIds) {

        if(busTeamIds != null && !busTeamIds.isEmpty() ){
            criteria.andIn("busTeamId" , busTeamIds );
        }else{
            criteria.andCondition(" 1 <> 1 ");
        }
    }


    private static Field[] getFields(Class<?> aClass) {

        List<Field> fields = new ArrayList<>(aClass.getDeclaredFields().length * 2 );
        while (true){
            fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
            aClass = aClass.getSuperclass();
            if(aClass == null ){
                break;
            }
        }
        return fields.toArray(new Field[fields.size()]);
    }

    private static Annotation[] filterCondition(Annotation[] annotations){
        List<Annotation> as = new ArrayList<>(annotations.length);
        for(int i = 0 ; i < annotations.length ; i++ ){
            if(annotations[i].annotationType().getName().startsWith(CONDITION_PKG)){
                as.add(annotations[i]);
            }
        }
        return as.toArray(new Annotation[annotations.length]);
    }

    public static List<Long> toLongIds(String idsStr) {

        if (StringUtils.isEmpty(idsStr)) {
            return Collections.EMPTY_LIST;
        }

        String[] idsArr = idsStr.split(",");
        if (idsArr.length == 0) {
            return Collections.emptyList();
        }
        List<Long> ids = new ArrayList<>();
        for (String c : idsArr) {
            ids.add(Long.valueOf(c.trim()));
        }
        return ids;
    }


}
