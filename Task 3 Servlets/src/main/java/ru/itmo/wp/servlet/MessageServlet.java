package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class MessageServlet extends HttpServlet {
    private JsonArray arrayText = new JsonArray();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri=request.getRequestURI();
        HttpSession session = request.getSession(true);
        response.setContentType("application/json");
        switch(uri){
            case "/message/auth": {
                auth(request, response, session);
                break;
            }
            case "/message/findAll":
                findAll(response);
                break;
            case "/message/add":
                add(request,session);
                break;
        }
    }
    private void auth(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String user = request.getParameter("user");
        if(user!=null) {
            session.setAttribute("user", user);
        }
        else user="";
        response.getWriter().print(new Gson().toJson(user));
        response.getWriter().flush();
    }
    private void add(HttpServletRequest request,HttpSession session){
        String text = request.getParameter("text");
        String  current = session.getAttribute("user").toString()   ;
        JsonObject object=new JsonObject();
        object.addProperty("user",current);
        object.addProperty("text",text);
        arrayText.add(object);
    }
    private void findAll(HttpServletResponse response)throws IOException{
        response.getWriter().print(arrayText);
        response.getWriter().flush();
    }
}
