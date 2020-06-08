package com.zhuboyang.www.control;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author BIANG
 */
public class ControlUtil {
    static public void alert(String message, HttpServletResponse response,String... url) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out= response.getWriter();
        out.write("<script type='text/javascript'>alert('"+message+"');</script>");
        if(url[0]!=null) {
            out.write("<script type='text/javascript'>window.location.href=\""+url[0]+"\"</script>");
        }
        out.flush();
        out.close();
    }

    /**
     * 注销方法（未使用）
     * @param out PrintWriter
     */
    static public void logoutFunction(PrintWriter out){
        out.write("<script type=\"text/javascript\">\n" +
                "    window.onbeforeunload = function (e) {\n" +
                "        if (event.clientX>document.body.clientWidth && event.clientY<0||event.altKey){\n" +
                "            window.open('logout','',\n" +
                "                'height=0,width=0,top=10000,left=10000');\n" +
                "        }\n" +
                "    }\n" +
                "</script>");
    }
}
