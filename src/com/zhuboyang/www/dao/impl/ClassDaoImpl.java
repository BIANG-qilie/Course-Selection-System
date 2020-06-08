package com.zhuboyang.www.dao.impl;

import com.zhuboyang.www.dao.BaseDao;
import com.zhuboyang.www.dao.ClassDao;
import com.zhuboyang.www.dao.ResultGet;
import com.zhuboyang.www.po.Class;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class ClassDaoImpl extends BaseDao implements ClassDao {
    public ClassDaoImpl(){tableName=" class ";}
    @Override
    protected Object getInstance(ResultSet rs) throws SQLException {
        Class classTemp= new Class(rs.getInt("id"),rs.getString("class_name"),rs.getInt("grade_id"));
        return classTemp;
    }
    /**
     * 通过id查询class
     *
     * @param classId 用来查询的id
     * @return 查询的class
     */
    @Override
    public Class queryByClassId(int classId) {
        return (Class) queryOneSelf(" WHERE id=?",classId);
    }

    /**
     * 通过年级id查询class
     *
     * @param gradeId 年级id
     * @return 查询得到的class
     */
    @Override
    public ArrayList queryByGradeId(int gradeId) {
        return queryArrayList(" WHERE grade_id=?",gradeId);
    }

    /**
     * 通过class id删除class
     *
     * @param classId grade id
     * @return 是否删除
     */
    @Override
    public boolean deleteClassByClassId(int classId) {
        String sql="DELETE FROM class WHERE id=?";
        return update(sql,classId);
    }

    /**
     * 用班级名和年级id创建一个新的年级
     *
     * @param className 班级名
     * @param gradeId   年级id
     * @return 是否成功创建
     */
    @Override
    public boolean createClassByClassNameAndGradeId(String className, int gradeId) {
        String sql="INSERT INTO class  VALUES(default,?,?)";
        return update(sql,className,gradeId);
    }


}
