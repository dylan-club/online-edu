package com.nicklaus.commonutils;


public interface UtilConstants {

    /**
     * 统一返回结果规范常量
     */
    public static class ResultCode{
        public static Integer SUCCESS_CODE = 20000;

        public static Integer ERROR_CODE = 20001;

        public static String SUCCESS_MSG = "成功";

        public static String ERROR_MSG = "失败";
    }

    /**
     * 课程常量
     */
    public static class Course{
        public static String COURSE_PUBLISHED = "Normal";
    }
}
