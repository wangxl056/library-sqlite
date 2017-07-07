package com.wxl.library.sqlite.modal;

public final class Modal {
    public static final class common {
        public static final String key = "12345678";
    }

    public static final class create {
        public static String execute(Class<?> cls) throws Exception {
            return new CreateModal().create(cls);
        }
    }

    public static final class drop {
        public static String execute(Class<?> cls) throws Exception {
            return new DropModal().drop(cls);
        }
    }

    public static final class insert {
        public static String execute(Object bean) throws Exception {
            return new InsertModal().insert(bean);
        }
    }

    public static final class delete {
        public static String execute(Object bean) throws Exception {
            return new DeleteModal().delete(bean);
        }
    }

    public static final class update {
        public static String execute(Object bean) throws Exception {
            return new UpdateModal().update(bean);
        }
    }

    public static final class select {
        public static String execute(Class<?> cls, Object key) throws Exception {
            return new SelectModal().select(cls, key);
        }

        public static String execute(Class<?> cls) throws Exception {
            return new SelectModal().select(cls);
        }
    }
}