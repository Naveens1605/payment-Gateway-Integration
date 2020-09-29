public class OrderDetail {
    private String productName;
    private float amount;
    private float tax;
    private float total;

    public OrderDetail(String productName, String amount,
                        String tax, String total) {
        this.productName = productName;
        this.amount = Float.parseFloat(amount);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }

    public String getProductName() {
        return productName;
    }

    public String getAmount() {
        return String.format("%.2f", amount);
    }


    public String getTax() {
        return String.format("%.2f", tax);
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }
}
