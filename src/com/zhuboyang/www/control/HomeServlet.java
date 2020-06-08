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

public class HomeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));
        PrintWriter out= response.getWriter();

        selectSubjectFunction(out);
        editSubjectFunction(out);
        if(loginUser.getLevel()==3||loginUser.getLevel()==2){
            out.write("<script type=\"text/javascript\">\n" +
                    "            function manage() {\n" +
                    "                window.location.href=\"managerSubject\";\n" +
                    "            }\n" +
                    "        </script>\n");
        }
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n"+
                "           <tr>\n" +
                "               <th colspan=\"8\">\n" +
                "                   <font color=\"blue\" face=\"楷体\" size=\"7\">选课系统</font>\n" +
                "               </th>\n" +
                "           </tr>\n" +
                "     </table>\n");
        createTable(out);
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"30\" cellspacing=\"0\" id=\"tableEnd\">\n"+
                "   <tr></tr>\n" +
                "        <tr>\n" +
                "           <th >\n" +
                "               <font color=\"blue\" face=\"宋体\" size=\"2\">ps:每个学生只可以选择3门课</font>\n" +
                "           </th>\n" +
                "        </tr>\n" +
                "        <tr></tr>\n" +
                "</table>\n");
        moreFunction(out);
    }

    SubjectService subjectService=new SubjectServiceImpl();

    /**
     * 跳转至选课界面的函数
     * @param out 声明过的PrintWriter
     */
    public void selectSubjectFunction(PrintWriter out){
        out.write("<script type='text/javascript'>\n" +
                "          function selectSubject(){\n" +
                "               var selectObj=document.getElementsByName(\"subjectSelect\");\n" +
                "               if(selectObj[0].value==0)\n" +
                "                   return false;\n" +
                "     }\n" +
                "     </script>\n");
    }
    /**
     * 跳转至编辑已选课程的界面的函数
     * @param out 声明过的PrintWriter
     */
    public void editSubjectFunction(PrintWriter out){
        out.write("<script type='text/javascript'>\n" +
                "          function editSubject(){\n" +
                "               window.location.href=\"editSubject\";\n" +
                "     }\n" +
                "     </script>\n");
    }

    /**
     * 制表
     * @param out 声明过的PrintWriter
     */
    public void createTable(PrintWriter out){
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(subjectService.getSubjectByUser(getLoginUser()));
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n"+
                "           <tr>\n" +
                "               <th>课程id</th>\n" +
                "               <th>课程名</th>\n" +
                "               <th>上课日</th>\n" +
                "                <th>上课时间</th>\n" +
                "                <th>下课时间</th>\n" +
                "                <th>开始选课时间</th>\n" +
                "                <th>结束选课时间</th>\n" +
                /*"                 <th>归属级别</th>\n" +
                "                   <th>归属id</th>\n" +*/
                "                <th>选课</th>\n" +
                "           </tr>\n");
        fillTable(out);
        out.write("</table>\n");
    }
    /**
     * 用数据库的课程内容填充表格
     * @param out 声明过的PrintWriter
     */
    public void fillTable(PrintWriter out){
        ArrayList arrayList=new ArrayList();
        arrayList.addAll(subjectService.getSubjectByUser(getLoginUser()));
        int arraySize=arrayList.size();
        out.write("     <tr>" +
                "              <th colspan=\"8\">共找到"+arraySize+"条课程</th>" +
                "          </tr>\n");
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
                    "        <th>"+ isSelected(subject.getId()) + " </th>\n" +
                    "    </tr>\n");
        }
        if(loginUser.getLevel()==3||loginUser.getLevel()==2){
            out.write("     <tr>\n" +
                    "                   <th></th>\n" +
                    "<th>\n" +
                    "            <button onclick=\"manage()\">\n" +
                    "                管理课程\n" +
                    "            </button>\n" +
                    "        </th>\n");
        }else {
            out.write("                   <th></th>\n" +
                    "                   <th></th>\n");
        }
        out.write(
                "               <form action=\"chooseSubject\" method=\"get\">\n" +
                "                   <th colspan=\"2\">\n" +
                "                       <select name=\"subjectSelect\">\n" +
                "                           <option value=\"0\">\n" +
                "                               --选择课程--\n" +
                "                           </option>\n");
        for (int i=0;i<arraySize;i++){
            Subject subject= (Subject) arrayList.get(i);
            out.write("                  <option value=\""+subject.getId()+"\">\n" +
                    subject.getSubjectName()+"\n"+
                    "                       </option>\n");
        }
        out.write("                 </select>\n" +
                "                   </th>\n" );
        out.write("                   <th></th>\n" +
                "                   <th>" +
                "                       <input type=\"submit\" value=\"选课\" onclick=\"return selectSubject()\"/>" +
                "                   </th>\n" +
                "                   <th>" +
                "                       <input type=\"button\" value=\"编辑已选课程\" onclick=\"return editSubject()\"/>\n" +
                "                   </th>\n" +
                "                   <th></th>\n" +
                "              </form>" +
                "          </tr>\n");
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
     * 检查是否已选这节课
     * @param subjectId 所谓的这节课
     * @return "已选","未选"
     */
    public String isSelected(int subjectId){
        for(int i = 0;i<3;i++){
            int n=i+1;
            if(loginUser.getSubjectId(n)==subjectId) {
                return "已选";
            }
        }
        return "未选";
    }

    public void moreFunction(PrintWriter out){
        if(getLoginUser().getLevel()!=1){
            out.write("<script type='text/javascript'>\n" +
                    "    function faculty() {\n" +
                    "        window.location.href=\"facultyManage\";\n" +
                    "    }\n" +
                    "</script>");
            out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"30\" cellspacing=\"0\" id=\"otherFunction\">\n" +
                    "    <tr>\n" +
                    "        <th>\n");
            if(getLoginUser().getLevel()==3) {
                out.write("            <button onclick=\"manager()\">管理员管理</button>\n" );
                out.write("<script type='text/javascript'>\n" +
                        "    function manager() {\n" +
                        "    window.location.href=\"managerManage\";\n" +
                        "    }\n" +
                        "</script>\n");
            }
            out.write( "        </th>\n" +
                    "        <th>\n" +
                    "            <button onclick=\"faculty()\">学 院 管 理</button>\n" +
                    "        </th>\n" +
                    "        <th>\n");
            if(getLoginUser().getLevel()==3) {
                out.write("            <button onclick=\"user()\">学 生 管 理</button>\n");
                out.write("<script type='text/javascript'>\n" +
                        "    function user() {\n" +
                        "        window.location.href=\"userManage\";\n" +
                        "    }\n" +
                        "</script>");
            }
            out.write("        </th>\n" +
                    "    </tr>\n" +
                    "</table>");
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
