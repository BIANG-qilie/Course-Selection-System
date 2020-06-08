package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.SubjectService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.SubjectServiceImpl;
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
public class UserManageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int userId=Integer.parseInt(request.getParameter("userSelecting"));
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));
        User user=userService.getUserByUserId(userId);
        if(user.getLevel()==1){
            userService.updateUserLevelByUserId(user.getId(),-1);
            for(int i=0;i<User.SUBJECT_SIZE;i++){
                userService.cancelSubjectBySubjectIdn(user,i+1);
            }
            userService.updateClassIdByUserId(0,user.getId());
            ControlUtil.alert("学生开除成功",response,"userManage");
            return;
        }
        ControlUtil.alert("学生开除失败:该学生非普通用户",response,"userManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();

        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"6\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">学生管理</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        checkUserFunction(out);
        changeUserFunction(out);
        addUserFunction(out);
        returnToHomeFunction(out);
        fillTable(out);
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

    private void checkUserFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function checkUser() {\n" +
                "        var selectObj=document.getElementsByName(\"userSelecting\");\n" +
                "        if(selectObj[0].value==0){\n" +
                "            return false;\n" +
                "        }else{\n" +
                "            return true;\n" +
                "        }\n" +
                "    }\n" +
                "</script>");
    }

    private void changeUserFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function changeUser() {\n" +
                "        document.selectFrom.action=\"userChange\";\n" +
                "        document.selectFrom.submit();\n" +
                "    }\n" +
                "</script>");
    }

    private void addUserFunction(PrintWriter out) {
        out.write("<script type=\"text/javascript\">\n" +
                "    function addUser() {\n" +
                "        window.location.href=\"register.html\";\n" +
                "    }\n" +
                "</script>");
    }

    UserService userService=new UserServiceImpl();
    /**
     * 填充表格
     * @param out PrintWriter
     */
    void fillTable(PrintWriter out){
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(userService.getUserByLevel(1));
        arrayList.addAll(userService.getUserByLevel(2));
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>用户id</th>\n" +
                "            <th>学 生 名</th>\n" +
                "            <th> 级 别 </th>\n" +
                "            <th>所属班级</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"8\">共找到"+arraySize+"个用户</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            User user = (User) arrayList.get(i);
            ArrayList arrayListOfBelong=userService.getBelongByUser(user);
            String belongString=((Faculty)arrayListOfBelong.get(0)).getFacultyName()+" "+((Grade)arrayListOfBelong.get(1)).getGradeName()+" "+((Class)arrayListOfBelong.get(2)).getClassName();
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+user.getId()+"</td>\n" +
                    "        <td align=\"center\">"+user.getUsername()+"</td>\n" +
                    "        <td align=\"center\">"+getLevelName(user)+"</td>\n" +
                    "        <td align=\"center\" >"+belongString+"</td>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "             <form action=\"userManage\" method=\"post\" name=\"selectFrom\">\n" +
                "            <th>\n" +
                "                    <input type=\"submit\" value=\"编辑\" onclick=\"return checkUser()&&changeUser()\"/>\n" +
                "             </th>\n" +
                "             <th>\n");
        out.write("                   <select name=\"userSelecting\">\n" +
                "                       <option value=\"0\">\n" +
                "                            --选择用户--\n" +
                "                       </option>\n");
        for(int i =0;i<arraySize;i++){
            User user = (User) arrayList.get(i);
            out.write("                       <option value=\""+user.getId()+"\">\n" +
                    "                            "+user.getUsername()+"\n" +
                    "                       </option>\n");
        }
        out.write("                   </select>\n");
        out.write("                    <input type=\"submit\" value=\"开除\" onclick=\"return checkUser()\"/>\n");
        out.write("             </th>\n" +
                "             </form>\n"+
                "             <th>\n");
        out.write("                    <input type=\"button\" value=\"新建\" onclick=\"addUser()\"/>\n");
        out.write("             </th>\n" +
                "            <th><button onclick=\"returnToHome()\">返回</button></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }

    public String getLevelName(User user){
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
