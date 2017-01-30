package org.liuwei.test.common;

/**
 * Created by Administrator on 2017/1/30.
 */
public class Concurrent {
    private static class FiledHolder{
        public static final String filed = initField();

        private static String initField() {
            System.out.println("in init field");
            return "field";
        }
    }
    private static String cFiled = FiledHolder.filed;
    public static String getcFiled(){
        return cFiled;
    }
}
