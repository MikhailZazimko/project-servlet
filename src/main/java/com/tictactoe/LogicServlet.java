package com.tictactoe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession currentSession = request.getSession();
        Field field = extractField(currentSession);
        int index = getSelectedIndex(request);

        Sign currentSign = field.getField().get(index);
        if(Sign.EMPTY!=currentSign){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
            return;
        }
        field.getField().put(index,Sign.CROSS);
        int emptyFieldIndex = field.getEmptyFieldIndex();
        if(emptyFieldIndex>=0){
            field.getField().put(emptyFieldIndex,Sign.NOUGHT);
        }
        List<Sign> data = field.getFieldData();
        currentSession.setAttribute("field", field);
        currentSession.setAttribute("data",data);

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
