package com.zhuboyang.www.service;

import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface FacultyService {
    /**
     * 获取管理者旗下的全部管理的课程
     * @param manager 管理者的User
     * @return 全部管理的课程
     */
    public ArrayList getFacultyByManager(User manager);

    /**
     * 通过年级获取所在的学院
     * @param grade 用来查找的年级
     * @return 所在的学院
     */
    public Faculty getFacultyByGrade(Grade grade);

    /**
     * 通过学院id获取学院
     * @param facultyId 学院id
     * @return 学院
     */
    public Faculty getFacultyByFacultyId(int facultyId);

    /**
     * 获取全部学院
     * @return 全部学院
     */
    public ArrayList getAllFaculty();

    /**
     * 更新管理员
     * @param facultyId 更新管理员的学院
     * @param managerUserId 更新后管理员的id
     * @return 更新后的学院
     */
    public Faculty updateManagerUserId(int facultyId,int managerUserId);

    /**
     * 通过faculty id删除faculty
     * @param facultyId faculty id
     * @return 是否删除
     */
    public boolean deleteFacultyByFacultyId(int facultyId);

    /**
     * 用学院名和管理员用户id创建一个新的学院
     * @param facultyName 学院名
     * @param mangerUserId 管理员用户id
     * @return 是否成功创建
     */
    public boolean createFacultyByFacultyNameAndManagerUserId(String facultyName, int mangerUserId);
}
