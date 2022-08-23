package com.tictactoe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int index = getSelectedIndex(request);
        response.sendRedirect("/index.jsp");
    }
    private Field extractField(HttpSession currentSession){
        Object fieldAttribute = currentSession.getAttribute("field");
        if(Field.class!=fieldAttribute.getClass()){
            currentSession.invalidate();
            throw  new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAttribute;
    }


    private int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

}
