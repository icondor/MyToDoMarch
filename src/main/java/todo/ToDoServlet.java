package todo;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@WebServlet("/manageMyToList")
public class ToDoServlet extends HttpServlet {

    List toDo = new ArrayList<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        String action = req.getParameter("action");
        System.out.println("action is:" + action);
        if (action != null && action.equalsIgnoreCase("NEW")) {

            addToDo(req);
        } else if (action != null && action.equalsIgnoreCase("LIST")) {
            //afisare
            listAllMyToDo(resp);
        } else if (action != null && action.equalsIgnoreCase("DELETE")) {
            deleteAll(resp);
        } else {
            System.out.println("nu a venit action, deci nu fac nimic ");
            error(resp);
        }

    }

    private void deleteAll(HttpServletResponse resp) {
        toDo.clear();

        listAllMyToDo(resp);
    }

    private void listAllMyToDo(HttpServletResponse resp) {



            JSONObject json = new JSONObject();
            json.put("listFromBackend", toDo); // pun colectia pe cheia asta , asa o voi folosi din ui

            String result = json.toString();
            returnJsonResponse(resp, result); // trimit jsonul convertit la string spre print writer



    }

    private void addToDo(HttpServletRequest req) {
        System.out.println("intra pe serverrrrrr  ");
        String name = req.getParameter("nume");
        System.out.println(name);
        toDo.add(name);
    }

    private void error(HttpServletResponse resp) {
        try {
            PrintWriter pw = resp.getWriter();
            pw.println("ERROR");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
