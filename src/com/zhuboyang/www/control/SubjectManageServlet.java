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

/**
 * @author BIANG
 */
public class SubjectManageServlet extends HttpServlet {
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
        Subject[] subjects={null,null,null};
        int subjectSize=3;
        for(int i=0;i<subjectSize;i++){
            int n=i+1;
            if(getLoginUser().getSubjectId(n)!=0) {
                subjects[i]=subjectService.getSubjectBySubjectId(getLoginUser().getSubjectId(n));
            }
        }
        returnToHomeFunction(out);
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"300\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"8\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">已选课程编辑</font>\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <th>课程id</th>\n" +
                "                <th>课程名</th>\n" +
                "                <th>上课日</th>\n" +
                "                <th>上课时间</th>\n" +
                "                <th>下课时间</th>\n" +
                "                <th>开始选课时间</th>\n" +
                "                <th>结束选课时间</th>\n" +
                "                <th>编辑</th>\n" +
                "            </tr>\n" );
        for(int i=0;i<subjectSize;i++){
            out.write("            <tr>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getId())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getSubjectName())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getDayOfWeek())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getStartTime())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getStopTime())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getSelectStart())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":subjects[i].getSelectStop())+"</th>\n" +
                    "                <th>"+((subjects[i]==null)? "--":"<form action=\"cancel\" method=\"post\">\n" +
                    "                       <input type=\"hidden\" value=\""+(i+1) +"\" name=\"subjectId\">" +
                    "                       <input type=\"submit\" value=\"取消选课\" id=\""+i+"\"/>\n" +
                    "                </form>")+"</th>\n" +
                    "            </tr>\n");
        }
        out.write("            <tr>\n" +
                     "                <th colspan=\"8\"><button onclick=\"returnToHome()\">返回</button></th>\n" +
                     "            </tr>\n"+
                     "    </table>>");
    }
    public void returnToHomeFunction(PrintWriter out){
        out.write("<script type='text/javascript'>\n" +
                "        function returnToHome(){\n" +
                "            window.location.href=\"home\";\n" +
                "        }\n" +
                "    </script>");
    }
    SubjectService subjectService=new SubjectServiceImpl();
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
