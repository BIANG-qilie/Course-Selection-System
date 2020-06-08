package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.Class;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface ClassDao {
    /**
     * 通过id查询class
     * @param classId 用来查询的id
     * @return 查询的class
     */
    public Class queryByClassId(int classId);

    /**
     * 通过年级id查询class
     * @param gradeId 年级id
     * @return 查询得到的class
     */
    public ArrayList queryByGradeId(int gradeId);

    /**
     * 通过class id删除class
     *
     * @param classId grade id
     * @return 是否删除
     */
    public boolean deleteClassByClassId(int classId);

    /**
     * 用班级名和年级id创建一个新的年级
     *
     * @param className 班级名
     * @param gradeId   年级id
     * @return 是否成功创建
     */
    public boolean createClassByClassNameAndGradeId(String className, int gradeId);
}
