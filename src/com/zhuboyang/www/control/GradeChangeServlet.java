package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.service.*;
import com.zhuboyang.www.service.impl.*;

import javax.servlet.ServletContext;
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
public class GradeChangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int classId=Integer.parseInt(request.getParameter("classSelecting"));
        classService.deleteClassByClassId(classId);
        ControlUtil.alert("班级解散成功",response,"facultyManage");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int gradeId=Integer.parseInt(request.getParameter("gradeSelecting"));

        setGrade(gradeService.getGradeByGradeId(gradeId));
        ServletContext context = getServletContext();
        setFaculty((Faculty)context.getAttribute("faculty"));
        PrintWriter out=response.getWriter();


        addClassFunction(out);
        checkUsingFunction(out);
        checkClassFunction(out);
        returnToFacultyManageFunction(out);

        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"6\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">班级管理</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        fillTable(out);
    }
    SubjectService subjectService=new SubjectServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    UserService userService=new UserServiceImpl();

    /**
     * 添加班级
     * @param out PrintWriter
     */
    private void addClassFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function addClass() {\n" +
                "        window.location.href=\"addClass\";\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * js函数检查选中的学院是否不为空
     * @param out PrintWriter
     */
    void checkUsingFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkUsing() {\n" +
                "        var selectObj=document.getElementsByName(\"classSelecting\");\n" +
                "        var classObj=document.getElementsByName(selectObj[0].value);\n" +
                "        if(classObj[0].innerText>0) {\n" +
                "            alert(\"该班级课程不为空\");\n" +
                "            return false;\n" +
                "        }\n" +
                "        else if(classObj[1].innerText>0){\n" +
                "            alert(\"该班级学生不为空\");\n" +
                "            return false;\n" +
                "        }\n" +
                "        return true;\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * js函数检查选中的班级
     * @param out PrintWriter
     */
    void checkClassFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkClass() {\n" +
                "        var selectObj=document.getElementsByName(\"classSelecting\");\n" +
                "        if(selectObj[0].value==0){\n" +
                "            return false;\n" +
                "        }else{\n" +
                "            return true;\n" +
                "        }\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * 填充表格
     * @param out PrintWriter
     */
    void returnToFacultyManageFunction(PrintWriter out){
        out.write("<script>\n" +
                "            function returnToFacultyManage() {\n" +
                "       window.location.href=\"facultyManage\";\n"+
                "       }\n" +
                "        </script>\n");
    }

    void fillTable(PrintWriter out){
        Grade grade=gradeService.getGradeByGradeId(getGrade().getId());
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(classService.getClassByGrade(grade));
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>班级id</th>\n" +
                "            <th>班 级 名</th>\n" +
                "            <th>学 生 数</th>\n" +
                "            <th>课 程 数</th>\n" +
                "            <th>归属路径</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"5\">共找到"+arraySize+"个班级</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            Class classTemp = (Class) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+classTemp.getId()+"</td>\n" +
                    "        <td align=\"center\">"+classTemp.getClassName()+"</td>\n" +
                    "        <td align=\"center\" name=\""+classTemp.getId()+"\">"+userService.getUserByClassId(classTemp.getId()).size()+"</td>\n"+
                    "        <td align=\"center\" name=\""+classTemp.getId()+"\">"+subjectService.getSubjectByBelongId("class",classTemp.getId()).size()+"</td>\n" +
                    "        <td align=\"center\">"+facultyService.getFacultyByGrade(grade).getFacultyName()+" "+grade.getGradeName()+ "</td>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "             <form action=\"changeGrade\" method=\"get\" name=\"selectFrom\">\n" +
                "             <th>\n");
        out.write("                   <select name=\"classSelecting\">\n" +
                "                       <option value=\"0\">\n" +
                "                            --选择班级--\n" +
                "                       </option>\n");
        for(int i =0;i<arraySize;i++){
            Class classTemp = (Class) arrayList.get(i);
            out.write("                       <option value=\""+classTemp.getId()+"\">\n" +
                    "                            "+classTemp.getClassName()+"\n" +
                    "                       </option>\n");
        }
        out.write("                   </select>\n");
        out.write("                    <input type=\"submit\" value=\"解散\" onclick=\"return checkClass()&&checkUsing()\"/>\n");
        out.write("             </th>\n" +
                "             </form>\n"+
                "             <th></th>\n" +
                "             <th></th>\n" +
                "             <th>\n");
        out.write("                    <input type=\"button\" value=\"新建\" onclick=\"addClass()\"/>\n");
        out.write("             </th>\n" +
                "            <th><button onclick=\"returnToFacultyManage()\">返回</button></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }

    public Grade getGrade() {
        return grade;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
        ServletContext context = getServletContext();
        context.removeAttribute("grade");
        context.setAttribute("grade", grade);
    }
    Grade grade;

    public Faculty getFaculty() {
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        ServletContext context = getServletContext();
        context.removeAttribute("faculty");
        context.setAttribute("faculty", faculty);
    }
    Faculty faculty;
}
