package com.zhuboyang.www.dao.impl;

import com.zhuboyang.www.dao.BaseDao;
import com.zhuboyang.www.dao.ResultGet;
import com.zhuboyang.www.dao.UserDao;
import com.zhuboyang.www.po.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    public UserDaoImpl(){
        tableName = " user ";
    }

    @Override
    protected User getInstance(ResultSet rs) throws SQLException {
        int[] subjectId=new int[User.SUBJECT_SIZE];
        for(int i=0;i<User.SUBJECT_SIZE;i++){
            subjectId[i]=rs.getInt("subject_id"+(i+1));
        }
        User user=new User(rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getInt("level"),rs.getInt("class_id"),
                subjectId);
        return user;
    }



    /**
     * 查询是否有该用户名的账号
     *
     * @param userName 用户名
     * @return 该用户名的账号或者null
     */
    @Override
    public User queryByUserName(String userName) {
        return (User) queryOneSelf(" WHERE user_name=?",userName);
    }

    /**
     * 验证账号密码
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User queryByUserNameAndPassword(String userName, String password) {
        return (User) queryOneSelf(" WHERE user_name=? AND password=?",userName,password);
    }

    /**
     * 注册
     * @param userName 注册用的用户名
     * @param password 注册用的密码
     * @param classId 课程id
     * @return 是否创建成功
     */
    @Override
    public boolean createByUserNameAndPassword(String userName, String password,int classId) {
        String sql="INSERT INTO user  VALUES(default,?,?,1,?,0,0,0)";
        return update(sql,userName,password,classId);
    }

    /**
     * 通过用户id以及课程id选课
     *
     * @param userId    给这个id得用户选课
     * @param subjectId 选这门课
     * @param n 放在哪个课程栏中
     * @return 是否选课成功
     */
    @Override
    public boolean updateSubjectByUserIdAndSubjectId(int userId, int subjectId,int n) {
        String sql="UPDATE user SET subject_id?=? WHERE id=?";
        return update(sql,n,subjectId,userId);
    }

    /**
     * 通过subject id查询含有该id的用户
     *
     * @param subjectId 用来查询的subject id
     * @return 选了这节课的用户
     */
    @Override
    public ArrayList queryBySubjectId(int subjectId) {
        String extraSql=" WHERE";
        for(int i=0;i<User.SUBJECT_SIZE;i++){
            String temp;
            if(i==User.SUBJECT_SIZE-1){
                temp=(" subject_id"+(i+1)+"=?");
            }else{
                temp=" subject_id"+(i+1)+"=? OR";
            }
            extraSql +=temp;
        }
        return queryArrayList(extraSql,subjectId,subjectId,subjectId);
    }

    /**
     * 通过level查询含有该等级的用户
     *
     * @param level 用来查询的level
     * @return 该等级的User
     */
    @Override
    public ArrayList queryByLevel(int level) {
        return queryArrayList(" WHERE level=?",level);
    }

    /**
     * 通过user id查询该id的用户
     *
     * @param userId 用来查询的user id
     * @return 用户
     */
    @Override
    public User queryByUserId(int userId) {
        return (User) queryOneSelf(" WHERE id=?",userId);
    }

    /**
     * 通过用户id和level修改该用户level
     *
     * @param userId 用户id
     * @param level  等级
     * @return 是否修改成功
     */
    @Override
    public boolean updateUserLevelByUserIdAndLevel(int userId, int level) {
        String sql="UPDATE user SET level=? WHERE id=?";
        return update(sql,level,userId);
    }

    /**
     * 通过用户id更新班级id
     *
     * @param classId 班级id
     * @param userId  用户id
     * @return 是否更新成功
     */
    @Override
    public boolean updateClassIdByUserIdAndClassId(int classId, int userId) {
        String sql="UPDATE user SET class_id=? WHERE id=?";
        return update(sql,classId,userId);
    }

    /**
     * 通过班级号获取班级下的用户
     *
     * @param classId 课程id
     * @return 获取该班级下的所有用户
     */
    @Override
    public ArrayList queryByClassId(int classId) {
        return queryArrayList(" WHERE class_id=?",classId);
    }

    /**
     * 通过用户id和登录状态更新登录状态
     *
     * @param userId      用户id
     * @param loginStatus 登录状态
     * @return 是否更新成功
     */
    @Override
    public boolean updateLoginStatusByUserIdAndLoginStatus(int userId, int loginStatus) {
        String sql="UPDATE user SET login_status=? WHERE id=?";
        return update(sql,loginStatus,userId);
    }
}
