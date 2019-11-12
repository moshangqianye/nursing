package com.jqsoft.nursing.util;

import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017-06-30.
 */

public class BeanBase64Decoder {
    public static void decodeBase64Bean(Object o)  {
        if (o==null){
            return;
        }
//        LogUtil.i("before decode base64:"+ JSON.toJSONString(o));
        try {
            if (o instanceof List){
                List list = (List) o;
                if (!ListUtils.isEmpty(list)){
                    for (int i = 0; i < list.size(); ++i){
                        Object item = list.get(i);
                        decodeBase64Bean(item);
                    }
                }
            } else {
                Class<?> clazz = o.getClass();
                Field[] selfFields = clazz.getDeclaredFields();
                List<Field> fields = new ArrayList<Field>();
                Class<?> superclass = clazz.getSuperclass();
                while (superclass!=null){

                    Field[] parentFields = superclass.getDeclaredFields();
                    fields.addAll(Arrays.asList(parentFields));
                    superclass=superclass.getSuperclass();
                }
                fields.addAll(Arrays.asList(selfFields));
                if (fields!=null){
                    for (Field field : fields){
                        String fieldName = field.getName();
                        field.setAccessible(true);
                        Object fieldObject = Util.getFieldValueByName(fieldName, o);
//                        String fieldValue = (String) fieldObject;
                        if (fieldObject instanceof String){
                            String fieldValue = String.valueOf(fieldObject);
                            String decodedValue = Util.getDecodedBase64String(fieldValue);
                            field.set(o, decodedValue);
                        } else if (fieldObject instanceof List){
                            List list = (List) fieldObject;
                            if (!ListUtils.isEmpty(list)){
                                for (int i = 0; i < list.size(); ++i){
                                    Object item = list.get(i);
                                    decodeBase64Bean(item);
                                }
                            }
                        } else {
                            decodeBase64Bean(fieldObject);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("exception in decodeBase64Bean:"+e.getMessage());
        }
//        LogUtil.i("after decode base64:"+ JSON.toJSONString(o));
    }
}
