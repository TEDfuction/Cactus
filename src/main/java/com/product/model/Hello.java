package com.product.model;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Hello extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
	    req.setCharacterEncoding("Big5");
        res.setContentType("text/html; charset=Big5");
        PrintWriter out = res.getWriter();

        String name = req.getParameter("name");
        
        System.out.println(name==null);
        System.out.println("--------------------");
        
        if(name!=null) {
        	System.out.println(name.trim().length());
            System.out.println(name.trim().isEmpty());
            System.out.println(name.trim().equals(""));
        }
        
        
        out.println("<HTML>");
        out.println("<HEAD><TITLE>Hello, " + name + "</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("Hello, 你好: " + name);
        out.println("</BODY></HTML>");
  }
  
  public String getServletInfo() {
    return "A servlet that knows the name of the person to whom it's" + 
           "saying hello";
  }
}
