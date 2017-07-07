package com.wxl.library.sqlite.modal;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;

class SelectModal {
    public String select(Class<?> cls, Object key) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ");
        builder.append(Util.getTableName(cls));
        builder.append(" where ");
        builder.append(getPrimaryValue(cls, key));
        builder.append(";");
        return builder.toString();
    }

    public String select(Class<?> cls) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ");
        builder.append(Util.getTableName(cls));
        builder.append(";");
        return builder.toString();
    }

    private String getPrimaryValue(Class<?> cls, Object key) throws Exception {
        Field[] fields = Util.getFields(cls);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Column column = field.getAnnotation(Column.class);
            if (Util.isEmpty(column)) {
                continue;
            } else if (column.key()) {
                if (field.getType() != int.class) {
                    throw new Exception("Invalid Primary Key!");
                }

                StringBuilder builder = new StringBuilder();
                builder.append(column.name());
                builder.append("=");
                builder.append(key.toString());
                return builder.toString();
            }
        }

        throw new Exception("No Primary Key!");
    }
}