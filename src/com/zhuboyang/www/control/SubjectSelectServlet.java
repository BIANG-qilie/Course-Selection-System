package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Subject;
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
import java.sql.Date;
import java.sql.Time;

/**
 * @author BIANG
 */
public class SubjectSelectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));

        int subjectSelectingId=Integer.parseInt(request.getParameter("subjectSelect"));

        Subject subjectSelecting=subjectService.getSubjectBySubjectId(subjectSelectingId);
        if(!isPermit(subjectSelecting)){
            ControlUtil.alert("课程不在选课时间内", response, "home");
            return;
        }
        for(int i=0;i<User.SUBJECT_SIZE;i++){
            int n=i+1,subjectId=getLoginUser().getSubjectId(n);
            if(subjectId!=0) {
                Subject subjectSelected=subjectService.getSubjectBySubjectId(subjectId);
                if (subjectSelected.getId() == subjectSelecting.getId()) {
                    ControlUtil.alert("该课程已选", response, "home");
                    return;
                }
                if (subjectSelected.getDayOfWeek() == subjectSelecting.getDayOfWeek()) {
                    if (!((subjectSelected.getStartTime().after(subjectSelecting.getStopTime()))
                            || (subjectSelecting.getStartTime().after(subjectSelected.getStopTime()) ))) {
                        ControlUtil.alert("与" + subjectSelected.getSubjectName() + "时间冲突", response, "home");
                        return;
                    }
                }
            }
        }
        for(int i=0;i<User.SUBJECT_SIZE;i++) {
            int n=i+1,subjectId=getLoginUser().getSubjectId(n);
            if (subjectId == 0) {
                setLoginUser(userService.chooseSubject(getLoginUser(), subjectSelectingId, n));
                ControlUtil.alert("课程选择成功",response,"home");
                return;
            }
        }
        ControlUtil.alert("课程已满",response,"home");
        return;
    }
    UserService userService=new UserServiceImpl();
    SubjectService subjectService=new SubjectServiceImpl();

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

    public boolean isPermit(Subject subject) {
        Date nowDate = Date.valueOf(new Date(System.currentTimeMillis()).toString());
        Time nowTime = Time.valueOf(new Time(new java.util.Date().getTime()).toString());
        if(subject.getSelectStartDate().compareTo(nowDate) <= 0&& subject.getSelectStopDate().compareTo(nowDate) >= 0){
            if(subject.getSelectStartDate().compareTo(nowDate) <= 0&& subject.getSelectStopDate().compareTo(nowDate) > 0){
                return true;
            }
            if (subject.getSelectStartTime().compareTo(nowTime) <= 0 && subject.getSelectStopTime().compareTo(nowTime) >= 0) {
                return true;
            }
        }
        return false;
    }
}
