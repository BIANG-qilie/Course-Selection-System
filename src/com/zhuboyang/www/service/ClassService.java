package com.zhuboyang.www.service;

import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface ClassService {
    /**
     * 通过user的class_id查询class
     * @param user 用户
     * @return class
     */
    public Class getClassByUser(User user);

    /**
     * 通过grade获取旗下的所有class
     * @param grade 用来查询的年级
     * @return 旗下的class
     */
    public ArrayList getClassByGrade(Grade grade);

    /**
     * 通过class_id查询class
     * @param classId 用户
     * @return class
     */
    public Class getClassByClassId(int classId);

    /**
     * 根据班级id获取归属路径
     * @param classId 班级id
     * @return 归属路径
     */
    public String getBelongByClassId(int classId);

    /**
     * 通过class id删除class
     * @param classId grade id
     * @return
     */
    public boolean deleteClassByClassId(int classId);

    /**
     * 用班级名和年级id创建一个新的年级
     * @param className 班级名
     * @param gradeId 年级id
     * @return
     */
    public boolean createClassByClassNameAndGradeId(String className, int gradeId);
}
