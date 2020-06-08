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
import java.util.Calendar;

/**
 * @author BIANG
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserService userService=new UserServiceImpl();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        setLoginUser(userService.login(new User(-1,username,password,1,-1,-1,-1,-1)));
        if(loginUser!=null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out= response.getWriter();
            out.write("<script type='text/javascript'>alert('登陆成功');</script>");
            if(getLoginUser().getLevel()!=-1) {
                out.write("<script type='text/javascript'>alert(\"" + getLevelName() + " 欢迎回来，" + greedByTime() + "!\")</script>\n");
                out.write("<script type='text/javascript'>window.location.href=\"home\"</script>");
                out.flush();
                out.close();
            }else{
                ControlUtil.alert("您已被开除，无法进入系统",response,"login.html");
            }
        }else {
            ControlUtil.alert("密码错误",response,"login.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public String greedByTime(){
        int hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour>5&&hour<7) {
            return "清晨好,早起的鸟儿有虫吃";
        }
        if(hour>=7&&hour<11){
            return "早上好,一日之计在于晨";
        }
        if(hour>=11&&hour<13){
            return "中午好，越努力越幸运哦";
        }
        else if(hour>=13&&hour<18){
            return "下午好，这是学习的大好时光";
        }
        else if(hour>=18&&hour<24){
            return "晚上好，要适当的给自己放松哦";
        }
        else if(hour>=0&&hour<5){
            return "凌晨好，熬夜对身体不好哦";
        }
        return "你好";
    }
    public String getLevelName(){
        switch (getLoginUser().getLevel())
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

    public User getLoginUser() {
        return loginUser;
    }
    public void setLoginUser(User loginUser) {
        ServletContext context = getServletContext();
        context.removeAttribute("loginUser");
        context.setAttribute("loginUser", loginUser);
        this.loginUser = loginUser;
    }
    private User loginUser;
}
