package ru.mail.server.servlets;

import ru.mail.server.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static ru.mail.server.ReadWriteProducts.writeData;

@SuppressWarnings("serial")
public class PostProduct extends HttpServlet {
    public PostProduct() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            final String name = request.getParameter("name");
            final String manufacturer = request.getParameter("manufacturer");
            final int amount = parseInt(request.getParameter("amount"));
            writeData(new Product(name, manufacturer, amount));
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } finally {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h1>File sent</h1>\n" +
                    "<p><a href=\"http://127.0.0.1:3466/\">Turn back on main<p>\n"
            );
        }
    }
}




