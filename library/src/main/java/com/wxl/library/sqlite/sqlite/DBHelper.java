package com.wxl.library.sqlite.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wxl.library.sqlite.annotation.Column;
import com.wxl.library.sqlite.code.Code;
import com.wxl.library.sqlite.modal.Modal;
import com.wxl.library.sqlite.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "wsqlite";

    public DBHelper(Context context, Class<?> cls) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean create(Class<?> cls) {
        SQLiteDatabase db = super.getWritableDatabase();

        try {
            String create = Modal.create.execute(cls);
            db.execSQL(create);
            return true;
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "create exception:" + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean drop(Class<?> cls) {
        SQLiteDatabase db = super.getWritableDatabase();

        try {
            String create = Modal.drop.execute(cls);
            db.execSQL(create);
            return true;
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "drop exception:" + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean insert(Object bean) {
        SQLiteDatabase db = super.getWritableDatabase();

        try {
            create(bean.getClass());

            String sql = Modal.insert.execute(bean);
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "insert exception:" + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean delete(Object bean) {
        SQLiteDatabase db = super.getWritableDatabase();

        try {
            create(bean.getClass());

            String sql = Modal.delete.execute(bean);
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "delete exception:" + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean update(Object bean) {
        SQLiteDatabase db = super.getWritableDatabase();

        try {
            create(bean.getClass());

            String sql = Modal.update.execute(bean);
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "update exception:" + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public <T> T select(Class<T> cls, Object key) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;

        try {
            create(cls);

            String sql = Modal.select.execute(cls, key);
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            return getBean(cls, cursor);
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "select exception:" + e.getMessage());
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }
    }

    public <T> List<T> select(Class<T> cls) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;

        try {
            create(cls);

            String sql = Modal.select.execute(cls);
            cursor = db.rawQuery(sql, null);
            return getBeanList(cls, cursor);
        } catch (Exception e) {
            Log.d("wsqlite==>DBHelper", "select exception:" + e.getMessage());
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }
    }

    private <T> List<T> getBeanList(Class<T> cls, Cursor cursor) throws Exception {
        List<T> beanList = new ArrayList<T>();
        while (cursor.moveToNext()) {
            beanList.add(getBean(cls, cursor));
        }
        return beanList;
    }

    private <T> T getBean(Class<T> cls, Cursor cursor) throws Exception {
        T obj = cls.newInstance();
        Field[] fields = Util.getFields(cls);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (Util.isEmpty(column)) {
                continue;
            } else {
                int index = cursor.getColumnIndex(column.name());
                String value = cursor.getString(index);

                if (!column.key() && column.code()) {
                    value = Code.des.decrypt(value, Modal.common.key);
                }

                Method tm = cls.getMethod(Util.buildSetters(field), field.getType());
                tm.invoke(obj, Util.getValue(field, value));
            }
        }
        return obj;
    }
}