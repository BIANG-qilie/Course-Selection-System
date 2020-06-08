package com.zhuboyang.www.service.impl;

import com.zhuboyang.www.dao.UserDao;
import com.zhuboyang.www.dao.impl.UserDaoImpl;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.*;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    SubjectService subjectService=new SubjectServiceImpl();
    /**
     * 注册
     *
     * @param user
     */
    @Override
    public void registerUser(User user) {
        userDao.createByUserNameAndPassword(user.getUsername(),user.getPassword(),user.getClassId());
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        assert (user!=null):"登录时出错:用户为null";
        User loginUser=userDao.queryByUserNameAndPassword(user.getUsername(),user.getPassword());
        /*未启用的功能（登录状态变更）
        if(loginUser!=null) {
            userDao.updateLoginStatusByUserIdAndLoginStatus(loginUser.getId(), 1);
        }*/
        return loginUser;
    }

    /**
     * 查重名
     *
     * @param userName
     * @return
     */
    @Override
    public boolean isExist(String userName) {
        return (userDao.queryByUserName(userName)!=null);
    }

    /**
     * 选课
     *
     * @param user      给这个用户选课
     * @param subjectId 课程id
     * @param n         课程栏id
     * @return 选完课的用户
     */
    @Override
    public User chooseSubject(User user, int subjectId, int n) {
        assert user!=null:"选课时出错:用户为null";
        assert subjectService.getSubjectBySubjectId(subjectId)!=null :"选课时出错:找不到该课程 subject_id="+subjectId;
        userDao.updateSubjectByUserIdAndSubjectId(user.getId(),subjectId,n);
        return userDao.queryByUserName(user.getUsername());
    }

    /**
     * 取消选课
     *
     * @param user 取消选课的用户
     * @param n    取消哪个栏的的课程
     * @return
     */
    @Override
    public User cancelSubjectBySubjectIdn(User user, int n) {
        userDao.updateSubjectByUserIdAndSubjectId(user.getId(),0,n);
        return userDao.queryByUserName(user.getUsername());
    }

    /**
     * 通过level查询含有该等级的用户
     *
     * @param level 用来查询的level
     * @return 该等级的User
     */
    @Override
    public ArrayList getUserByLevel(int level) {
        return userDao.queryByLevel(level);
    }

    /**
     * 通过User id查询含有该等级的用户
     *
     * @param userId 用户id
     * @return 用户
     */
    @Override
    public User getUserByUserId(int userId) {
        return userDao.queryByUserId(userId);
    }

    /**
     * 通过用户id和level修改该用户level
     *
     * @param userId 用户id
     * @param level  等级
     * @return 修改后的User
     */
    @Override
    public User updateUserLevelByUserId(int userId, int level) {
        userDao.updateUserLevelByUserIdAndLevel(userId,level);
        return userDao.queryByUserId(userId);
    }

    /**
     * 通过用户获取归属路径
     *
     * @param user 用户
     * @return 归属路径
     */
    @Override
    public ArrayList getBelongByUser(User user) {
        ArrayList arrayList=new ArrayList();
        Class classTemp=classService.getClassByClassId(user.getClassId());
        Grade grade=gradeService.getGradeByClass(classTemp);
        Faculty faculty=facultyService.getFacultyByGrade(grade);
        arrayList.add(faculty);
        arrayList.add(grade);
        arrayList.add(classTemp);
        return arrayList;
    }

    /**
     * 通过用户id更新班级id
     *
     * @param classId 班级id
     * @param userId  用户id
     * @return 是否更新成功
     */
    @Override
    public boolean updateClassIdByUserId(int classId, int userId) {
        return userDao.updateClassIdByUserIdAndClassId(classId,userId);
    }

    /**
     * 通过班级号获取班级下的用户
     *
     * @param classId 课程id
     * @return 获取该班级下的所有用户
     */
    @Override
    public ArrayList getUserByClassId(int classId) {
        return userDao.queryByClassId(classId);
    }

    /**
     * 注销（未启用）
     *
     * @param user 需要注销的用户
     * @return 是否注销成功
     */
    @Override
    public boolean logout(User user) {

        return userDao.updateLoginStatusByUserIdAndLoginStatus(user.getId(),0);
    }
}
