package com.zhuboyang.www.control;

import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.ClassServiceImpl;
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
public class ManagerManageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out=response.getWriter();
        checkManager(out);
        addManager(out);
        deleteManager(out);
        returnToHomeFunction(out);
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"8\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">管理员管理</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        fillTable(out);
    }
    UserService userService=new UserServiceImpl();
    ClassService classService=new ClassServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    /**
     * 填充表格
     * @param out PrintWriter
     */
    void fillTable(PrintWriter out){
        ArrayList arrayList=userService.getUserByLevel(2);
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>用户id</th>\n" +
                "            <th>用 户 名</th>\n" +
                "            <th> 等 级 </th>\n" +
                "            <th>所属班级</th>\n" +
                "            <th>已选课程1</th>\n" +
                "            <th>已选课程3</th>\n" +
                "            <th>已选课程3</th>\n" +
                "            <th>掌握</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"8\">共找到"+arraySize+"条课程</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            User user= (User) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+user.getId()+"</td>\n" +
                    "        <td align=\"center\">"+user.getUsername()+"</td>\n" +
                    "        <td align=\"center\">"+getLevelName(user)+"</td>\n" +
                    "        <td align=\"center\">"+classService.getBelongByClassId(user.getClassId())+"</td>\n" +
                    "        <td align=\"center\">"+user.getSubjectId(1)+"</td>\n" +
                    "        <td align=\"center\">"+user.getSubjectId(2)+"</td>\n" +
                    "        <td align=\"center\">"+user.getSubjectId(3)+"</td>\n" +
                    "        <th id=\""+user.getId()+"\">"+ isControlling(user) + "</th>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "            <th></th>\n" +
                "            <th></th>\n" +
                "            <form action=\"changeManager\" method=\"get\" name=\"changeFrom\">\n " +
                "                <th colspan=\"1\">\n" +
                "                    <input type=\"submit\" value=\"编辑\"/>\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <select name=\"managerSelect\">\n" +
                "                        <option value=\"0\">\n" +
                "                            --选择管理员--\n" +
                "                        </option>\n" );
        for (int i=0;i<arraySize;i++){
            User user= (User) arrayList.get(i);
            out.write("                        <option value=\""+user.getId()+"\">\n" +
                    "                            "+user.getUsername()+"\n" +
                    "                        </option>\n");
        }
        out.write("                    </select>\n" +
                "                    <input type=\"button\" value=\"解除\" onclick=\"return checkManager()&&deleteManager()\"/>\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <input type=\"button\" value=\"任命\" onclick=\"return addManager()\"/>\n" +
                "                </th>\n" +
                "            </form>\n" +
                "            <th><button onclick=\"returnToHome()\">返回</button></th>\n" +
                "            <th></th>\n" +
                "            <th></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }
    /**
     * js函数检查选中的管理员
     * @param out PrintWriter
     */
    void checkManager(PrintWriter out){
        out.write("<script>\n" +
                "            function checkManager() {\n" +
                "                var selectObj=document.getElementsByName(\"managerSelect\");\n" +
                "                if(selectObj[0].value==0)\n" +
                "                    return false;\n" +
                "                return true;\n" +
                "            }\n" +
                "        </script>");
    }
    /**
     * js函数跳转至addSubject界面
     * @param out PrintWriter
     */
    void addManager(PrintWriter out){
        out.write("<script>\n" +
                "            function addManager() {\n" +
                "                window.location.href=\"addManager\";\n" +
                "            }\n" +
                "        </script>");
    }
    /**
     * js函数跳转至deleteManager界面
     * @param out PrintWriter
     */
    void deleteManager(PrintWriter out){
        out.write("<script>\n" +
                "            function deleteManager() {\n" +
                "            var managerSelectObj=document.getElementsByName(\"managerSelect\");\n" +
                "            var textObj=document.getElementById(managerSelectObj[0].value);" +
                "            if(textObj.innerText==\"无\"){" +
                "                document.changeFrom.action=\"deleteManager\";\n" +
                "                document.changeFrom.submit();\n"+
                "                return true;\n" +
                "            }else{\n" +
                "                alert(\"Ta手下有掌管的学院\");\n" +
                "                   return false;\n" +
                "            }\n" +
                "       }\n" +
                "        </script>\n");
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

    String isControlling(User user) {
        if(facultyService.getFacultyByManager(user).size()==0){
            return "无";
        }else{
            return "有";
        }
    }

    String getLevelName(User user){
        switch (user.getLevel())
        {
            case -1:
                return "您已被开除";
            case 2:
                return "管理员";
            case 3:
                return "站长";
            default:
                return "学生";
        }
    }

}
