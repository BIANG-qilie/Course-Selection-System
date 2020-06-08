package com.zhuboyang.www.dao.impl;

import com.zhuboyang.www.dao.BaseDao;
import com.zhuboyang.www.dao.ResultGet;
import com.zhuboyang.www.dao.SubjectDao;
import com.zhuboyang.www.po.Subject;
import com.zhuboyang.www.po.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class SubjectDaoImpl extends BaseDao implements SubjectDao {
    public SubjectDaoImpl(){tableName=" subject ";}
    @Override
    protected Subject getInstance(ResultSet rs) throws SQLException {
        Subject subject=new Subject(rs.getInt("id"), rs.getString("subject_name"),
                rs.getTime("start_time"), rs.getTime("stop_time"),
                rs.getTime("select_start_time"), rs.getTime("select_stop_time"),
                rs.getDate("select_start_time"), rs.getDate("select_stop_time"),
                rs.getInt("day_of_week"), rs.getString("belong"), rs.getInt("belong_id"));
        return subject;
    }



    /**
     * 用于通过归属以及归属id查询课程的方法
     *
     * @param belong   归属 比如class grade faculty
     * @param belongId 归属id 比如class_id grade_id faculty_id
     * @return 找到的课程
     */
    @Override
    public ArrayList queryByBelongId(String belong, int belongId) {
        return queryArrayList(" WHERE belong=? AND belong_id=?",belong,belongId);
    }

    /**
     * 通过subject_id获取相关课程信息
     *
     * @param subjectId 用于查询的课程id
     * @return 找到的课程
     */
    @Override
    public Subject queryBySubjectId(int subjectId) {
        return (Subject) queryOneSelf(" WHERE id=?",subjectId);
    }

    /**
     * 获取全部课程
     *
     * @return 全部课程
     */
    @Override
    public ArrayList queryAllSubject() {
        return queryArrayList("");
    }

    /**
     * 更新整个subject id为subjectId的课程
     *
     * @param subjectId   用来确定哪个课程的subject id
     * @param subjectName 修改后的课程名
     * @param startTime 修改后的上课时间
     * @param stopTime 修改后的下课时间
     * @param selectStart 修改后的开始选课时间
     * @param selectStop 修改后的结束选课时间
     * @param dayOfWeek 修改后的上课日
     * @param belong 修改后的课程归属
     * @param belongId 修改后的课程归属id
     * @return 是否更新成功
     */
    @Override
    public boolean updateSubjectBySubjectId(int subjectId, String subjectName, String startTime, String stopTime, String selectStart, String selectStop, int dayOfWeek, String belong, int belongId) {
        String sql="UPDATE subject " +
                "SET `subject_name`=?, " +
                "`start_time`=?, " +
                "`stop_time`=?, " +
                "`select_start_time`=?, " +
                "`select_stop_time`=?, " +
                "`day_of_week`=?, " +
                "`belong`=?, " +
                "`belong_id`=? " +
                "WHERE  `id`=?";
        return update(sql,subjectName,startTime,stopTime,selectStart,selectStop,dayOfWeek,belong,belongId,subjectId);
    }

    /**
     * 创建一个新的课程
     *
     * @param subjectName 新的课程的课程名
     * @param startTime   新的课程的上课时间
     * @param stopTime    新的课程的下课时间
     * @param selectStart 新的课程的开始选课时间
     * @param selectStop  新的课程的结束选课时间
     * @param dayOfWeek   新的课程的上课日
     * @param belong      新的课程的归属
     * @param belongId    新的课程的归属id
     * @return 是否成功创建
     */
    @Override
    public boolean createSubjectBySubject(String subjectName, String startTime, String stopTime, String selectStart, String selectStop, int dayOfWeek, String belong, int belongId) {
        String sql="insert into subject values (default,?,?,?,?,?,?,?,?)";
        return update(sql,subjectName,startTime,stopTime,selectStart,selectStop,dayOfWeek,belong,belongId);
    }

    /**
     * 通过subject id删除subject
     *
     * @param subjectId 要被删除的subject的id
     * @return 是否被删除
     */
    @Override
    public boolean deleteSubjectBySubjectId(int subjectId) {
        String sql="DELETE FROM subject WHERE id=?";
        return update(sql,subjectId);
    }


}
