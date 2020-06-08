package com.zhuboyang.www.control;

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
public class FacultyChangeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int gradeId=Integer.parseInt(request.getParameter("gradeSelecting"));
        gradeService.deleteGradeByGradeId(gradeId);
        ControlUtil.alert("年级解散成功",response,"facultyManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int facultyId=Integer.parseInt(request.getParameter("facultySelecting"));
        setFaculty(facultyService.getFacultyByFacultyId(facultyId));
        PrintWriter out=response.getWriter();


        addGradeFunction(out);
        checkUsingFunction(out);
        checkFacultyFunction(out);
        returnToFacultyManageFunction(out);
        changeGradeFunction(out);

        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"6\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">学院管理</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        fillTable(out);
    }

    SubjectService subjectService=new SubjectServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    /**
     * 填充表格
     * @param out PrintWriter
     */
    void fillTable(PrintWriter out){
        Faculty faculty=facultyService.getFacultyByFacultyId(getFaculty().getId());
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(gradeService.getGradeByFaculty(faculty));
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>年级id</th>\n" +
                "            <th>年 级 名</th>\n" +
                "            <th>班 级 数</th>\n" +
                "            <th>课 程 数</th>\n" +
                "            <th>学 院 名</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"5\">共找到"+arraySize+"个年级</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            Grade grade = (Grade) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+grade.getId()+"</td>\n" +
                    "        <td align=\"center\">"+grade.getGradeName()+"</td>\n" +
                    "        <td align=\"center\" name=\""+grade.getId()+"\">"+getClassNumber(grade)+"</td>\n" +
                    "        <td align=\"center\" name=\""+grade.getId()+"\">"+subjectService.getSubjectByBelongId("grade",grade.getId()).size()+"</td>\n" +
                    "        <td align=\"center\">"+facultyService.getFacultyByGrade(grade).getFacultyName()+ "</td>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "             <form action=\"changeFaculty\" method=\"post\" name=\"selectFrom\">\n" +
                "            <th>\n" +
                "                    <input type=\"submit\" value=\"编辑\" onclick=\"return checkGrade()&&changeGrade()\"/>\n" +
                "             </th>\n" +
                "             <th>\n");
        out.write("                   <select name=\"gradeSelecting\">\n" +
                "                       <option value=\"0\">\n" +
                "                            --选择年级--\n" +
                "                       </option>\n");
        for(int i =0;i<arraySize;i++){
            Grade grade=(Grade) arrayList.get(i);
            out.write("                       <option value=\""+grade.getId()+"\">\n" +
                    "                            "+grade.getGradeName()+"\n" +
                    "                       </option>\n");
        }
        out.write("                   </select>\n");
        out.write("                    <input type=\"submit\" value=\"解散\"  onclick=\"return checkGrade()&&checkUsing()\"/>\n");
        out.write("             </th>\n" +
                "             </form>\n"+
                "             <th></th>\n" +
                "             <th>\n");
        out.write("                    <input type=\"button\" value=\"新建\" onclick=\"addGrade()\"/>\n");
        out.write("             </th>\n" +
                "            <th><button onclick=\"returnToFacultyManage()\">返回</button></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }
    /**
     * 获取一个年级下面有几个班级
     * @param grade 查询的年级
     * @return 有几个班级
     */
    private int getClassNumber(Grade grade) {
        ArrayList arrayListOfClass=new ArrayList();
        arrayListOfClass.addAll(classService.getClassByGrade(grade));
        return arrayListOfClass.size();
    }

    /**
     * 添加年级
     * @param out PrintWriter
     */
    private void addGradeFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function addGrade() {\n" +
                "        window.location.href=\"addGrade\";\n" +
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
                "        var selectObj=document.getElementsByName(\"gradeSelecting\");" +
                "        var gradeObj=document.getElementsByName(selectObj[0].value);\n" +
                "        if(gradeObj[0].innerText>0) {\n" +
                "            alert(\"该年级不为空\");\n" +
                "            return false;\n" +
                "        }\n" +
                "        else\n" +
                "            if (gradeObj[1].innerText > 0) {\n" +
                "            alert(\"该年级课程不为空\");\n" +
                "                return false;\n" +
                "            } else\n" +
                "                return true;\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * js函数检查选中的年级
     * @param out PrintWriter
     */
    void checkFacultyFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkGrade() {\n" +
                "        var selectObj=document.getElementsByName(\"gradeSelecting\");\n" +
                "        if(selectObj[0].value==0){\n" +
                "            return false;\n" +
                "        }else{\n" +
                "            return true;\n" +
                "        }\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * js函数跳转至home界面
     * @param out PrintWriter
     */
    void returnToFacultyManageFunction(PrintWriter out){
        out.write("<script>\n" +
                "            function returnToFacultyManage() {\n" +
                "       window.location.href=\"facultyManage\";\n"+
                "       }\n" +
                "        </script>\n");
    }
    /**
     * js函数跳转至编辑界面
     * @param out PrintWriter
     */
    void changeGradeFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function changeGrade() {\n" +
                "        document.selectFrom.action=\"changeGrade\";\n" +
                "        document.selectFrom.submit();\n" +
                "    }\n" +
                "</script>");
    }

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
