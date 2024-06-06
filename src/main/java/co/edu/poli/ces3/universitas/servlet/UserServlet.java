package co.edu.poli.ces3.universitas.servlet;

import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionMySql;
import co.edu.poli.ces3.universitas.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import sun.jvm.hotspot.utilities.IntegerEnum;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends MyServlet {
    private UserRepository repository;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    public void init() {
        repository = new UserRepository();
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
        response.setContentType("application/json");

        // Hello
        PrintWriter out = response.getWriter();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            if(request.getParameter("id") == null) {
                ArrayList<User> listUsers = (ArrayList<User>) repository.get();
                out.print(gson.toJson(listUsers));
            }else{
                User user = repository.getOne(Integer.parseInt(request.getParameter("id")));
                out.print(gson.toJson(user));
            }
            out.flush();
        } catch (SQLException e) {
            System.out.println("error");
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.doPatch(req, resp);
    }

    public void destroy() {
    }

    @Override
    void saludar() {

    }
}