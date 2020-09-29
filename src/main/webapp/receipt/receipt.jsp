<%--
  Created by IntelliJ IDEA.
  User: Naveen
  Date: 17-09-2020
  Time: 02:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt</title>
    <style type="text/css">
        table { border: 0; }
        table td { padding: 5px; }
    </style>
</head>
<body>
<div align="center">
    <h1>Transaction Successful! Thank You for Donation.</h1>
    <br/>
    <h2>Receipt Details:</h2>
    <table>
        <tr>
            <td><b>Merchant:</b></td>
            <td>Donation NGO</td>
        </tr>
        <tr>
            <td><b>Payer:</b></td>
            <td>${payer.firstName} ${payer.lastName}</td>
        </tr>
        <tr>
            <td><b>Description:</b></td>
            <td>${transaction.description}</td>
        </tr>
        <tr>
            <td><b>Subtotal:</b></td>
            <td>₹ ${transaction.amount.details.subtotal} INR</td>
        </tr>
        <tr>
            <td><b>Tax:</b></td>
            <td>₹ ${transaction.amount.details.tax} INR</td>
        </tr>
        <tr>
            <td><b>Total:</b></td>
            <td>₹ ${transaction.amount.total} INR</td>
        </tr>
    </table>
</div>
</body>
</html>
