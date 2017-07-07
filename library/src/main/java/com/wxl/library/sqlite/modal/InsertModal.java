package com.wxl.library.sqlite.modal;

import android.annotation.SuppressLint;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.code.Code;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

class InsertModal {
    public String insert(Object bean) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ");
        builder.append(Util.getTableName(bean.getClass()));
        builder.append(" values(");
        ArrayList<String> params = getParams(bean, true);
        for (int i = 0; i < params.size(); i++) {
            builder.append(params.get(i));
            if (i < params.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }

    private ArrayList<String> getParams(Object bean, boolean insert) throws Exception {
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

    @SuppressLint("DefaultLocale")
    private String getValue(Object bean, Column column, Field field) throws Exception {
        if (column.key()) {
            if (field.getType() != int.class) {
                throw new Exception("Invalid Primary Key!");
            }

            return "null";
        }

        Method method = bean.getClass().getMethod(Util.buildGetters(field), new Class<?>[0]);
        Object obj = method.invoke(bean, new Object[0]);
        String value = obj.toString();

        if (column.code()) {
            value = Code.des.encrypt(value, Modal.common.key);
        }

        return "\"" + value + "\"";
    }
}