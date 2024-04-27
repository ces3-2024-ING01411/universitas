package co.edu.poli.ces3.universitas.servlet;

import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionMySql;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private ConexionMySql cnn;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    public void init() {
        cnn = new ConexionMySql();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>PUT</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>POST</h1>");
        out.println("</body></html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            ArrayList<User> listUsers = (ArrayList<User>)cnn.getUsers();
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>GET</h1>");

            out.print(
                    "<table>" +
                            "<thead>" +
                            "<tr>" +
                            "<th>Nombre</th>" +
                            "<th>Apellido</th>" +
                            "</thead>" +
                            "<tbody>"
            );

            for (User u: listUsers) {
                out.print(
                        "<tr>" +
                                "<td>"+u.getName()+"</td>" +
                                "<td>"+u.getLastName()+"</td>" +
                                "</tr>"
                );
            }
            out.print("</tbody></table>");
            out.println("</body></html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void destroy() {
    }
}