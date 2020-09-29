import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/Details")
public class information extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        int amount = Integer.parseInt(req.getParameter("amount"));
        String lastName = "";
        String firstName;
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }
        HttpSession session = req.getSession();
        session.setAttribute("name", name);
        session.setAttribute("firstname",firstName);
        session.setAttribute("lastname",lastName);
        session.setAttribute("email", email);
        session.setAttribute("amount", amount);
        RequestDispatcher rs = req.getRequestDispatcher("Authorize");
        rs.forward(req,res);
    }
}
