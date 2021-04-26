package com.jason.web.test;

/**
 * @author zhangjianhua
 * @date 2021/3/11 16:51
 */
public class TryCacheTest {

    public static void main(String[] args) {
        //int
        System.err.println("最终输出，int = " + get());
        // Integer
        Integer integer = new Integer(0);
        System.err.println("第一次输出，int = " + integer);
        //integer = printInteger(integer);
        printInteger(integer);
        System.err.println("最终输出，int = " + integer);
        // String
        String str = "a";
        System.err.println("第一次输出，str = " + str);
        //str = printString(str);
        printString(str);
        System.err.println("最终输出，str = " + str);
        // User
        User user = new User(11, "小王");
        System.err.println("第一次输出，" + user.toString());
        //user = printUser(user);
        printUser(user);
        System.err.println("最终输出，" + user.toString());
    }

    public static int get(){
        try {
            return 1;
        }finally {
            return 2;
        }
    }

    public static int printInteger(Integer i){
        try {
            ++i;
            System.err.println("第一次修改，int = " + i);
            return i;
        }finally {
            ++i;
            System.err.println("第二次修改，int = " + i);
        }
    }

    public static String printString(String str){
        try {
            str += "b" ;
            System.err.println("第一次修改，str = " + str);
            return str;
        }finally {
            str += "c" ;
            System.err.println("第二次修改，str = " + str);
            return str;
        }
    }

    public static User printUser(User user){
        try {
            user.setAge(12);
            user.setName("小明");
            //User user1 = new User(12, "小明");
            System.err.println("第一次修改，" + user.toString());
            return user;
        }finally {
            user.setAge(13);
            user.setName("小华");
            System.err.println("第二次修改，" + user.toString());
            return user;
        }
    }


}
