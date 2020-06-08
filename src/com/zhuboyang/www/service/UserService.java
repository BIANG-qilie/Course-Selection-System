package com.zhuboyang.www.service;

import com.zhuboyang.www.po.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author BIANG
 */
public interface UserService {
    /**
     * 注册
     * @param user
     */
    public void registerUser(User user);
    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user);
    /**
     * 查重名
     * @param userName
     * @return
     */
    public boolean isExist(String userName);
    /**
     *选课
     * @param user 给这个用户选课
     * @param subjectId 课程id
     * @param n 课程栏id
     * @return 选完课的用户
     */
    public User chooseSubject(User user,int subjectId,int n);

    /**
     * 取消选课
     * @param user 取消选课的用户
     * @param n 取消哪个栏的的课程
     * @return
     */
    public User cancelSubjectBySubjectIdn(User user, int n);

    /**
     * 通过level查询含有该等级的用户
     *
     * @param level 用来查询的level
     * @return 该等级的User
     */
    public ArrayList getUserByLevel(int level);

    /**
     * 通过User id查询含有该等级的用户
     * @param userId 用户id
     * @return 用户
     */
    public User getUserByUserId(int userId);

    /**
     * 通过用户id和level修改该用户level
     * @param userId 用户id
     * @param level 等级
     * @return 修改后的User
     */
    public User updateUserLevelByUserId(int userId, int level);

    /**
     * 通过用户获取归属路径
     * @param user 用户
     * @return 归属路径
     */
    public ArrayList getBelongByUser(User user);

    /**
     * 通过用户id更新班级id
     * @param classId 班级id
     * @param userId 用户id
     * @return 是否更新成功
     */
    public boolean updateClassIdByUserId(int classId, int userId);

    /**
     * 通过班级号获取班级下的用户
     * @param classId  课程id
     * @return 获取该班级下的所有用户
     */
    public ArrayList getUserByClassId(int classId);

    /**
     * 注销
     * @param user 需要注销的用户
     * @return 是否注销成功
     */
    public boolean logout(User user);
}
