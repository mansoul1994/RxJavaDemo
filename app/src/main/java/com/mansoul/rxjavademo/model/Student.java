package com.mansoul.rxjavademo.model;

import java.util.List;

/**
 * Created by Mansoul on 16/5/29.
 */
public class Student {

    public String name;
    public List<Course> course;

    public static class Course {
        public String math = "math";
        public String english = "english";

        @Override
        public String toString() {
            return "Course{" +
                    "math='" + math + '\'' +
                    ", english='" + english + '\'' +
                    '}';
        }
    }
}
