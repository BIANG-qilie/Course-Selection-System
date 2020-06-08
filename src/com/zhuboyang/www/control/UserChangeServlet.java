package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.ClassServiceImpl;
import com.zhuboyang.www.service.impl.FacultyServiceImpl;
import com.zhuboyang.www.service.impl.GradeServiceImpl;
import com.zhuboyang.www.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author BIANG
 */
public class UserChangeServlet extends HttpServlet {
    UserService userService=new UserServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int userId=Integer.parseInt(request.getParameter("userSelecting"));
        User user=userService.getUserByUserId(userId);
        PrintWriter out=response.getWriter();
        out.write("<form action=\"userChange\" method=\"get\">\n" +
                "    <table align=\"center\" border=\"1\" width=\"300\" height=\"300\" cellspacing=\"0\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"2\"><font color=\"blue\" face=\"楷体\" size=\"7\">学生编辑</font></th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>\n" +
                "                用户名:\n" +
                "            </th>\n" +
                "            <th>\n" +
                "                "+user.getUsername()+"\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th>\n" +
                "                所在班级:\n" +
                "            </th>\n" +
                "            <th>\n" +
                "                <select name=\"classSelect\">\n");
        setBelongSelect(out,user);
        out.write("                </select>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th colspan=\"2\">\n" +
                "                <input type=\"hidden\" name=\"userId\" value=\""+user.getId()+"\">\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <th colspan=\"2\">\n" +
                "                <input type=\"submit\" value=\"完成编辑\">\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</form>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int classId=Integer.parseInt(request.getParameter("classSelect"));
        int userId=Integer.parseInt(request.getParameter("userId"));

        userService.updateClassIdByUserId(classId,userId);

        ControlUtil.alert("编辑成功",response,"userManage");
    }
    void setBelongSelect(PrintWriter out,User user){
        ArrayList arrayListToFaculty=new ArrayList();
        arrayListToFaculty.addAll(facultyService.getAllFaculty());
        ArrayList arrayListToGrade=new ArrayList();
        ArrayList arrayListToClass=new ArrayList();
        ArrayList subjectBelong=userService.getBelongByUser(user);
        String subjectSelectedBelongString="", subjectBelongString;
        switch (subjectBelong.size()){
            case 3:subjectSelectedBelongString=((Class)subjectBelong.get(2)).getClassName();
            case 2:subjectSelectedBelongString=((Grade)subjectBelong.get(1)).getGradeName()+" "+subjectSelectedBelongString;
            case 1:subjectSelectedBelongString=((Faculty)subjectBelong.get(0)).getFacultyName()+" "+subjectSelectedBelongString;
                break;
            default:subjectSelectedBelongString="出错";
        }
        out.write("                        <option value=\""+user.getClassId()+"\">"+ subjectSelectedBelongString+"</option>\n");
        //找到所有学院
        for(int i=0;i<arrayListToFaculty.size();i++){
            Faculty faculty=(Faculty)arrayListToFaculty.get(i);
            //每个学院下面的各个年级
            arrayListToGrade.addAll(gradeService.getGradeByFaculty(faculty));
            for(int j=0;j<arrayListToGrade.size();j++){
                Grade grade=(Grade)arrayListToGrade.get(j);
                //各个年级下的各个班级
                arrayListToClass.addAll(classService.getClassByGrade(grade));
                for(int k=0;k<arrayListToClass.size();k++){
                    Class classTemp=(Class)arrayListToClass.get(k);
                    subjectBelongString=faculty.getFacultyName()+" "+grade.getGradeName()+" "+classTemp.getClassName();
                    if(!subjectBelongString.equals(subjectSelectedBelongString)) {
                        out.write("                        <option value=\""+classTemp.getId()+"\">"+subjectBelongString+"</option>\n");
                    }
                }
                arrayListToClass.clear();
            }
            arrayListToGrade.clear();
        }
    }
}
