package com.ping.easyfile.util;

import com.ping.easyfile.annotation.ExcelWriteProperty;
import com.ping.easyfile.excelmeta.BaseColumnProperty;
import com.ping.easyfile.excelmeta.BaseRowModel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 16:58
 */
public class FieldUtil {
    /**
     * @param field
     * @param tClass
     * @return
     */
    public static <T extends BaseColumnProperty> T annotationToObject(Field field, Class<T> tClass) {
        T t = null;
        try {
            ExcelWriteProperty annotation = field.getAnnotation(ExcelWriteProperty.class);
            if (null != annotation && null != tClass) {
                String format = annotation.format();
                Class<?>[] fieldClass = {Field.class, int.class, List.class};
                Object[] fieldValue = {field, annotation.index(), Arrays.asList(annotation.value())};
                if (StringUtils.isNotBlank(format)) {
                    fieldClass = DataUtil.concatAll(fieldClass, String.class);
                    fieldValue = DataUtil.concatAll(fieldValue, format);
                }
                Constructor<T> constructor = tClass.getDeclaredConstructor(fieldClass);
                t = constructor.newInstance(fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static List<Field> getObjectField(Class<? extends BaseRowModel> cls) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = cls;
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;

    }
}
