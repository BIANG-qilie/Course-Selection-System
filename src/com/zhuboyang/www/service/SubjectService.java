package com.zhuboyang.www.service;

import com.zhuboyang.www.po.Subject;
import com.zhuboyang.www.po.User;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface SubjectService {
    /**
     * 通过一个User直接找到它能选的课程
     * @param user 用来找课程的user
     * @return 返回能选的课程
     */
    public ArrayList getSubjectByUser(User user);

    /**
     * 通过一个课程id获取课程
     * @param subjectId 要获取的课程的id
     * @return 获取到的课程
     */
    public Subject getSubjectBySubjectId(int subjectId);

    /**
     * 通过一个User直接找到它管理的课程
     * @param manager 用来找课程的user
     * @return 返回能管理的课程
     */
    public ArrayList getSubjectByManager(User manager);

    /**
     * 查询该课程是否有人选过
     * @param subject 课程
     * @return 是则返回true
     */
    public boolean isSelected(Subject subject);

    /**
     * 根据课程获取归属路径
     * @param subject 课程
     * @return 归属路径
     */
    public ArrayList getBelongBySubject(Subject subject);

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
     * @return 更新后的课程
     */
    public Subject updateSubjectBySubjectData(int subjectId,String subjectName,
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
    public boolean createSubjectBySubjectData(String subjectName,
                                       String startTime,String stopTime,
                                       String selectStart,String selectStop,int dayOfWeek,
                                       String belong,int belongId);

    /**
     *通过subject id删除subject
     * @param subjectId 要被删除的subject的id
     * @return 是否被删除
     */
    public boolean deleteSubjectBySubjectId(int subjectId);


    /**
     * 通过一个归属直接找到归属下的全部课程
     * @param belong 归属
     * @param belongId 归属id
     * @return 返回课程
     */
    public ArrayList getSubjectByBelongId(String belong,int belongId);

}
