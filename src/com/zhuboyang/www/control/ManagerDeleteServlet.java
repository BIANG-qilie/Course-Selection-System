package com.zhuboyang.www.control;

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

/**
 * @author BIANG
 */
public class ManagerDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        int managerUserId=Integer.parseInt(request.getParameter("managerSelect"));
        userService.updateUserLevelByUserId(managerUserId,1);
        ControlUtil.alert("解除管理身份成功",response,"managerManage");
    }
    UserService userService=new UserServiceImpl();
}
