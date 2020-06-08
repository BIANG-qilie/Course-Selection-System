package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Subject;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.SubjectService;
import com.zhuboyang.www.service.impl.SubjectServiceImpl;

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
public class ManagerSubjectServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));
        PrintWriter out=response.getWriter();
        checkSubject(out);
        addSubject(out);
        deleteSubject(out);
        returnToHomeFunction(out);
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"8\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">管理课程</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        fillTable(out);
    }
    SubjectService subjectService=new SubjectServiceImpl();

    /**
     * js函数检查选中的课程
     * @param out PrintWriter
     */
    void checkSubject(PrintWriter out){
        out.write("<script>\n" +
                "            function checkSubject() {\n" +
                "                var selectObj=document.getElementsByName(\"subjectSelect\");\n" +
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
    void addSubject(PrintWriter out){
        out.write("<script>\n" +
                "            function addSubject() {\n" +
                "                window.location.href=\"addSubject\";\n" +
                "            }\n" +
                "        </script>");
    }
    /**
     * js函数跳转至deleteSubject界面
     * @param out PrintWriter
     */
    void deleteSubject(PrintWriter out){
        out.write("<script>\n" +
                "            function deleteSubject() {\n" +
                "            var subjectSelectObj=document.getElementsByName(\"subjectSelect\");\n" +
                "            var textObj=document.getElementById(subjectSelectObj[0].value);" +
                "            if(textObj.innerText==\"未被选\"){" +
                "                document.changeFrom.action=\"deleteSubject\";\n" +
                "                document.changeFrom.submit();\n"+
                "                return true;\n" +
                "            }else{\n" +
                "                alert(\"有用户选了该课程\");\n" +
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

    /**
     * 填充表格
     * @param out PrintWriter
     */
    void fillTable(PrintWriter out){
        ArrayList arrayList=subjectService.getSubjectByManager(getLoginUser());
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>课程id</th>\n" +
                "            <th>课程名</th>\n" +
                "            <th>上课日</th>\n" +
                "            <th>上课时间</th>\n" +
                "            <th>下课时间</th>\n" +
                "            <th>开始选课时间</th>\n" +
                "            <th>结束选课时间</th>\n" +
                "            <th>归属</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"8\">共找到"+arraySize+"条课程</th>          </tr>");
        for(int i=0;i<arraySize;i++){
            Subject subject= (Subject) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+subject.getId()+"</td>\n" +
                    "        <td align=\"center\">"+subject.getSubjectName()+"</td>\n" +
                    "        <td align=\"center\">"+getDayOfWeek(subject.getDayOfWeek())+"</td>\n" +
                    "        <td align=\"center\">"+subject.getStartTime()+"</td>\n" +
                    "        <td align=\"center\">"+subject.getStopTime()+"</td>\n" +
                    "        <td align=\"center\">"+subject.getSelectStart()+"</td>\n" +
                    "        <td align=\"center\">"+subject.getSelectStop()+"</td>\n" +
                    "        <th id=\""+subject.getId()+"\">"+ isSelected(subject) + "</th>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "            <th></th>\n" +
                "            <th></th>\n" +
                "            <form action=\"changeSubject\" method=\"get\" name=\"changeFrom\">\n " +
                "                <th colspan=\"3\">\n" +
                "                    <select name=\"subjectSelect\">\n" +
                "                        <option value=\"0\">\n" +
                "                            --选择课程--\n" +
                "                        </option>\n" );
        for (int i=0;i<arraySize;i++){
            Subject subject=(Subject)arrayList.get(i);
            out.write("                        <option value=\""+subject.getId()+"\">\n" +
                    "                            "+subject.getSubjectName()+"\n" +
                    "                        </option>\n");
        }
        out.write("                    </select>\n" +
                "                    <input type=\"submit\" value=\"编辑\" onclick=\"return checkSubject()\"/>\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <input type=\"button\" value=\"新增\" onclick=\"return addSubject()\"/>\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <input type=\"button\" value=\"删除\" onclick=\"return checkSubject()&&deleteSubject()\"/>\n" +
                "                </th>\n" +
                "            </form>\n" +
                "            <th><button onclick=\"returnToHome()\">返回</button></th>\n" +
                "        </tr>\n" +
                "    </table>");
    }
    /**
     * 检查是否有人已选这节课
     * @param subject 所谓的这节课
     * @return "已选","未选"
     */
    public String isSelected(Subject subject){
        if(subjectService.isSelected(subject)) {
            return "已被选";
        }
        return "未被选";
    }
    /**
     * 由数字获取是周几
     * @param dayOfWeek 数字（1~7）
     * @return 星期几
     */
    public String getDayOfWeek(int dayOfWeek){
        switch (dayOfWeek){
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "出错";
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
