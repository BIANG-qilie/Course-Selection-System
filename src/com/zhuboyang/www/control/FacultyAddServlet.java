package com.zhuboyang.www.control;

import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.FacultyServiceImpl;
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
public class FacultyAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String facultyName=request.getParameter("facultyName");
        int mangerUserId=Integer.parseInt(request.getParameter("managerSelecting"));

        facultyService.createFacultyByFacultyNameAndManagerUserId(facultyName,mangerUserId);

        ControlUtil.alert("新建学院成功",response,"facultyManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();
        out.write("<form action=\"addFaculty\" method=\"post\" name=\"addForm\">\n" +
                "        <table align=\"center\" border=\"1\" cellspacing=\"0\" width=\"300\" height=\"120\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">新建学院</font>\n" +
                "                </th>\n" +
                "            </tr>\n");

        checkNameFunction(out);
        setFacultyName(out);
        setManagerSelect(out);
        setSubmit(out);
        out.write("        </table>\n" +
                "    </form>");
    }
    UserService userService=new UserServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    private void setManagerSelect(PrintWriter out) {
        ArrayList arrayListOfManager=new ArrayList();
        arrayListOfManager.addAll(userService.getUserByLevel(2));
        int arraySizeOfManager=arrayListOfManager.size();
        out.write("<tr>\n" +
                "   <th>管理员:</th>" +
                "   <th>\n" +
                "       <select name=\"managerSelecting\">\n");
        for(int j=0;j<arraySizeOfManager;j++){
            User manager=(User)arrayListOfManager.get(j);
            out.write("                    <option value=\""+manager.getId()+"\">\n" +
                    manager.getUsername() +"\n"+
                    "                    </option>\n");
        }
        out.write("     </select>\n" +
                "   </th>\n" +
                "</tr>\n");
    }

    /**
     * js 检查名字是否为空的函数
     * @param out PrintWriter
     */
    private void checkNameFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkName() {\n" +
                "        var nameObj=document.getElementsByName(\"facultyName\");\n" +
                "        if(nameObj[0].value==\"\"){\n" +
                "            alert(\"学院名不能为空\");\n" +
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
    private void setFacultyName(PrintWriter out) {
        out.write("<tr>\n" +
                "        <th>学院名:</th>\n" +
                "        <th>\n" +
                "            <input type=\"text\" name=\"facultyName\" value=\"\"/>\n" +
                "        </th>\n" +
                "    </tr>\n");
    }
}
