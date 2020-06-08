package com.zhuboyang.www.control;

import com.zhuboyang.www.control.ControlUtil;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.impl.ClassServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author BIANG
 */
public class ClassAddServlet extends HttpServlet {
    ClassService classService=new ClassServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        ServletContext context = getServletContext();
        setGrade((Grade) context.getAttribute("grade"));

        String className=request.getParameter("className");

        classService.createClassByClassNameAndGradeId(className,getGrade().getId());

        ControlUtil.alert("新建班级成功",response,"facultyManage");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();

        ServletContext context = getServletContext();
        setGrade((Grade) context.getAttribute("grade"));
        out.write("<form action=\"addClass\" method=\"post\" name=\"addForm\">\n" +
                "        <table align=\"center\" border=\"1\" cellspacing=\"0\" width=\"300\" height=\"120\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">新建班级</font>\n" +
                "                </th>\n" +
                "            </tr>\n");

        checkNameFunction(out);
        setClassName(out);
        //setFacultySelect(out);
        out.write("<tr>\n" +
                "            <th>\n" +
                "                <input type=\"hidden\" value=\""+getGrade().getId()+"\">\n" +
                "            </th>\n" +
                "        </tr>");
        setSubmit(out);
        out.write("        </table>\n" +
                "    </form>");
    }

    /**
     * 设置学院名框
     * @param out PrintWriter
     */
    private void setClassName(PrintWriter out) {
        out.write("<tr>\n" +
                "        <th>班级名:</th>\n" +
                "        <th>\n" +
                "            <input type=\"text\" name=\"className\" value=\"\"/>\n" +
                "        </th>\n" +
                "    </tr>\n");
    }

    /**
     * js 检查名字是否为空的函数
     * @param out PrintWriter
     */
    private void checkNameFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkName() {\n" +
                "        var nameObj=document.getElementsByName(\"className\");\n" +
                "        if(nameObj[0]班级名不能为空\");\n" +
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
}
