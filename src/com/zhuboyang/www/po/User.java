package com.zhuboyang.www.po;

/**
 * @author BIANG
 */
public class User {
    public User(int id, String username, String password, int level, int classId, int... subjectId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.classId = classId;
       for(int i=0;i<SUBJECT_SIZE;i++){
           this.subjectId[i]=subjectId[i];
       }
    }

    static public final int SUBJECT_SIZE=3;
    private int id;
    private String username;
    private String password;
    private int level;
    private int classId;
    private int[] subjectId=new int[SUBJECT_SIZE];


    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getLevel() {
        return level;
    }
    public int getClassId() {
        return classId;
    }
    public int getSubjectId(int n){
        return subjectId[n-1];
    }
}
