package com.zhuboyang.www.control;

import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.UserService;
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
public class ManagerAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int managerUserId=Integer.parseInt(request.getParameter("userSelecting"));

        userService.updateUserLevelByUserId(managerUserId,2);
        ControlUtil.alert("任命成功",response,"managerManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();
        out.write("<form action=\"addManager\" method=\"post\" name=\"addForm\">\n" +
                "        <table align=\"center\" border=\"1\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">管理员任命</font>\n" +
                "                </th>\n" +
                "            </tr>\n");
        setUserNameSelect(out);
        checkNameFunction(out);
        setSubmit(out);
        out.write("        </table>\n" +
                "    </form>");
    }
    UserService userService=new UserServiceImpl();
    /**
     * js函数检查选中的用户
     * @param out PrintWriter
     */
    private void checkNameFunction(PrintWriter out) {
        out.write("<script>\n" +
                "            function checkManager() {\n" +
                "                var selectObj=document.getElementsByName(\"userSelecting\");\n" +
                "                if(selectObj[0].value==0)\n" +
                "                    return false;\n" +
                "                return true;\n" +
                "            }\n" +
                "        </script>");
    }
    /**
     * 表格填充提交键
     * @param out 用来输入html的PrintWriter
     */
    void setSubmit(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <input type=\"submit\" value=\"确认任命\" onclick=\"return checkManager()\">\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 填充用户名下拉列表
     * @param out PrintWriter
     */
    private void setUserNameSelect(PrintWriter out) {
        out.write("            <tr>\n" +
                "            <th>\n" +
                "            受命用户</th>\n" +
                "            <th>\n" +
                "                <select name=\"userSelecting\">\n");
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(userService.getUserByLevel(1));
        out.write("                    <option value=\"0\">\n" +
                "                        --选择用户--\n" +
                "                    </option>\n");
        int arraySize=arrayList.size();
        for(int i=0;i<arraySize;i++){
            User user=(User)arrayList.get(i);
            out.write("                    <option value=\""+user.getId()+"\">\n" +
                    "                        "+user.getUsername()+"\n" +
                    "                    </option>\n");
        }
        out.write("                </select>\n" +
                "            </th>" +
                "            </tr>\n");
    }
}
