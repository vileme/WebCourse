package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private static String generateForm(Integer captchaData) {
        String encodedImage = Base64.getEncoder().encodeToString(ImageUtils.toPng(captchaData.toString()));
        String[] parts = {"<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<body>\n" +
                "  <div class=\"captcha-form\">\n" +
                "    <img src=\"data:image/png;base64, ",

                "\">\n      <form class=\"captcha-form\" method=\"get\" action=\"",

                "\">\n      <label for=\"captcha__answer\">Enter number:</label>\n" +
                        "       <input name=\"captcha\" id=\"captcha_answer\">\n" +
                        "       </form>\n" +
                        "       </div>\n" +
                        "</body>\n" +
                        "</html>"};
        return parts[0] + encodedImage + parts[1] + parts[2];
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(session.getAttribute("check")!=null){
            chain.doFilter(request,response);
        }
        else if((request.getParameter("captcha") == null
                || !request.getParameter("captcha").equals(session.getAttribute("randomValue").toString()))) {
            int randomValue = new Random().nextInt(900) + 100;
            session.setAttribute("randomValue", randomValue);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(generateForm(randomValue).getBytes());
        } else {
            session.setAttribute("check","authorized");
            chain.doFilter(request,response);
        }
    }
}
