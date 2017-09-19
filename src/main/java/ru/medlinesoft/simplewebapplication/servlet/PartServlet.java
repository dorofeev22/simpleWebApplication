package ru.medlinesoft.simplewebapplication.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.medlinesoft.simplewebapplication.dto.PartDto;
import ru.medlinesoft.simplewebapplication.service.PartService;

/**
 * Сервлет для получения данных Part из базы.
 */
@WebServlet(urlPatterns = {"/"})
public class PartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String order = request.getParameter("order");
        List<PartDto> parts = new ArrayList<>();
        try {
            parts = new PartService().findDtoParts(order);
        } catch (ClassNotFoundException ex) {
            request.setAttribute( "error", ex.getMessage());
        }
        request.setAttribute("parts", parts);
        request.getRequestDispatcher("/WEB-INF/parts.jsp").forward(request, response);
    }
    
}
