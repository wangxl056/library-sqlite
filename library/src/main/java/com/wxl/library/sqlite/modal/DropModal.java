package com.wxl.library.sqlite.modal;

import com.wxl.library.sqlite.utils.Util;

class DropModal {
    public String drop(Class<?> cls) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("drop table if exists ");
        builder.append(Util.getTableName(cls));
        builder.append(";");
        return builder.toString();
    }
}