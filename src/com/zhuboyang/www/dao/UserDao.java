package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.User;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public interface UserDao {
    /**
     * 查重名
     * @param userName
     * @return
     */
    public User queryByUserName(String userName);

    /**
     * 验证账号密码
     * @param userName
     * @param password
     * @return
     */
    public User queryByUserNameAndPassword(String userName,String password);

    /**
     * 注册
     * @param userName 注册用的用户名
     * @param password 注册用的密码
     * @param classId 课程id
     * @return 是否创建成功
     */
    public boolean createByUserNameAndPassword(String userName, String password,int classId);

    /**
     * 通过用户id以及课程id选课
     * @param userId 给这个id得用户选课
     * @param subjectId 选这门课
     * @param n 放在哪个课程栏中
     * @return 是否选课成功
     */
    public boolean updateSubjectByUserIdAndSubjectId(int userId,int subjectId,int n);

    /**
     * 通过subject id查询含有该id的用户
     * @param subjectId 用来查询的subject id
     * @return 选了这节课的用户
     */
    public ArrayList queryBySubjectId(int subjectId);

    /**
     * 通过level查询含有该等级的用户
     * @param level 用来查询的level
     * @return 该等级的User
     */
    public ArrayList queryByLevel(int level);

    /**
     * 通过user id查询该id的用户
     * @param userId 用来查询的user id
     * @return 用户
     */
    public User queryByUserId(int userId);

    /**
     * 通过用户id和level修改该用户level
     *
     * @param userId 用户id
     * @param level  等级
     * @return 是否修改成功
     */
    public boolean updateUserLevelByUserIdAndLevel(int userId, int level);

    /**
     * 通过用户id更新班级id
     *
     * @param classId 班级id
     * @param userId  用户id
     * @return 是否更新成功
     */
    public boolean updateClassIdByUserIdAndClassId(int classId, int userId);

    /**
     * 通过班级号获取班级下的用户
     *
     * @param classId 课程id
     * @return 获取该班级下的所有用户
     */
    public ArrayList queryByClassId(int classId);

    /**
     * 通过用户id和登录状态更新登录状态
     * @param userId 用户id
     * @param loginStatus 登录状态
     * @return 是否更新成功
     */
    public boolean updateLoginStatusByUserIdAndLoginStatus(int userId,int loginStatus);
}
