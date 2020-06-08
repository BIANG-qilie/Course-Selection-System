package com.zhuboyang.www.view;

import com.zhuboyang.www.control.ControlUtil;
import com.zhuboyang.www.po.Class;
import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.User;
import com.zhuboyang.www.service.ClassService;
import com.zhuboyang.www.service.FacultyService;
import com.zhuboyang.www.service.GradeService;
import com.zhuboyang.www.service.SubjectService;
import com.zhuboyang.www.service.impl.ClassServiceImpl;
import com.zhuboyang.www.service.impl.FacultyServiceImpl;
import com.zhuboyang.www.service.impl.GradeServiceImpl;
import com.zhuboyang.www.service.impl.SubjectServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class SubjectAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));

        String subjectName=request.getParameter("subjectName");
        int dayOfWeek=Integer.parseInt(request.getParameter("dayOfWeek"));
        String startTime=request.getParameter("startTimehh")+":"+request.getParameter("startTimemm")+":"+request.getParameter("startTimess");
        String stopTime=request.getParameter("stopTimehh")+":"+request.getParameter("stopTimemm")+":"+request.getParameter("stopTimess");
        String startSelect=request.getParameter("selectStartTimeYYYY")+"-"+request.getParameter("selectStartTimeMM")+
                "-"+request.getParameter("selectStartTimeDD")+" "+request.getParameter("selectStartTimehh")+
                ":"+request.getParameter("selectStartTimemm")+":"+request.getParameter("selectStartTimess");
        String stopSelect=request.getParameter("selectStopTimeYYYY")+"-"+request.getParameter("selectStopTimeMM")+
                "-"+request.getParameter("selectStopTimeDD")+" "+request.getParameter("selectStopTimehh")+
                ":"+request.getParameter("selectStopTimemm")+":"+request.getParameter("selectStopTimess");
        String belong=request.getParameter("belong");
        int belongId=Integer.parseInt(belong.substring(belong.indexOf("-")+1,belong.length()));
        belong=belong.substring(0,belong.indexOf("-"));
        subjectService.createSubjectBySubjectData(subjectName,startTime,stopTime,startSelect,stopSelect,dayOfWeek,belong,belongId);
        ControlUtil.alert("添加成功",response,"managerSubject");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext context = getServletContext();
        setLoginUser ((User)context.getAttribute("loginUser"));
        PrintWriter out=response.getWriter();
        setBaseTag(out);
        out.write("<form action=\"addSubject\" method=\"post\" name=\"addForm\">\n" +
                "        <table align=\"center\" border=\"1\" cellspacing=\"0\">\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <font color=\"blue\" face=\"楷体\" size=\"7\">课程添加</font>\n" +
                "                </th>\n" +
                "            </tr>\n");
        setSubjectName(out);
        setDayOfWeek(out);
        setStartTime(out);
        setStopTime(out);
        setSelectStart(out);
        setSelectStop(out);
        setBelong(out);
        setSubmit(out);
        out.write("        </table>\n" +
                "    </form>");
    }
    SubjectService subjectService=new SubjectServiceImpl();
    ClassService classService=new ClassServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    FacultyService facultyService=new FacultyServiceImpl();
    /**
     * 格式化时间
     * @param time 时间
     * @return 格式化后的时间
     */
    String formatTime(int time){
        return ((time<10)?"0"+time: String.valueOf(time));
    }
    /**
     * 表格填充时间下拉选项
     * @param out
     * @param time
     * @param timeMax
     */
    void setTimeOption(PrintWriter out,int time,int timeMin,int timeMax){
        String timeString;
        timeString=formatTime(time);
        out.write("                        <option value=\""+timeString+"\">"+timeString+"</option>\n" );
        for(int i =timeMin;i<timeMax;i++) {
            if(i==time) { continue; }
            timeString=formatTime(i);
            out.write("                        <option value=\""+timeString+"\">"+timeString+"</option>\n" );
        }
    }
    /**
     * 填充开始以及结束选课需要用到的js函数
     * @param out 用来输入html的PrintWriter
     */
    void setSelectFunction(PrintWriter out) {
        String date=new java.sql.Date(System.currentTimeMillis()).toString();
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        int monthInt=Integer.parseInt(month);
        int dayInt=Integer.parseInt(day);
        out.write("    <script language=\"JavaScript\">\n" +
                "        function formatTime(time) {\n" +
                "            return ((time<10)?\"0\"+time: time);\n" +
                "        }\n" +
                "        function YYYYMMDDInitialization() {\n" +
                "            var MMSize = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];\n" +
                "\n" +
                "            var y  ="+year+";\n" +
                "            for (var i = (y-30); i < (y+30); i++){\n" +
                "                if(i=="+year+")continue;\n" +
                "                document.addForm.selectStartTimeYYYY.options.add(new Option( i , i));\n" +
                "                document.addForm.selectStopTimeYYYY.options.add(new Option( i , i));\n" +
                "            }\n" +
                "\n" +
                "            for (var i = 1; i < 13; i++){\n" +
                "                if(i=="+monthInt+")continue;\n" +
                "                document.addForm.selectStartTimeMM.options.add(new Option( formatTime(i) , formatTime(i)));\n" +
                "                document.addForm.selectStopTimeMM.options.add(new Option( formatTime(i) , formatTime(i)));\n" +
                "            }\n" +
                "\n" +
                "            document.addForm.selectStartTimeYYYY.value = y;\n" +
                "            document.addForm.selectStartTimeMM.value =\""+month+"\";\n" +
                "            document.addForm.selectStopTimeYYYY.value = y;\n" +
                "            document.addForm.selectStopTimeMM.value =\""+month+"\";\n" +
                "            var n = MMSize["+(monthInt-1)+"];\n" +
                "            if ("+monthInt+" ==2 && IsPinYear(YYYYMMDDInitialization)) n++;\n" +
                "            writeDay(n);\n" +
                "            document.addForm.selectStartTimeDD.value =\""+day+"\";\n" +
                "            document.addForm.selectStopTimeDD.value =\""+day+"\";\n" +
                "        }\n" +
                "\n" +
                "        window.addEventListener('load', YYYYMMDDInitialization, false);\n" +
                "\n" +
                "        function YYYYDDChange(str) \n" +
                "        {\n" +
                "            var MMSize = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];\n" +
                "            var MMValueStart = document.addForm.selectStartTimeMM[document.addForm.selectStartTimeMM.selectedIndex].value;\n" +
                "            var MMValueStop = document.addForm.selectStopTimeMM[document.addForm.selectStopTimeMM.selectedIndex].value;\n" +
                "            var nStart = MMSize[MMValueStart - 1];\n" +
                "            var nStop = MMSize[MMValueStop - 1];\n" +
                "            if (MMValueStart ==2 && IsPinYear(str)) nStart++;\n" +
                "            if (MMValueStop ==2 && IsPinYear(str)) nStop++;\n" +
                "            writeDay(nStart);\n" +
                "            writeDay(nStop);\n" +
                "        }\n" +
                "        function MMDDChange(str) \n" +
                "        {\n" +
                "            var MMSize = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];\n" +
                "            var YYYYValueStart = document.addForm.selectStartTimeYYYY.options[document.addForm.selectStartTimeYYYY.selectedIndex].value;\n" +
                "            var YYYYValueStop = document.addForm.selectStopTimeYYYY.options[document.addForm.selectStopTimeYYYY.selectedIndex].value;\n" +
                "            var nStart = MMSize[str - 1];\n" +
                "            var nStop = MMSize[str - 1];\n" +
                "            if (str ==2 && IsPinYear(YYYYValueStart)) nStart++;\n" +
                "            if (str ==2 && IsPinYear(YYYYValueStop)) nStop++;\n" +
                "            writeDay(nStart);\n" +
                "            writeDay(nStop);\n" +
                "        }\n" +
                "        function writeDay(n)\n" +
                "        {\n" +
                "            var eStart = document.addForm.selectStartTimeDD; optionsClear(eStart);\n" +
                "            var eStop = document.addForm.selectStopTimeDD; optionsClear(eStop);\n" +
                "            for (var i=1; i<(n+1); i++){\n" +
                "                if(i=="+dayInt+") continue;\n" +
                "                eStart.options.add(new Option( formatTime(i) ,  formatTime(i)));\n" +
                "            }\n" +
                "            for (var i=1; i<(n+1); i++){\n" +
                "                if(i=="+dayInt+") continue;\n" +
                "                eStop.options.add(new Option( formatTime(i) , formatTime(i)));\n" +
                "             }\n" +
                "        }\n" +
                "        function IsPinYear(year)\n" +
                "        {     return(0 == year%4 && (year%100 !=0 || year%400 == 0));}\n" +
                "        function optionsClear(e)\n" +
                "        {\n" +
                "            e.options.length = 1;\n" +
                "        }\n" +
                "    </script>\n");
    }
    /**
     * 检查课程名是否为空
     * @param out PrintWriter
     */
    void setCheckNameFunction(PrintWriter out){
        out.write("    <script type=\"text/javascript\">\n" +
                "        function checkName(){\n" +
                "            var subjectNameObj=document.getElementsByName(\"subjectName\");\n" +
                "            if(subjectNameObj[0].value==\"\")\n" +
                "                return false;\n" +
                "            return true;\n" +
                "        }\n" +
                "    </script>");
    }


    /**
     * 表格填充课程名
     * @param out 用来输入html的PrintWriter
     */
    void setSubjectName(PrintWriter out){
        out.write("            <tr>\n"+
                "                <th>\n" +
                "                    课 程 名:\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <input type=\"text\" name=\"subjectName\" />\n" +
                "                </th>\n" +
                "            </tr>\n" );
    }
    /**
     * 表格填充上课日
     * @param out 用来输入html的PrintWriter
     */
    void setDayOfWeek(PrintWriter out){
        int dayOfWeek=1;
        out.write( "            <tr>\n" +
                "                <th>\n" +
                "                    上 课 日:\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <select name=\"dayOfWeek\">\n" +
                "                        <option value=\""+dayOfWeek+"\">"+getDayOfWeek(dayOfWeek)+"</option>\n");
        for(int i = 1;i<=7;i++) {
            if(i==dayOfWeek){continue;}
            out.write("                        <option value=\""+i+"\">"+getDayOfWeek(i)+"</option>\n");
        }
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 表格填充上课时间
     * @param out 用来输入html的PrintWriter
     */
    void setStartTime(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th>\n" +
                "                    上课时间:\n" +
                "                </th>\n" +
                "                <th>\n");
        String time=new Time(new Date().getTime()).toString();
        String hourString=time.substring(0,2);
        String minuteString=time.substring(3,5);
        String secondString=time.substring(6,8);
        int second=Integer.parseInt(secondString);
        int minute=Integer.parseInt(minuteString);
        int hour= Integer.parseInt(hourString);
        String timeString;
        out.write("                    <select name=\"startTimehh\">\n");
        setTimeOption(out,hour,0,24);
        out.write("                    </select>:\n" +
                "                    <select name=\"startTimemm\">\n");
        setTimeOption(out,minute,0,60);
        out.write("                    </select>:\n" +
                "                    <select name=\"startTimess\">\n");
        setTimeOption(out,second,0,60);
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 表格填充下课时间
     * @param out 用来输入html的PrintWriter
     */
    void setStopTime(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th>\n" +
                "                    下课时间:\n" +
                "                </th>\n" +
                "                <th>\n");
        String time=new Time(new Date().getTime()).toString();
        String hourString=time.substring(0,2);
        String minuteString=time.substring(3,5);
        String secondString=time.substring(6,8);
        int second=Integer.parseInt(secondString);
        int minute=Integer.parseInt(minuteString);
        int hour= Integer.parseInt(hourString);
        out.write("                    <select name=\"stopTimehh\">\n");
        setTimeOption(out,hour,0,24);
        out.write("                    </select>:\n" +
                "                    <select name=\"stopTimemm\">\n");
        setTimeOption(out,minute,0,60);
        out.write("                    </select>:\n" +
                "                    <select name=\"stopTimess\">\n");
        setTimeOption(out,second,0,60);
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 表格填充开始选课时间
     * @param out 用来输入html的PrintWriter
     */
    void setSelectStart(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th>\n" +
                "                    开始选课时间:\n" +
                "                </th>\n" +
                "                <th>\n");
        String date=new java.sql.Date(System.currentTimeMillis()).toString();
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        String time=new Time(System.currentTimeMillis()).toString();
        String hour=time.substring(0,2);
        String minute=time.substring(3,5);
        String second=time.substring(6,8);
        out.write("<select name=\"selectStartTimeYYYY\" onchange=\"YYYYDDChange(this.value)\">\n" +
                "        <option value=\""+year+"\">"+year+"</option>\n" +
                "    </select>年\n" +
                "    <select name=\"selectStartTimeMM\" onchange=\"MMDDChange(this.value)\">\n" +
                "        <option value=\""+month+"\">"+month+"</option>\n" +
                "    </select>月\n" +
                "    <select name=\"selectStartTimeDD\">\n" +
                "        <option value=\""+day+"\">"+day+"</option>\n" +
                "    </select>日\n" );
        String timeString;
        out.write("                    <select name=\"selectStartTimehh\">\n");
        setTimeOption(out,Integer.parseInt(hour),0,24);
        out.write("                    </select>:\n" +
                "                    <select name=\"selectStartTimemm\">\n");
        setTimeOption(out,Integer.parseInt(minute),0,60);
        out.write("                    </select>:\n" +
                "                    <select name=\"selectStartTimess\">\n");
        setTimeOption(out,Integer.parseInt(second),0,60);
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 表格填充结束选课时间
     * @param out 用来输入html的PrintWriter
     */
    void setSelectStop(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th>\n" +
                "                    结束选课时间:\n" +
                "                </th>\n" +
                "                <th>\n");
        String date=new java.sql.Date(System.currentTimeMillis()).toString();
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        String time=new Time(System.currentTimeMillis()).toString();
        String hour=time.substring(0,2);
        String minute=time.substring(3,5);
        String second=time.substring(6,8);
        out.write("<select name=\"selectStopTimeYYYY\" onchange=\"YYYYDDChange(this.value)\">\n" +
                "        <option value=\""+year+"\">"+year+"</option>\n" +
                "    </select>年\n" +
                "    <select name=\"selectStopTimeMM\" onchange=\"MMDDChange(this.value)\">\n" +
                "        <option value=\""+month+"\">"+month+"</option>\n" +
                "    </select>月\n" +
                "    <select name=\"selectStopTimeDD\">\n" +
                "        <option value=\""+day+"\">"+day+"</option>\n" +
                "    </select>日\n" );
        out.write("                    <select name=\"selectStopTimehh\">\n");
        setTimeOption(out,Integer.parseInt(hour),0,24);
        out.write("                    </select>:\n" +
                "                    <select name=\"selectStopTimemm\">\n");
        setTimeOption(out,Integer.parseInt(minute),0,60);
        out.write("                    </select>:\n" +
                "                    <select name=\"selectStopTimess\">\n");
        setTimeOption(out,Integer.parseInt(second),0,60);
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }
    /**
     * 表格填充归属
     * @param out 用来输入html的PrintWriter
     */
    void setBelong(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th>\n" +
                "                    归属:\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    <select name=\"belong\">\n");
        ArrayList arrayListToFaculty=new ArrayList();
        arrayListToFaculty.addAll(facultyService.getFacultyByManager(getLoginUser()));
        ArrayList arrayListToGrade=new ArrayList();
        ArrayList arrayListToClass=new ArrayList();
        String  subjectBelongString="";

        //找到所有学院
        for(int i=0;i<arrayListToFaculty.size();i++){
            Faculty faculty=(Faculty)arrayListToFaculty.get(i);
            subjectBelongString=faculty.getFacultyName();
            out.write("                        <option value=\"faculty-"+faculty.getId()+"\">"+subjectBelongString+"</option>\n");
            //每个学院下面的各个年级
            arrayListToGrade.addAll(gradeService.getGradeByFaculty(faculty));
            for(int j=0;j<arrayListToGrade.size();j++){
                Grade grade=(Grade)arrayListToGrade.get(j);
                subjectBelongString=faculty.getFacultyName()+" "+grade.getGradeName();
                out.write("                        <option value=\"grade-"+grade.getId()+"\">"+subjectBelongString+"</option>\n");

                //各个年级下的各个班级
                arrayListToClass.addAll(classService.getClassByGrade(grade));
                for(int k=0;k<arrayListToClass.size();k++){
                    Class classTemp=(Class)arrayListToClass.get(k);
                    subjectBelongString=faculty.getFacultyName()+" "+grade.getGradeName()+" "+classTemp.getClassName();
                    out.write("                        <option value=\"class-"+classTemp.getId()+"\">"+subjectBelongString+"</option>\n");
                }
                arrayListToClass.clear();
            }
            arrayListToGrade.clear();
        }
        out.write("                    </select>\n" +
                "                </th>\n" +
                "            </tr>\n");
    }//由control层给map过来 value（key） -
    /**
     * 表格填充提交键
     * @param out 用来输入html的PrintWriter
     */
    void setSubmit(PrintWriter out){
        out.write("            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                </th>\n" +
                "            </tr>\n"+
                "            <tr>\n" +
                "                <th colspan=\"2\">\n" +
                "                    <input type=\"submit\" value=\"添加课程\" onclick=\"return checkName()\">\n" +
                "                </th>\n" +
                "            </tr>\n");
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

    String title;

    private void setBaseTag(PrintWriter out){
        out.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>"+title+"</title>\n");
        setFunctions(out);
        out.write("</head>\n");
    }

    private void setFunctions(PrintWriter out) {
        setSelectFunction(out);
        setCheckNameFunction(out);
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
