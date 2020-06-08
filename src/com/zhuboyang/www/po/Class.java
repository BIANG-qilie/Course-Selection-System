package com.zhuboyang.www.po;

/**
 * @author BIANG
 */
public class Class {
    private int id;
    private String className;
    private int gradeId;

    public Class(int id, String className, int gradeId) {
        this.id = id;
        this.className = className;
        this.gradeId = gradeId;
    }

    public int getId() {
        return id;
    }
    public String getClassName() {
        return className;
    }
    public int getGradeId() {
        return gradeId;
    }


}
