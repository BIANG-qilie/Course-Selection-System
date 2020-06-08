package com.zhuboyang.www.dao.impl;

import com.zhuboyang.www.dao.BaseDao;
import com.zhuboyang.www.dao.FacultyDao;
import com.zhuboyang.www.dao.ResultGet;
import com.zhuboyang.www.po.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class FacultyDaoImpl extends BaseDao implements FacultyDao {
    public FacultyDaoImpl(){ tableName=" faculty ";}
    @Override
    protected Object getInstance(ResultSet rs) throws SQLException {
        Faculty faculty=new Faculty(rs.getInt("id"),rs.getNString("faculty_name"),rs.getInt("manager_user_id"));
        return faculty;
    }
    /**
     * 通过管理者id查询旗下的所有学院
     *
     * @param managerUserId 管理者id
     * @return 找到的学院
     */
    @Override
    public ArrayList queryByManagerUserId(int managerUserId) {
        return queryArrayList(" WHERE manager_user_id=?",managerUserId);
    }

    /**
     * 通过faculty id 获取faculty
     *
     * @param facultyId 用来查询的faculty id
     * @return 查询得到的faculty
     */
    @Override
    public Faculty queryByFacultyId(int facultyId) {
        return (Faculty) queryOneSelf(" where id=?",facultyId);
    }

    /**
     * 查询所有的学院
     *
     * @return 所有的学院
     */
    @Override
    public ArrayList queryAllFaculty() {
        return queryArrayList("");
    }

    /**
     * 更新管理员
     *
     * @param facultyId     更新管理员的学院
     * @param managerUserId 更新后管理员的id
     * @return 是否更新成功
     */
    @Override
    public boolean updateManagerUserIdByFacultyIdAndManagerUserId(int facultyId, int managerUserId) {
        String sql="UPDATE faculty SET manager_user_id =? WHERE id=?";
        return update(sql,managerUserId,facultyId);
    }

    /**
     * 通过faculty id删除faculty
     *
     * @param facultyId faculty id
     * @return 是否删除
     */
    @Override
    public boolean deleteFacultyByFacultyId(int facultyId) {
        String sql="DELETE FROM faculty WHERE id=?";
        return update(sql,facultyId);
    }

    /**
     * 用学院名和管理员用户id创建一个新的学院
     *
     * @param facultyName  学院名
     * @param mangerUserId 管理员用户id
     * @return 是否成功创建
     */
    @Override
    public boolean createFacultyByFacultyNameAndManagerUserId(String facultyName, int mangerUserId) {
        String sql="INSERT INTO faculty  VALUES(default,?,?)";
        return update(sql,facultyName,mangerUserId);
    }


}
