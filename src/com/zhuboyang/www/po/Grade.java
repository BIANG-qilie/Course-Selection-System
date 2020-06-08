package com.zhuboyang.www.po;

/**
 * @author BIANG
 */
public class Grade {
    private int id;
    private String gradeName;
    private int facultyId;

    public Grade(int id, String gradeName, int facultyId) {
        this.id = id;
        this.gradeName = gradeName;
        this.facultyId = facultyId;
    }

    public int getId() {
        return id;
    }
    public String getGradeName() {
        return gradeName;
    }
    public int getFacultyId() {
        return facultyId;
    }

}
