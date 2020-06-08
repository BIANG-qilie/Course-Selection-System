package com.zhuboyang.www.control;

import com.zhuboyang.www.po.Faculty;
import com.zhuboyang.www.po.Grade;
import com.zhuboyang.www.po.Subject;
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
 * @author BIANG
 */
public class ManagerChangeServlet extends HttpServlet {
    FacultyService facultyService=new FacultyServiceImpl();
    UserService userService=new UserServiceImpl();
    GradeService gradeService=new GradeServiceImpl();
    ClassService classService=new ClassServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        ArrayList arrayList=new ArrayList();
        arrayList.addAll(facultyService.getAllFaculty());
        int arraySize=arrayList.size();
        for(int i=0;i<arraySize;i++){
            Faculty faculty = (Faculty) arrayList.get(i);
            int managerUserId=Integer.parseInt(request.getParameter("faculty-"+faculty.getId()));
            facultyService.updateManagerUserId(faculty.getId(),managerUserId);
        }
        ControlUtil.alert("编辑成功",response,"managerManage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out=response.getWriter();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\"60\" cellspacing=\"0\" id=\"tableTitle\">\n" +
                "        <tr>\n" +
                "            <th colspan=\"8\">\n" +
                "                <font color=\"blue\" face=\"楷体\" size=\"7\">管理员编辑</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "    </table>");
        fillTable(out);
        returnToManagerManageFunction(out);

    }

    /**
     * 填充表格
     * @param out PrintWriter
     */
    private void fillTable(PrintWriter out) {
        ArrayList arrayList=facultyService.getAllFaculty();
        int arraySize=arrayList.size();
        out.write("<table align=\"center\" border=\"1\" width=\"800\" height=\""+(90+arraySize*30)+"\" cellspacing=\"0\" id=\"table01\">\n" +
                "        <tr>\n" +
                "            <th>学院id</th>\n" +
                "            <th>学 院 名</th>\n" +
                "            <th>年 级 数</th>\n" +
                "            <th>班 级 数</th>\n" +
                "            <th>管 理 员</th>\n" +
                "        </tr>\n" +
                "        <tr>              <th colspan=\"8\">共找到"+arraySize+"个学院</th>          </tr>"+
                "<form action=\"changeManager\" method=\"post\">");
        ArrayList arrayListOfManager=new ArrayList();
        arrayListOfManager.addAll(userService.getUserByLevel(2));
        int arraySizeOfManager=arrayListOfManager.size();
        for(int i=0;i<arraySize;i++){
            Faculty faculty = (Faculty) arrayList.get(i);
            out.write("      <tr>\n" +
                    "        <td align=\"center\">"+faculty.getId()+"</td>\n" +
                    "        <td align=\"center\">"+faculty.getFacultyName()+"</td>\n" +
                    "        <td align=\"center\">"+getGradeNumber(faculty)+"</td>\n" +
                    "        <td align=\"center\">"+getClassNumber(faculty)+"</td>\n" +
                    "        <td align=\"center\">");
            String facultyId="faculty-"+faculty.getId();
            out.write("<select name=\""+facultyId+"\">\n");
            User managerSelected=userService.getUserByUserId(faculty.getManagerUserId());
            out.write("                    <option value=\""+managerSelected.getId()+"\">\n" +
                    managerSelected.getUsername() +"\n"+
                    "                    </option>\n");
            for(int j=0;j<arraySizeOfManager;j++){
                User manager=(User)arrayListOfManager.get(j);
                if(managerSelected.getUsername().equals(manager.getUsername())) {
                    continue;
                }
                out.write("                    <option value=\""+manager.getId()+"\">\n" +
                        manager.getUsername() +"\n"+
                        "                    </option>\n");
            }
            out.write("                </select>");
            out.write("</td>\n" +
                    "    </tr>\n");
        }
        out.write("<tr>\n" +
                "            <th></th>\n" +
                "            <th>\n"+
                "                    <input type=\"submit\" value=\"完成编辑\" />\n" +
                "            </th>\n" +
                "       </form>"+
                "            <th></th>\n" +
                "            <th><button onclick=\"returnToManagerManage()\">返回</button></th>\n" +
                "            <th></th>\n"+
                "        </tr>\n" +
                "    </table>" );
    }
    void returnToManagerManageFunction(PrintWriter out){
        out.write("<script>\n" +
                "            function returnToManagerManage() {\n" +
                "       window.location.href=\"managerManage\";\n"+
                "       }\n" +
                "        </script>\n");
    };
    /**
     * 获取一个学院下面有几个班级
     * @param faculty 查询的学院
     * @return 有几个班级
     */
    private int getClassNumber(Faculty faculty) {
        ArrayList arrayListOfGrade=new ArrayList();
        ArrayList arrayListOfClass=new ArrayList();
        arrayListOfGrade.addAll(gradeService.getGradeByFaculty(faculty));
        for(int i=0;i<arrayListOfGrade.size();i++){
            Grade grade= (Grade) arrayListOfGrade.get(i);
            arrayListOfClass.addAll(classService.getClassByGrade(grade));
        }
        return arrayListOfClass.size();
    }
    /**
     * 获取一个学院下面有几个年级
     * @param faculty 查询的学院
     * @return 有几个年级
     */
    private int getGradeNumber(Faculty faculty) {
        return gradeService.getGradeByFaculty(faculty).size();
    }
}
