import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentServices extends HttpServlet {
    private void user(HttpServletRequest request,HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        String firstname = (String) session.getAttribute("firstname");
        String lastname = (String) session.getAttribute("lastname");
        String email = (String) session.getAttribute("email");
        getPayerInformation(firstname,lastname,email);
    }
        private static final String CLIENT_ID = "AYfbb6GdCP2eu9Xvoe-fsmTmGtL-rmr5SLs1r5Mv5a3e6vc6B_cvUd4TIZl1LBqwqJ9MpUw3c7Nk0YIo";
        private static final String CLIENT_SECRET = "ELL_OjsSi4KAPwlBCIJWnwkkj72ByrLHNjzMC45ZviSTlJzAW07_9AlbJqHlT1w0yYy8u4-1-4ibqZSJ";
        private static final String MODE = "sandbox";

        public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException {

            Payer payer = getPayerInformation("","","");
            RedirectUrls redirectUrls = getRedirectURLs();
            List<Transaction> listTransaction = getTransactionInformation(orderDetail);

            Payment requestPayment = new Payment();
            requestPayment.setTransactions(listTransaction);
            requestPayment.setRedirectUrls(redirectUrls);
            requestPayment.setPayer(payer);
            requestPayment.setIntent("authorize");

            APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

            Payment approvedPayment = requestPayment.create(apiContext);

            return getApprovalLink(approvedPayment);

        }
        private Payer getPayerInformation(String firstname,String lastname,String email) {

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            PayerInfo payerInfo = new PayerInfo();
            payerInfo.setFirstName(firstname)
                    .setLastName(lastname)
                    .setEmail(email);
            payer.setPayerInfo(payerInfo);
            return payer;
        }

        private RedirectUrls getRedirectURLs() {
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://paypalgateway.us-east-2.elasticbeanstalk.com/index.html");
            redirectUrls.setReturnUrl("http://paypalgateway.us-east-2.elasticbeanstalk.com/review");
            return redirectUrls;
        }

        private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
            Details details = new Details();
            details.setSubtotal(orderDetail.getAmount());
            details.setTax(orderDetail.getTax());
            Amount amount = new Amount();
            amount.setCurrency("INR");
            amount.setTotal(orderDetail.getTotal());
            amount.setDetails(details);
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(orderDetail.getProductName());
            ItemList itemList = new ItemList();
            List<Item> items = new ArrayList<>();
            Item item = new Item();
            item.setCurrency("INR");
            item.setName(orderDetail.getProductName());
            item.setPrice(orderDetail.getAmount());
            item.setTax(orderDetail.getTax());
            item.setQuantity("1");
            items.add(item);
            itemList.setItems(items);
            transaction.setItemList(itemList);
            List<Transaction> listTransaction = new ArrayList<>();
            listTransaction.add(transaction);
            return listTransaction;
        }

        private String getApprovalLink(Payment approvedPayment) {
            List<Links> links = approvedPayment.getLinks();
            String approvalLink = null;

            for (Links link : links) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    approvalLink = link.getHref();
                    break;
                }
            }
            return approvalLink;
        }
    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException{
            APIContext apiContext = new APIContext(CLIENT_ID,CLIENT_SECRET,MODE);
            return Payment.get(apiContext,paymentId);
    }
}


