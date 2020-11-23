package ru.mail.server.servlets;

import ru.mail.server.ReadWriteProducts;
import ru.mail.server.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class GetProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String responseSrt = "<style>\n" +
                "        table {\n" +
                "            margin: 0 auto;\n" +
                "            font-size: large;\n" +
                "            border: 1px solid black;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            text-align: center;\n" +
                "            color: #006600;\n" +
                "            font-size: xx-large;\n" +
                "            font-family: 'Gill Sans',\n" +
                "            'Gill Sans MT', ' Calibri',\n" +
                "            'Trebuchet MS', 'sans-serif';\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            background-color: #E4F5D4;\n" +
                "            border: 1px solid black;\n" +
                "        }\n" +
                "\n" +
                "        th,\n" +
                "        td {\n" +
                "            font-weight: bold;\n" +
                "            border: 1px solid black;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            font-weight: lighter;\n" +
                "        }\n" +
                "    </style>";

        responseSrt += " <h1>Products</h1>\n" +
                "    <!-- TABLE CONSTRUCTION-->\n" +
                "    <table id='table'>\n" +
                "        <!-- HEADING FORMATION -->\n" +
                "        <tr>\n" +
                "            <th>Name</th>\n" +
                "            <th>Manufacturer</th>\n" +
                "            <th>Amount</th>\n" +
                "        </tr>\n" + responseTable()
                +
                "    </table>";
        try {
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } finally {

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(responseSrt);
        }
    }

    private String responseTable() {
        ArrayList<Product> arrayList = ReadWriteProducts.getData();

        String result = "";
        for (Product p : arrayList) {
            result += ("<tr>\n" +
                    "<td>" + p.getName() + "</td>\n" +
                    "<td>" + p.getManufacturer() + "</td>\n" +
                    "<td>" + p.getAmount() + "</td>\n" +
                    "</tr>");

        }
        return result;
    }


}



