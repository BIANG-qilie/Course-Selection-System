package com.zhuboyang.www.service.impl;

import com.zhuboyang.www.dao.FacultyDao;
import com.zhuboyang.www.dao.GradeDao;
import com.zhuboyang.www.dao.impl.FacultyDaoImpl;
import com.zhuboyang.www.dao.impl.GradeDaoImpl;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.service.GradeService;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public class GradeServiceImpl implements GradeService {
    GradeDao gradeDao=new GradeDaoImpl();
    /**
     * 通过班级获得年级
     *
     * @param tempClass 班级
     * @return 班级所在的年级
     */
    @Override
    public Grade getGradeByClass(Class tempClass) {
        Grade grade=gradeDao.queryByGradeId(tempClass.getGradeId());
        assert grade!=null :"通过班级获得其年级时出错:该年级不存在 grade_id="+tempClass.getGradeId();
        return gradeDao.queryByGradeId(tempClass.getGradeId());
    }

    /**
     * 通过学院获得年级
     *
     * @param faculty 学院
     * @return 找到的年纪
     */
    @Override
    public ArrayList getGradeByFaculty(Faculty faculty) {
        assert faculty!=null:"通过学院获得归属于它的年级时出错:学院为null";
        return gradeDao.queryByFacultyId(faculty.getId());
    }

    /**
     * 通过年级id获得年级
     *
     * @param gradeId 年级id
     * @return 年级
     */
    @Override
    public Grade getGradeByGradeId(int gradeId) {
        Grade grade=gradeDao.queryByGradeId(gradeId);
        assert grade!=null :"通过年级id获得年级时出错:该年级不存在 grade_id="+gradeId;
        return grade;
    }

    /**
     * 通过grade id删除grade
     *
     * @param gradeId grade id
     * @return 是否删除
     */
    @Override
    public boolean deleteGradeByGradeId(int gradeId) {
        assert gradeDao.queryByGradeId(gradeId)!=null :"通过年级id删除年级时出错:找不到该年级 grade_id="+gradeId;
        return gradeDao.deleteGradeByGradeId(gradeId);
    }

    /**
     * 用年级名和学院id创建一个新的年级
     *
     * @param gradeName 年级名
     * @param facultyId 学院id
     * @return 是否成功创建
     */
    @Override
    public boolean createGradeByGradeNameAndFacultyId(String gradeName, int facultyId) {
        return gradeDao.createGradeByGradeNameAndFacultyId(gradeName,facultyId);
    }


}
