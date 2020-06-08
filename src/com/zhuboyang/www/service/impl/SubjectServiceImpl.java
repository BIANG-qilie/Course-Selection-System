package com.zhuboyang.www.service.impl;

import com.zhuboyang.www.dao.SubjectDao;
import com.zhuboyang.www.dao.UserDao;
import com.zhuboyang.www.dao.impl.SubjectDaoImpl;
import com.zhuboyang.www.dao.impl.UserDaoImpl;
import com.zhuboyang.www.po.*;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;
import com.zhuboyang.www.service.SubjectService;

import java.util.ArrayList;

/**
 * @author BIANG
 */
public class SubjectServiceImpl implements SubjectService {
    UserDao userDao=new UserDaoImpl();
    SubjectDao subjectDao=new SubjectDaoImpl();
    ClassService classService=new ClassServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    /**
     * 通过一个User直接找到它能选的课程
     *
     * @param user 用来找课程的user
     * @return 返回能选的课程
     */
    @Override
    public ArrayList getSubjectByUser(User user) {
        assert user!=null:"通过用户获取其能选择的课程时出错:用户为null";
        ArrayList arrayList=new ArrayList();
        Class tempClass=classService.getClassByUser(user);
        assert tempClass!=null:"通过用户获取其能选择的课程时出错:找不到该班级 class_id="+user.getClassId();
        Grade grade=gradeService.getGradeByClass(tempClass);
        assert grade!=null:"通过用户获取其能选择的课程时出错:找不到该年级 grade_id="+tempClass.getGradeId();
        Faculty faculty=facultyService.getFacultyByGrade(grade);
        assert faculty!=null:"通过用户获取其能选择的课程时出错:找不到该学院 faculty_id="+grade.getFacultyId();
        arrayList.addAll(subjectDao.queryByBelongId("class",tempClass.getId()));
        arrayList.addAll(subjectDao.queryByBelongId("grade",grade.getId()));
        arrayList.addAll(subjectDao.queryByBelongId("faculty",faculty.getId()));;
        return arrayList;
    }

    /**
     * 通过一个课程id获取课程
     *
     * @param subjectId 要获取的课程的id
     * @return 获取到的课程
     */
    @Override
    public Subject getSubjectBySubjectId(int subjectId) {
        Subject subject=subjectDao.queryBySubjectId(subjectId);
        assert subject!=null :"通过课程id获得课程时出错:找不到该课程 subject_id="+subjectId;
        return subject;
    }

    /**
     * 通过一个User直接找到它管理的课程
     *
     * @param manager 用来找课程的user
     * @return 返回能管理的课程
     */
    @Override
    public ArrayList getSubjectByManager(User manager) {
        assert manager!=null :"通过一个管理者用户获取它管理的课程时出错:管理员用户为null";
        int mangerLevel=2;
        int webMasterLevel=3;
        ArrayList arrayList=new ArrayList();
        assert (manager.getLevel()==2||manager.getLevel()==3):"通过一个管理者用户获取它管理的课程时出错:管理员用户管理等级异常 level=" +manager.getLevel();
        if(manager.getLevel()==webMasterLevel){
            arrayList.addAll(subjectDao.queryAllSubject());
        }else if(manager.getLevel()==mangerLevel){
            ArrayList arrayListToFaculty=new ArrayList();
            ArrayList arrayListToGrade=new ArrayList();
            ArrayList arrayListToClass=new ArrayList();
            //manager获取id查询管理的学院
            arrayListToFaculty.addAll(facultyService.getFacultyByManager(manager));
            for(int i=0;i<arrayListToFaculty.size();i++){
                arrayList.addAll(subjectDao.queryByBelongId("faculty", ((Faculty) arrayListToFaculty.get(i)).getId()));
                //学院查询旗下的年级
                arrayListToGrade.addAll(gradeService.getGradeByFaculty((Faculty) arrayListToFaculty.get(i)));
            }
            for(int i=0;i<arrayListToGrade.size();i++){
                arrayList.addAll(subjectDao.queryByBelongId("grade",((Grade)arrayListToGrade.get(i)).getId()));
                //年级查询旗下的班级
                arrayListToClass.addAll(classService.getClassByGrade((Grade)arrayListToGrade.get(i)));
            }
            for (int i=0;i<arrayListToClass.size();i++){
                arrayList.addAll(subjectDao.queryByBelongId("class",((Class)arrayListToClass.get(i)).getId()));
            }
        }
        return arrayList;
    }

    /**
     * 查询该课程是否有人选过
     *
     * @param subject 课程id
     * @return 是则返回true
     */
    @Override
    public boolean isSelected(Subject subject) {
        assert subject!=null:"通过课程查询是否被人选中时出错:课程为null";
        return (userDao.queryBySubjectId(subject.getId()).size()!=0);
    }

