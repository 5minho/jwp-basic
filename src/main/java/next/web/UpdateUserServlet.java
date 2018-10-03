package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 10. 3..
 */

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Optional.ofNullable(req.getParameter("id"))
                .map(DataBase::findUserById)
                .ifPresent(user -> forwardUpdateUser(req, resp, user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        try {
            DataBase.update(new User(userId, password, name, email));
            Collection<User> users = DataBase.findAll();
            req.setAttribute("users", users);
            RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void forwardUpdateUser(HttpServletRequest req, HttpServletResponse resp, User user) {
        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/update.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
