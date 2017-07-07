package com.wxl.library.sqlite.utils;

import android.annotation.SuppressLint;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.annotation.Table;

import java.lang.reflect.Field;

@SuppressLint("DefaultLocale")
public class Util {
    public static boolean isEmpty(String str) {
        return str == null || str == "";
    }

    public static boolean isEmpty(Column column) {
        return column == null || column.name() == null || column.name() == "";
    }

    public static boolean isEmpty(Table table) {
        return table == null || table.name() == null || table.name() == "";
    }

    public static String buildGetters(Field field) {
        String name = field.getName();
        StringBuilder builder = new StringBuilder();
        if (field.getType() == boolean.class) {
            builder.append("is");
        } else {
            builder.append("get");
        }
        String str = name.substring(0, 1);
        builder.append(name.replace(str, str.toUpperCase()));
        return builder.toString();
    }

    public static String buildSetters(Field field) {
        String name = field.getName();
        StringBuilder builder = new StringBuilder();
        builder.append("set");
        String str = name.substring(0, 1);
        builder.append(name.replace(str, str.toUpperCase()));
        return builder.toString();
    }

    public static Object getValue(Field field, String value) throws Exception {
        Class<?> cls = field.getType();
        if (cls == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (cls == short.class) {
            return Short.parseShort(value);
        } else if (cls == int.class) {
            return Integer.parseInt(value);
        } else if (cls == long.class) {
            return Long.parseLong(value);
        } else if (cls == float.class) {
            return Float.parseFloat(value);
        } else if (cls == double.class) {
            return Double.parseDouble(value);
        } else if (cls == String.class) {
            return String.valueOf(value);
        } else {
            throw new Exception("Unsupport Data Type!");
        }
    }

    public static Field[] getFields(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < i; j++) {
                String name1 = fields[i].getName();
                String name2 = fields[j].getName();

                if (name1.compareTo(name2) < 0) {
                    Field temp = fields[i];
                    fields[i] = fields[j];
                    fields[j] = temp;
                }
            }
        }
        return fields;
    }

    public static String getTableName(Class<?> cls) throws Exception {
        Table table = cls.getAnnotation(Table.class);
        if (Util.isEmpty(table)) {
            throw new Exception("Invalid Table Annotation!");
        } else {
            return table.name();
        }
    }
}