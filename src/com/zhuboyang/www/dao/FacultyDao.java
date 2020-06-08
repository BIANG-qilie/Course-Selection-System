package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.Faculty;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface FacultyDao {
    /**
     * 通过管理者id查询旗下的所有学院
     * @param managerUserId 管理者id
     * @return 找到的学院
     */
    ArrayList queryByManagerUserId(int managerUserId);

    /**
     * 通过faculty id 获取faculty
     * @param facultyId 用来查询的faculty id
     * @return 查询得到的faculty
     */
    Faculty queryByFacultyId(int facultyId);

    /**
     * 查询所有的学院
     * @return 所有的学院
     */
    ArrayList queryAllFaculty();

    /**
     * 更新管理员
     *
     * @param facultyId     更新管理员的学院
     * @param managerUserId 更新后管理员的id
     * @return 是否更新成功
     */
    public boolean updateManagerUserIdByFacultyIdAndManagerUserId(int facultyId, int managerUserId);

    /**
     * 通过faculty id删除faculty
     *
     * @param facultyId faculty id
     * @return 是否删除
     */
    public boolean deleteFacultyByFacultyId(int facultyId);

    /**
     * 用学院名和管理员用户id创建一个新的学院
     *
     * @param facultyName  学院名
     * @param mangerUserId 管理员用户id
     * @return 是否成功创建
     */
    public boolean createFacultyByFacultyNameAndManagerUserId(String facultyName, int mangerUserId);
}
