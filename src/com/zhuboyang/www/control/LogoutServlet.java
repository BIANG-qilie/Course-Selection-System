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

/**
 * @author BIANG
 * 未启用
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        System.out.println("注销");
        setLoginUser ((User)context.getAttribute("loginUser"));
        context.removeAttribute("loginUser");
        userService.logout(getLoginUser());
        PrintWriter out=response.getWriter();
        out.write("<script type=\"text/javascript\">\n" +
                "        window.onload=function(){\n" +
                "                window.close();\n" +
                "            }\n" +
                "        }\n" +
                "    </script>");
    }
    UserService userService=new UserServiceImpl();
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
