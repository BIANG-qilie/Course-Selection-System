package com.zhuboyang.www.service.impl;

import com.zhuboyang.www.dao.FacultyDao;
import com.zhuboyang.www.dao.UserDao;
import com.zhuboyang.www.dao.impl.FacultyDaoImpl;
import com.zhuboyang.www.dao.impl.UserDaoImpl;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.FacultyService;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public class FacultyServiceImpl implements FacultyService {
    FacultyDao facultyDao=new FacultyDaoImpl();
    UserDao userDao=new UserDaoImpl();
    /**
     * 获取管理者旗下的全部管理的课程
     *
     * @param manager 管理者的User
     * @return 全部管理的课程
     */
    @Override
    public ArrayList getFacultyByManager(User manager) {
        int managerLevel=2;
        int webmasterLevel=3;
        assert (manager!=null):"通过管理员用户获得其所能管理的学院时出错:管理员用户为null";
        if(manager.getLevel()==managerLevel) {
            return facultyDao.queryByManagerUserId(manager.getId());
        }if(manager.getLevel()==webmasterLevel){
            return facultyDao.queryAllFaculty();
        }
        return null;
    }

    /**
     * 通过年级获取所在的学院
     *
     * @param grade 用来查找的年级
     * @return 所在的学院
     */
    @Override
    public Faculty getFacultyByGrade(Grade grade) {
        assert (grade!=null):"通过年级获得其所归属的学院时出错:年级为null";
        Faculty faculty=facultyDao.queryByFacultyId(grade.getFacultyId());
        assert (faculty!=null):"通过年级获得其所归属的学院时出错:找不到该学院 faculty_id="+grade.getFacultyId();
        return faculty;
    }

    /**
     * 通过学院id获取学院
     *
     * @param facultyId 学院id
     * @return 学院
     */
    @Override
    public Faculty getFacultyByFacultyId(int facultyId) {
        Faculty faculty=facultyDao.queryByFacultyId(facultyId);
        assert (faculty!=null):"通过学院id获得该学院id的学院时出错:找不到该学院 faculty_id="+facultyId;
        return faculty;
    }

    /**
     * 获取全部学院
     *
     * @return 全部学院
     */
    @Override
    public ArrayList getAllFaculty() {
        return facultyDao.queryAllFaculty();
    }

    /**
     * 更新管理员
     *
     * @param facultyId     更新管理员的学院
     * @param managerUserId 更新后管理员的id
     * @return 更新后的学院
     */
    @Override
    public Faculty updateManagerUserId(int facultyId, int managerUserId) {
        assert (facultyDao.queryByFacultyId(facultyId)!=null):"更换管理员时出错:找不到该学院 faculty_id="+facultyId;
        assert (userDao.queryByUserId(managerUserId)!=null):"更换管理员时出错:找不到该管理员 manager_user_id="+managerUserId;
        assert (facultyDao.updateManagerUserIdByFacultyIdAndManagerUserId(facultyId,managerUserId)):"更换管理员时出错:未知的更新失败";
        return facultyDao.queryByFacultyId(facultyId);
    }

    /**
     * 通过faculty id删除faculty
     *
     * @param facultyId faculty id
     * @return 是否删除
     */
    @Override
    public boolean deleteFacultyByFacultyId(int facultyId) {
        assert facultyDao.queryByFacultyId(facultyId)!=null :"通过学院id删除学院时出错:找不到该学院 faculty_id="+facultyId;
        return facultyDao.deleteFacultyByFacultyId(facultyId);
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
        return facultyDao.createFacultyByFacultyNameAndManagerUserId(facultyName,mangerUserId);
    }
}
