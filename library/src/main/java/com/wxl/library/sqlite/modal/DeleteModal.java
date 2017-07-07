package com.wxl.library.sqlite.modal;

import android.annotation.SuppressLint;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


class DeleteModal {
    public String delete(Object bean) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ");
        builder.append(Util.getTableName(bean.getClass()));
        builder.append(" where ");
        builder.append(getPrimary(bean));
        builder.append(";");
        return builder.toString();
    }

    private String getPrimary(Object bean) throws Exception {
        Field[] fields = Util.getFields(bean.getClass());
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Column column = field.getAnnotation(Column.class);
            if (Util.isEmpty(column)) {
                continue;
            } else if (column.key()) {
                return getPrimaryValue(bean, column, field);
            }
        }

        throw new Exception("No Primary Key!");
    }

    @SuppressLint("DefaultLocale")
    private String getPrimaryValue(Object bean, Column column, Field field) throws Exception {
        if (field.getType() != int.class) {
            throw new Exception("Invalid Primary Key!");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(column.name());
        builder.append("=");

        Method method = bean.getClass().getMethod(Util.buildGetters(field), new Class<?>[0]);
        Object obj = method.invoke(bean, new Object[0]);
        builder.append(obj.toString());

        return builder.toString();
    }
}