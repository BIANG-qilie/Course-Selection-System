package com.zhuboyang.www.service.impl;

import com.zhuboyang.www.dao.ClassDao;
import com.zhuboyang.www.dao.impl.ClassDaoImpl;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public class ClassServiceImpl implements ClassService {
    ClassDao classDao=new ClassDaoImpl();
    GradeService gradeService=new GradeServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    /**
     * 通过user的class_id查询class
     *
     * @param user 用户
     * @return class
     */
    @Override
    public Class getClassByUser(User user) {
        assert (user!=null):"通过用户获得其所在班级时出错:用户为null";
        return classDao.queryByClassId(user.getClassId());
    }

    /**
     * 通过grade获取旗下的所有class
     *
     * @param grade 用来查询的年级
     * @return 旗下的class
     */
    @Override
    public ArrayList getClassByGrade(Grade grade) {
        assert (grade!=null):"通过年级获得归属于它的班级时出错:年级为null";
        return classDao.queryByGradeId(grade.getId());
    }

    /**
     * 通过class_id查询class
     *
     * @param classId 用户
     * @return class
     */
    @Override
    public Class getClassByClassId(int classId) {
        Class classTemp=classDao.queryByClassId(classId);
        assert (classTemp!=null):"通过班级id获得该班级id的班级时出错:找不到该班级，class_id="+classId;
        return classTemp;
    }

    /**
     * 根据班级id获取归属路径
     *
     * @param classId 班级id
     * @return 归属路径
     */
    @Override
    public String getBelongByClassId(int classId) {
        Class classTemp=getClassByClassId(classId);
        assert (classTemp!=null):"通过班级id获得该班级的归属路径时出错:找不到该班级,class_id="+classId;
        Grade grade=gradeService.getGradeByClass(classTemp);
        assert (grade!=null):"通过班级id获得该班级的归属路径时出错:找不到该年级,grade_id="+classTemp.getGradeId();
        Faculty faculty=facultyService.getFacultyByGrade(grade);
        assert (faculty!=null):"通过班级id获得该班级的归属路径时出错:找不到该学院,faculty_id="+grade.getFacultyId();
        return faculty.getFacultyName()+" "+grade.getGradeName()+" "+classTemp.getClassName();
    }

    /**
     * 通过class id删除class
     *
     * @param classId grade id
     * @return
     */
    @Override
    public boolean deleteClassByClassId(int classId) {
        assert classDao.queryByClassId(classId)!=null:"通过班级id删除班级时出错:该班级id对应的班级不存在 class_id="+classId;
        return classDao.deleteClassByClassId(classId);
    }

    /**
     * 用班级名和年级id创建一个新的年级
     *  @param className 班级名
     * @param gradeId   年级id
     * @return
     */
    @Override
    public boolean createClassByClassNameAndGradeId(String className, int gradeId) {
        return classDao.createClassByClassNameAndGradeId(className,gradeId);
    }
}
