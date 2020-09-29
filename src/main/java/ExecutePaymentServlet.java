import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/execute")
public class ExecutePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExecutePaymentServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId =  request.getParameter("PayerID");

        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.executePayment(paymentId, payerId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);

            request.getRequestDispatcher("receipt/receipt.jsp").forward(request, response);


            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("name");
            String email = (String) session.getAttribute("email");
            int amount = (int) session.getAttribute("amount");
            String subject = "Regarding the Donation of Amount " + amount;
            String content = "Hi," + name + "\n" +
                    "This Mail is regarding your donation of Rs " + amount + " on " +
                    "our website for the Public Care. We appreciate your initiative. Your invoice is below.\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Payment Receipt</title>\n" +
                    "    <style type=\"text/css\">\n" +
                    "        table { border: 0; }\n" +
                    "        table td { padding: 5px; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div align=\"center\">\n" +
                    "    <h1>Invoice of Donation</h1>\n" +
                    "    <br/>\n" +
                    "    <h2>Receipt Details:</h2>\n" +
                    "    <table>\n" +
                    "        <tr>\n" +
                    "            <td><b>Merchant:</b></td>\n" +
                    "            <td>Donation NGO</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td><b>Payer:</b></td>\n" +
                    "            <td>"+ payerInfo.getFirstName() + payerInfo.getLastName()  +"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td><b>Description:</b></td>\n" +
                    "            <td> "+ transaction.getDescription() + "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td><b>Subtotal:</b></td>\n" +
                    "            <td> Rs " + transaction.getAmount().getDetails().getSubtotal() + " INR</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td><b>Tax:</b></td>\n" +
                    "            <td> Rs "+ transaction.getAmount().getDetails().getTax()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td><b>Total:</b></td>\n" +
                    "            <td> Rs  " + transaction.getAmount().getTotal() + " INR</td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";

            String user = "pitamahbhism16@gmail.com";
            String pass = "8896513483";
            PrintWriter out = response.getWriter();
            try {
                Mail.sendEmail(user, pass, email, subject, content);
                out.println("The E-mail was sent Successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
                out.println("There were an error: "  + ex.getMessage());
            }


        } catch (PayPalRESTException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            request.getRequestDispatcher("error/error.jsp").forward(request, response);
        }
    }
}
