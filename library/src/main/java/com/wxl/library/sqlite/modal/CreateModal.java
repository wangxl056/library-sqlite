package com.wxl.library.sqlite.modal;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;

class CreateModal {
    public String create(Class<?> cls) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists ");
        builder.append(Util.getTableName(cls));
        builder.append("(");
        ArrayList<String> params = getParams(cls);
        for (int i = 0; i < params.size(); i++) {
            builder.append(params.get(i));
            if (i < params.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }

    private ArrayList<String> getParams(Class<?> cls) throws Exception {
        ArrayList<String> params = new ArrayList<String>();
        Field[] fields = Util.getFields(cls);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Column column = field.getAnnotation(Column.class);
            if (Util.isEmpty(column)) {
                continue;
            } else {
                params.add(getParam(column, field));
            }
        }
        return params;
    }

    private String getParam(Column column, Field field) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(column.name());
        if (column.key()) {
            if (field.getType() != int.class) {
                throw new Exception("Invalid Primary Key!");
            }

            builder.append(" INTEGER");
            builder.append(" PRIMARY KEY");
            builder.append(" AUTOINCREMENT");
        } else {
            builder.append(" TEXT");
        }
        if (!column.empty()) {
            builder.append(" NOT NULL");
        }
        return builder.toString();
    }
}