package com.wxl.library.sqlite;

import android.content.Context;

import com.wxl.library.sqlite.sqlite.DBHelper;

import java.util.List;

public class WSQLite {
    private Context mContext = null;

    private WSQLite(Context context) {
        mContext = context;
    }

    public static WSQLite with(Context context) {
        return new WSQLite(context);
    }

    public boolean insert(Object bean) {
        DBHelper dbHelper = new DBHelper(mContext, bean.getClass());

        boolean result = dbHelper.insert(bean);

        dbHelper.close();

        return result;
    }

    public boolean delete(Object bean) {
        DBHelper dbHelper = new DBHelper(mContext, bean.getClass());

        boolean result = dbHelper.delete(bean);

        dbHelper.close();

        return result;
    }

    public boolean update(Object bean) {
        DBHelper dbHelper = new DBHelper(mContext, bean.getClass());

        boolean result = dbHelper.update(bean);

        dbHelper.close();

        return result;
    }

    public <T> List<T> select(Class<T> cls) {
        DBHelper dbHelper = new DBHelper(mContext, cls);

        List<T> result = dbHelper.select(cls);

        dbHelper.close();

        return result;
    }

    public <T> T select(Class<T> cls, Object key) {
        DBHelper dbHelper = new DBHelper(mContext, cls);

        T result = dbHelper.select(cls, key);

        dbHelper.close();

        return result;
    }
}