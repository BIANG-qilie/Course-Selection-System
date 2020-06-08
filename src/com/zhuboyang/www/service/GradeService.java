package com.zhuboyang.www.service;

import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;

import java.util.ArrayList;

public interface GradeService {
    /**
     * 通过班级获得年级
     * @param tempClass 班级
     * @return 班级所在的年级
     */
    public Grade getGradeByClass(Class tempClass);

    /**
     * 通过学院获得年级
     * @param faculty 学院
     * @return 找到的年纪
     */
    public ArrayList getGradeByFaculty(Faculty faculty);

    /**
     * 通过年级id获得年级
     * @param gradeId 年级id
     * @return 年级
     */
    public Grade getGradeByGradeId(int gradeId);

    /**
     * 通过grade id删除grade
     * @param gradeId grade id
     * @return 是否删除
     */
    public boolean deleteGradeByGradeId(int gradeId);

    /**
     * 用年级名和学院id创建一个新的年级
     * @param gradeName 年级名
     * @param facultyId 学院id
     * @return 是否成功创建
     */
    public boolean createGradeByGradeNameAndFacultyId(String gradeName, int facultyId);
}
