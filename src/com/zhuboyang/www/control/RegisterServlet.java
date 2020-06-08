package com.zhuboyang.www.control;

import com.zhuboyang.www.dao.UserDao;
import com.zhuboyang.www.dao.impl.UserDaoImpl;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;
import com.zhuboyang.www.service.UserService;
import com.zhuboyang.www.service.impl.ClassServiceImpl;
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
 * @author dell
 */
public class RegisterServlet extends HttpServlet {
    UserService userService=new UserServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String username=request.getParameter("username");
        String password=request.getParameterValues("password")[0];
        if(userService.isExist(username)) {
            ControlUtil.alert("用户名已存在",response,"register.html");
        }
        else{
            ServletContext context = getServletContext();
            context.removeAttribute("RegisterUser");
            context.setAttribute("RegisterUser", new User(-1,username,password,1,-1,-1,-1,-1));
            PrintWriter out=response.getWriter();
            out.write("<form action=\"register\" method=\"get\">\n" +
                    "        <table align=\"center\" border=\"0\" width=\"300\" height=\"300\" cellspacing=\"0\">\n" +
                    "<tr></tr>\n" +
                    "            <tr></tr>\n" +
                    "            <tr>\n" +
                    "                <th colspan=\"2\">注册</th>\n" +
                    "            </tr>\n" +
                    "            <tr></tr>\n" +
                    "            <tr>\n" +
                    "                <th>\n" +
                    "                    所在班级:\n" +
                    "                </th>\n" +
                    "                <th>\n" +
                    "                    <select name=\"classSelect\">\n");
            setBelongSelect(out);
            out.write("                    </select>\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "            <tr></tr>\n" +
                    "            <tr></tr>\n" +
                    "            <tr></tr>\n" +
                    "            <tr>\n" +
                    "                <th colspan=\"2\">\n" +
                    "                    <input type=\"submit\" >\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "    </form>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        User registerUser= (User)context.getAttribute("RegisterUser");
        int classId=Integer.parseInt(request.getParameter("classSelect"));
        userService.registerUser(new User(-1,registerUser.getUsername(),registerUser.getPassword(),1,classId,-1,-1,-1));
        setLoginUser((User) context.getAttribute("loginUser"));

        if(getLoginUser()==null) {
            ControlUtil.alert("注册成功",response,"index.html");
        }else{
            ControlUtil.alert("添加成功",response,"userManage");
        }
    }
    void setBelongSelect(PrintWriter out){
        ArrayList arrayListToFaculty=new ArrayList();
        ArrayList arrayListToGrade=new ArrayList();
        ArrayList arrayListToClass=new ArrayList();
        arrayListToFaculty.addAll(facultyService.getAllFaculty());
        for(int i=0;i<arrayListToFaculty.size();i++){
            Faculty faculty=(Faculty)arrayListToFaculty.get(i);
            String subjectBelongString;
            //每个学院下面的各个年级
            arrayListToGrade.addAll(gradeService.getGradeByFaculty(faculty));
            for(int j=0;j<arrayListToGrade.size();j++){
                Grade grade=(Grade)arrayListToGrade.get(j);
                //各个年级下的各个班级
                arrayListToClass.addAll(classService.getClassByGrade(grade));
                for(int k=0;k<arrayListToClass.size();k++){
                    Class classTemp=(Class)arrayListToClass.get(k);
                    subjectBelongString=faculty.getFacultyName()+" "+grade.getGradeName()+" "+classTemp.getClassName();
                    out.write("                        <option value=\""+classTemp.getId()+"\">"+subjectBelongString+"</option>\n");
                }
                arrayListToClass.clear();
            }
            arrayListToGrade.clear();
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
        this.loginUser = loginUser;
    }
    private User loginUser;
}
