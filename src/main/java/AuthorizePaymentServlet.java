import com.paypal.base.rest.PayPalRESTException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/Authorize")
public class AuthorizePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthorizePaymentServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String product = "Donation";
        String amount = request.getParameter("amount");
        String tax = "0";
        String total = amount;
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Pattern pattern1 = Pattern.compile("[A-Za-z]");
        Matcher match = pattern.matcher(amount);
        Matcher match1 = pattern1.matcher(amount);
        boolean val = match.find();
        boolean var = match1.find();
        if(val || var || Integer.parseInt(amount)==0) {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            out.println("<html><Head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<title>View Details</title>");
            out.println("<style>");
            out.println("h1 {");
            out.println("text-align: center;");
            out.println("color:blue;");
            out.println("font-family: verdana;\n" +
                    "  font-size: 300%;");
            out.println("}");
            out.println(".button {\n" +
                    "  background-color: red;\n" +
                    "  border: none;\n" +
                    "  color: white;\n" +
                    "  padding: 15px 32px;\n" +
                    "  text-align: center;\n" +
                    "  align: center;\n" +
                    "  text-decoration: none;\n" +
                    "  display: inline-block;\n" +
                    "  font-size: 16px;\n" +
                    "  margin: 0px 655px;\n" +
                    "  cursor: pointer;\n" +
                    "}");
            out.println(".button:hover {\n" +
                    "  background-color: maroon;\n" +
                    "}");
            out.println("</style>");
            out.println("<h1>Donation Amount can't be " + amount + " !!!</h1></head></html>");
            out.println("<a href='form/form.html' class='button'>Go Back</a >");
        }
        else {
            OrderDetail orderDetail = new OrderDetail(product, amount, tax, total);

            try {
                PaymentServices paymentServices = new PaymentServices();
                String approvalLink = paymentServices.authorizePayment(orderDetail);

                response.sendRedirect(approvalLink);

            } catch (PayPalRESTException ex) {
                request.setAttribute("errorMessage", ex.getMessage());
                ex.printStackTrace();
                request.getRequestDispatcher("error/error.jsp").forward(request, response);
            }
        }

    }
}
