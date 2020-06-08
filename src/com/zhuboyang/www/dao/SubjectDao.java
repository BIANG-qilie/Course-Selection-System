package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.Subject;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface SubjectDao {
    /**
     * 用于通过归属以及归属id查询课程的方法
     * @param Belong 归属 比如class grade faculty
     * @param BelongId 归属id 比如class_id grade_id faculty_id
     * @return 找到的课程
     */
    public ArrayList queryByBelongId(String Belong,int BelongId);

    /**
     * 通过subject_id获取相关课程信息
     * @param subjectId 用于查询的课程id
     * @return 找到的课程
     */
    public Subject queryBySubjectId(int subjectId);

    /**
     * 获取全部课程
     * @return 全部课程
     */
    public ArrayList queryAllSubject();

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
    public boolean updateSubjectBySubjectId(int subjectId,String subjectName,
                                     String startTime,String stopTime,
                                     String selectStart,String selectStop,int dayOfWeek,
                                     String belong,int belongId);

    /**
     * 创建一个新的课程
     * @param subjectName 新的课程的课程名
     * @param startTime 新的课程的上课时间
     * @param stopTime 新的课程的下课时间
     * @param selectStart 新的课程的开始选课时间
     * @param selectStop 新的课程的结束选课时间
     * @param dayOfWeek 新的课程的上课日
     * @param belong 新的课程的归属
     * @param belongId 新的课程的归属id
     * @return 是否成功创建
     */
    public boolean createSubjectBySubject(String subjectName,
                                   String startTime,String stopTime,
                                   String selectStart,String selectStop,int dayOfWeek,
                                   String belong,int belongId);

    /**
     *通过subject id删除subject
     * @param subjectId 要被删除的subject的id
     * @return 是否被删除
     */
    public boolean deleteSubjectBySubjectId(int subjectId);


}
