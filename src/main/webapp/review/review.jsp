<%--
  Created by IntelliJ IDEA.
  User: Naveen
  Date: 17-09-2020
  Time: 02:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review</title>
    <style type="text/css">
        table { border: 0; }
        table td { padding: 5px; }
    </style>
</head>
<body>
<div align="center">
    <h1>
        Transaction is Processing. Please wait for <span id="countdown">10</span> seconds
    </h1>
    <h2>Payment Description</h2>
    <form name="payment" action="execute" method="post">
        <table>
            <tr>
                <td colspan="2"><b>Transaction Details:</b></td>
                <td>
                    <input type="hidden" name="paymentId" value="${param.paymentId}" />
                    <input type="hidden" name="PayerID" value="${param.PayerID}" />
                </td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>${transaction.description}</td>
            </tr>
            <tr>
                <td>Subtotal:</td>
                <td>₹${transaction.amount.details.subtotal} INR</td>
            </tr>
            <tr>
                <td>Tax:</td>
                <td>₹${transaction.amount.details.tax} INR</td>
            </tr>
            <tr>
                <td>Total:</td>
                <td>₹${transaction.amount.total} INR</td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
                <td colspan="2"><b>Payer Information:</b></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td>${payer.firstName}</td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>${payer.lastName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${payer.email}</td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
                <td colspan="2"><b>Recipient Address:</b></td>
            </tr>
            <tr>
                <td>Recipient Name:</td>
                <td>${shippingAddress.recipientName}</td>
            </tr>
            <tr>
                <td>Line 1:</td>
                <td>${shippingAddress.line1}</td>
            </tr>
            <tr>
                <td>City:</td>
                <td>${shippingAddress.city}</td>
            </tr>
            <tr>
                <td>State:</td>
                <td>${shippingAddress.state}</td>
            </tr>
            <tr>
                <td>Country Code:</td>
                <td>${shippingAddress.countryCode}</td>
            </tr>
            <tr>
                <td>Postal Code:</td>
                <td>${shippingAddress.postalCode}</td>
            </tr>
        </table>
    </form>
    <script type="text/javascript">
        window.onload=function(){
            var auto = setTimeout(function(){ autoRefresh(); }, 100);

            function submitform(){
                document.forms["payment"].submit();
            }

            function autoRefresh(){
                clearTimeout(auto);
                auto = setTimeout(function(){ submitform(); autoRefresh(); }, 10000);
            }
        }
        let seconds = 10;
        function countdown() {
            seconds = seconds - 1;
            if (seconds < 0) {

            } else {
                document.getElementById("countdown").innerHTML = seconds;
                window.setTimeout("countdown()", 1000);
            }
        }
        countdown();
    </script>
</div>
</body>
</html>