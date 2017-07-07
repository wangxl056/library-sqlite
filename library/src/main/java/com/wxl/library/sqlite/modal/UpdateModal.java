package com.wxl.library.sqlite.modal;

import android.annotation.SuppressLint;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.code.Code;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

class UpdateModal {
    public String update(Object bean) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(Util.getTableName(bean.getClass()));
        builder.append(" set ");
        ArrayList<String> params = getParams(bean);
        for (int i = 0; i < params.size(); i++) {
            builder.append(params.get(i));
            if (i < params.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(" where ");
        builder.append(getPrimary(bean));
        builder.append(";");
        return builder.toString();
    }

    private ArrayList<String> getParams(Object bean) throws Exception {
        ArrayList<String> params = new ArrayList<String>();
        Field[] fields = Util.getFields(bean.getClass());
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if (Util.isEmpty(column)) {
                continue;
            } else {
                params.add(getValue(bean, column, fields[i]));
            }
        }
        return params;
    }

    private String getPrimary(Object bean) throws Exception {
        Field[] fields = Util.getFields(bean.getClass());
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if (column.key()) {
                return getValue(bean, column, fields[i]);
            }
        }

        throw new Exception("No Primary Key!");
    }

    @SuppressLint("DefaultLocale")
    private String getValue(Object bean, Column column, Field field) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(column.name());
        builder.append("=");

        Method method = bean.getClass().getMethod(Util.buildGetters(field), new Class<?>[0]);
        Object obj = method.invoke(bean, new Object[0]);
        String value = obj.toString();

        if (column.key()) {
            if (field.getType() != int.class) {
                throw new Exception("Invalid Primary Key!");
            }

            builder.append(value);
        } else {
            if (column.code()) {
                value = Code.des.encrypt(value, Modal.common.key);
            }

            builder.append("\"");
            builder.append(value);
            builder.append("\"");
        }

        return builder.toString();
    }
}