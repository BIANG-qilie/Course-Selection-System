package com.zhuboyang.www.dao.impl;

import com.zhuboyang.www.dao.BaseDao;
import com.zhuboyang.www.dao.GradeDao;
import com.zhuboyang.www.dao.ResultGet;
import com.zhuboyang.www.po.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class GradeDaoImpl extends BaseDao implements GradeDao {
    public GradeDaoImpl(){tableName=" grade ";}

    @Override
    protected Object getInstance(ResultSet rs) throws SQLException {
        Grade grade=new Grade(rs.getInt("id"),rs.getString("grade_name"),rs.getInt("faculty_id"));
        return grade;
    }

    /**
     * 通过年级id查询年级
     *
     * @param gradeId 年级id
     * @return 查询得到的年级
     */
    @Override
    public Grade queryByGradeId(int gradeId) {
        return (Grade) queryOneSelf("WHERE id=?",gradeId);
    }

    /**
     * 通过学院id查询年级
     *
     * @param facultyId 学院id
     * @return 查询到的年级
     */
    @Override
    public ArrayList queryByFacultyId(int facultyId) {
        return queryArrayList(" WHERE faculty_id=?",facultyId);
    }

    /**
     * 通过grade id删除grade
     *
     * @param gradeId grade id
     * @return 是否删除
     */
    @Override
    public boolean deleteGradeByGradeId(int gradeId) {
        String sql="DELETE FROM grade WHERE id=?";
        return update(sql,gradeId);
    }

    /**
     * 用年级名和学院id创建一个新的年级
     *
     * @param gradeName 年级名
     * @param facultyId 学院id
     * @return 是否成功创建
     */
    @Override
    public boolean createGradeByGradeNameAndFacultyId(String gradeName, int facultyId) {
        String sql="INSERT INTO grade  VALUES(default,?,?)";
        return update(sql,gradeName,facultyId);
    }


}
