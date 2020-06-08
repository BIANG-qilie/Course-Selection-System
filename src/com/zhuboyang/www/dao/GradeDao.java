package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.Grade;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface GradeDao {
    /**
     * 通过班级id查询年级
     * @param gradeId 班级id
     * @return 查询得到的年级
     */
    public Grade queryByGradeId(int gradeId);

    /**
     * 通过学院id查询年级
     * @param facultyId 学院id
     * @return 查询到的年级
     */
    public ArrayList queryByFacultyId(int facultyId);

    /**
     * 通过grade id删除grade
     *
     * @param gradeId grade id
     * @return 是否删除
     */
    public boolean deleteGradeByGradeId(int gradeId);

    /**
     * 用年级名和学院id创建一个新的年级
     *
     * @param gradeName 年级名
     * @param facultyId 学院id
     * @return 是否成功创建
     */
    public boolean createGradeByGradeNameAndFacultyId(String gradeName, int facultyId);
}
