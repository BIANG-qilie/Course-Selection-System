package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
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
public class FacultyManageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int facultyId=Integer.parseInt(request.getParameter("facultySelecting"));
        facultyService.deleteFacultyByFacultyId(facultyId);
        ControlUtil.alert("学院解散成功",response,"facultyManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));
        PrintWriter out=response.getWriter();

        checkUsingFunction(out);
        addFacultyFunction(out);
        checkFacultyFunction(out);
        returnToHomeFunction(out);
        changeFacultyFunction(out);
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
    UserService userService=new UserServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();

    /**
     * 添加学院
     * @param out PrintWriter
     */
    private void addFacultyFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function addFaculty() {\n" +
                "        window.location.href=\"addFaculty\";\n" +
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
                "        var selectObj=document.getElementsByName(\"facultySelecting\");" +
                "        var gradeObj=document.getElementsByName(selectObj[0].value);\n" +
                "        if(gradeObj[0].innerText>0) {\n" +
                "            alert(\"该学院不为空\");\n" +
                "            return false;\n" +
                "        }\n" +
                "        else\n" +
                "            if (gradeObj[1].innerText > 0) {\n" +
                "            alert(\"该学院课程不为空\");\n" +
                "                return false;\n" +
                "            } else\n" +
                "                return true;\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * js函数检查选中的学院
     * @param out PrintWriter
     */
    void checkFacultyFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkFaculty() {\n" +
                "        var selectObj=document.getElementsByName(\"facultySelecting\");\n" +
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
    void returnToHomeFunction(PrintWriter out){
        out.write("<script>\n" +
                "            function returnToHome() {\n" +
                "       window.location.href=\"home\";\n"+
                "       }\n" +
                "        </script>\n");
    }
    /**
     * js函数跳转至编辑界面
     * @param out PrintWriter
     */
    void changeFacultyFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    function changeFaculty() {\n" +
                "        document.selectFrom.action=\"changeFaculty\";\n" +
                "        document.selectFrom.method=\"get\";\n" +
                "        document.selectFrom.submit();\n" +
                "    }\n" +
                "</script>");
    }
    /**
     * 填充表格
     * @param out PrintWriter
     */
    void fillTable(PrintWriter out){
        ArrayList arrayList=facultyService.getFacultyByManager(getLoginUser());
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>学院id</th>\n" +
                "            <th>学 院 名</th>\n" +
                "            <th>年 级 数</th>\n" +
                "            <th>班 级 数</th>\n" +
                "            <th>课 程 数</th>\n" +
                "            <th>管 理 员</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"8\">共找到"+arraySize+"个学院</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            Faculty faculty = (Faculty) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+faculty.getId()+"</td>\n" +
                    "        <td align=\"center\">"+faculty.getFacultyName()+"</td>\n" +
                    "        <td align=\"center\" name=\""+faculty.getId()+"\">"+getGradeNumber(faculty)+"</td>\n" +
                    "        <td align=\"center\" >"+getClassNumber(faculty)+"</td>\n" +
                    "        <td align=\"center\" name=\""+faculty.getId()+"\">"+subjectService.getSubjectByBelongId("faculty",faculty.getId()).size()+"</td>\n" +
                    "        <td align=\"center\">"+userService.getUserByUserId(faculty.getManagerUserId()).getUsername()+ "</td>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "             <form action=\"facultyManage\" method=\"post\" name=\"selectFrom\">\n" +
                "            <th>\n" +
                "                    <input type=\"submit\" value=\"编辑\" onclick=\"return checkFaculty()&&changeFaculty()\"/>\n" +
                "             </th>\n" +
                "             <th>\n");
        out.write("                   <select name=\"facultySelecting\">\n" +
                "                       <option value=\"0\">\n" +
                "                            --选择学院--\n" +
                "                       </option>\n");
        for(int i =0;i<arraySize;i++){
            Faculty faculty=(Faculty) arrayList.get(i);
            out.write("                       <option value=\""+faculty.getId()+"\">\n" +
                    "                            "+faculty.getFacultyName()+"\n" +
                    "                       </option>\n");
        }
        out.write("                   </select>\n");
        if(getLoginUser().getLevel()==3){
            out.write("                    <input type=\"submit\" value=\"解散\" onclick=\"return checkFaculty()&&checkUsing()\"/>\n");
        }
        out.write("             </th>\n" +
                "             </form>\n"+
                "             <th></th>\n" +
                "             <th>\n");
        if(getLoginUser().getLevel()==3) {
            out.write("                    <input type=\"button\" value=\"新建\" onclick=\"addFaculty()\"/>\n");
        }
        out.write("             </th>\n" +
                "             <th></th>\n" +
                "            <th><button onclick=\"returnToHome()\">返回</button></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }
    /**
     * 获取一个学院下面有几个班级
     * @param faculty 查询的学院
     * @return 有几个班级
     */
    private int getClassNumber(Faculty faculty) {
        ArrayList arrayListOfGrade=new ArrayList();
        ArrayList arrayListOfClass=new ArrayList();
        arrayListOfGrade.addAll(gradeService.getGradeByFaculty(faculty));
        for(int i=0;i<arrayListOfGrade.size();i++){
            Grade grade= (Grade) arrayListOfGrade.get(i);
            arrayListOfClass.addAll(classService.getClassByGrade(grade));
        }
        return arrayListOfClass.size();
    }
    /**
     * 获取一个学院下面有几个年级
     * @param faculty 查询的学院
     * @return 有几个年级
     */
    private int getGradeNumber(Faculty faculty) {
        return gradeService.getGradeByFaculty(faculty).size();
    }
    /**
     * 获取登录用户
     * @return 登录用户
     */
    public User getLoginUser() {
        return loginUser;
    }
    /**
     * 设置登录用户
     * @param loginUser 登录用户
     */
    public void setLoginUser(User loginUser) {
        ServletContext context = getServletContext();
        context.removeAttribute("loginUser");
        context.setAttribute("loginUser", loginUser);
        this.loginUser = loginUser;
    }
    private User loginUser;
}
