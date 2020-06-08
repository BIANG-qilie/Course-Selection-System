package com.zhuboyang.www.po;

/**
 * @author BIANG
 */
public class Faculty {
    private int id;
    private String facultyName;
    private int managerUserId;

    public Faculty(int id, String facultyName, int managerUserId) {
        this.id = id;
        this.facultyName = facultyName;
        this.managerUserId = managerUserId;
    }

    public int getId() {
        return id;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public int getManagerUserId() {
        return managerUserId;
    }
}
