package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.FacultyServiceImpl;
import com.zhuboyang.www.service.impl.GradeServiceImpl;
import com.zhuboyang.www.service.impl.UserServiceImpl;

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
public class GradeAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        ServletContext context = getServletContext();
        setFaculty((Faculty)context.getAttribute("faculty"));

        String gradeName=request.getParameter("gradeName");

        gradeService.createGradeByGradeNameAndFacultyId(gradeName,getFaculty().getId());

        ControlUtil.alert("新建年级成功",response,"facultyManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();
        ServletContext context = getServletContext();
        setFaculty((Faculty)context.getAttribute("faculty"));
        out.write("<form action=\"addGrade\" method=\"post\" name=\"addForm\">\n" +
                "        <table align=\"center\" border=\"1\" cellspacing=\"0\" width=\"300\" height=\"120\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">新建年级</font>\n" +
                "                </th>\n" +
                "            </tr>\n");

        checkNameFunction(out);
        setGradeName(out);
        //setFacultySelect(out);
        out.write("<tr>\n" +
                "            <th>\n" +
                "                <input type=\"hidden\" value=\""+getFaculty().getId()+"\">\n" +
                "            </th>\n" +
                "        </tr>");
        setSubmit(out);
        out.write("        </table>\n" +
                "    </form>");
    }
    UserService userService=new UserServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    private void setFacultySelect(PrintWriter out) {
        ArrayList arrayListOfManager=new ArrayList();
        arrayListOfManager.addAll(userService.getUserByLevel(2));
        int arraySizeOfManager=arrayListOfManager.size();
        out.write("<tr>\n" +
                "   <th>学院:</th>" +
                "   <th>\n" +
                "       <select name=\"managerSelecting\">\n");
        /*for(int j=0;j<arraySizeOfManager;j++){
            User manager=(User)arrayListOfManager.get(j);
            out.write("                    <option value=\""+manager.getId()+"\">\n" +
                    manager.getUsername() +"\n"+
                    "                    </option>\n");
        }
        out.write("     </select>\n" +
                "   </th>\n" +
                "</tr>\n");*/
    }

    /**
     * js 检查名字是否为空的函数
     * @param out PrintWriter
     */
    private void checkNameFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkName() {\n" +
                "        var nameObj=document.getElementsByName(\"gradeName\");\n" +
                "        if(nameObj[0].value==\"\"){\n" +
                "            alert(\"年级名不能为空\");\n" +
                "            return false;\n" +
                "        }else{\n" +
                "            return true;\n" +
                "        }\n" +
                "    }\n" +
                "</script>");
    }

    /**
     * 设置提交键
     * @param out PrintWriter
     */
    private void setSubmit(PrintWriter out) {
        out.write("<tr>\n" +
                "        <th colspan=\"2\" ><input type=\"submit\" onclick=\"return checkName()\"></th>\n" +
                "    </tr>");
    }

    /**
     * 设置学院名框
     * @param out PrintWriter
     */
    private void setGradeName(PrintWriter out) {
        out.write("<tr>\n" +
                "        <th>年级名:</th>\n" +
                "        <th>\n" +
                "            <input type=\"text\" name=\"gradeName\" value=\"\"/>\n" +
                "        </th>\n" +
                "    </tr>\n");
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