    /**
     * 根据课程获取归属路径
     *
     * @param subject 课程
     * @return 归属路径
     */
    @Override
    public ArrayList getBelongBySubject(Subject subject) {
        assert subject!=null:"通过课程获取归属路径时出错:课程为null";
        ArrayList arrayList=new ArrayList();
        String belongClass="class";
        String belongGrade="grade";
        String belongFaculty="faculty";
        if(belongClass.equals(subject.getBelong())) {
            Class classTemp=classService.getClassByClassId(subject.getBelongId());
            Grade grade=gradeService.getGradeByClass(classTemp);
            Faculty faculty=facultyService.getFacultyByGrade(grade);
            arrayList.add(faculty);
            arrayList.add(grade);
            arrayList.add(classTemp);
        }else if (belongGrade.equals(subject.getBelong())){
            Grade grade=gradeService.getGradeByGradeId(subject.getBelongId());
            Faculty faculty=facultyService.getFacultyByGrade(grade);
            arrayList.add(faculty);
            arrayList.add(grade);
        }else if(belongFaculty.equals(subject.getBelong())){
            Faculty faculty=facultyService.getFacultyByFacultyId(subject.getBelongId());
            arrayList.add(faculty);
        }else{
            System.out.println("出错：subject.getBelong()="+subject.getBelong());
        }
        return arrayList;
    }

    /**
     * 更新整个subject id为subjectId的课程
     *
     * @param subjectId   用来确定哪个课程的subject id
     * @param subjectName 修改后的课程名
     * @param startTime   修改后的上课时间
     * @param stopTime    修改后的下课时间
     * @param selectStart 修改后的开始选课时间
     * @param selectStop  修改后的结束选课时间
     * @param dayOfWeek   修改后的上课日
     * @param belong      修改后的课程归属
     * @param belongId    修改后的课程归属id
     * @return 更新后的课程
     */
    @Override
    public Subject updateSubjectBySubjectData(int subjectId, String subjectName, String startTime, String stopTime, String selectStart, String selectStop, int dayOfWeek, String belong, int belongId) {
        assert subjectDao.queryBySubjectId(subjectId)!=null :"通过课程id更新整个课程时出错:找不到该课程 subject_id="+subjectId;
        subjectDao.updateSubjectBySubjectId(subjectId,subjectName,startTime,stopTime,selectStart,selectStop,dayOfWeek,belong,belongId);
        return subjectDao.queryBySubjectId(subjectId);
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
    public boolean createSubjectBySubjectData(String subjectName, String startTime, String stopTime, String selectStart, String selectStop, int dayOfWeek, String belong, int belongId) {
        return subjectDao.createSubjectBySubject(subjectName,startTime,stopTime,selectStart,selectStop,dayOfWeek,belong,belongId);
    }

    /**
     * 通过subject id删除subject
     *
     * @param subjectId 要被删除的subject的id
     * @return 是否被删除
     */
    @Override
    public boolean deleteSubjectBySubjectId(int subjectId) {
        assert subjectDao.queryBySubjectId(subjectId)!=null :"通过课程id删除课程时出错:找不到该课程 subject_id="+subjectId;
        return subjectDao.deleteSubjectBySubjectId(subjectId);
    }

    /**
     * 通过一个归属直接找到归属下的全部课程
     *
     * @param belong   归属
     * @param belongId 归属id
     * @return 返回课程
     */
    @Override
    public ArrayList getSubjectByBelongId(String belong, int belongId) {
        String facultyString="faculty";
        String gradeString="grade";
        String classString="class";
        assert (belong==facultyString||belong==gradeString||belong==classString):"通过归属和归属id找到归属下的所有课程时出错:未知的归属 belong="+belong;
        ArrayList arrayList=new ArrayList();
        ArrayList arrayListToGrade=new ArrayList();
        ArrayList arrayListToClass=new ArrayList();
        //manager获取id查询管理的学院
        if(facultyString.equals(belong)){
            facultyService.getFacultyByFacultyId(belongId);
            arrayList.addAll(subjectDao.queryByBelongId(facultyString, belongId));
            //学院查询旗下的年级
            arrayListToGrade.addAll(gradeService.getGradeByFaculty(facultyService.getFacultyByFacultyId(belongId)));
            for(int i=0;i<arrayListToGrade.size();i++){
                arrayList.addAll(subjectDao.queryByBelongId(gradeString,((Grade)arrayListToGrade.get(i)).getId()));
                //年级查询旗下的班级
                arrayListToClass.addAll(classService.getClassByGrade((Grade)arrayListToGrade.get(i)));
            }
            for (int i=0;i<arrayListToClass.size();i++){
                arrayList.addAll(subjectDao.queryByBelongId(classString,((Class)arrayListToClass.get(i)).getId()));
            }
        }else
            if(gradeString.equals(belong)){
                gradeService.getGradeByGradeId(belongId);
                arrayList.addAll(subjectDao.queryByBelongId(gradeString,belongId));
                arrayListToClass.addAll(classService.getClassByGrade(gradeService.getGradeByGradeId(belongId)));
                for (int i=0;i<arrayListToClass.size();i++){
                    arrayList.addAll(subjectDao.queryByBelongId(classString,((Class)arrayListToClass.get(i)).getId()));
                }
            }
            else
                if(classString.equals(belong)){
                    classService.getClassByClassId(belongId);
                    arrayList.addAll(subjectDao.queryByBelongId(classString,belongId));
                }
        return arrayList;
    }


}
