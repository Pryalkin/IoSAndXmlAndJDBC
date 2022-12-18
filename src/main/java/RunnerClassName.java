import factory.Factory;
import model.DiscountCard;
import model.Product;
import model.Receipt;
import repository.Repository;
import service.Service;

public class RunnerClassName {

    public static void main(String[] args) {
        Repository repository = Factory.getRepository();
        createProducts(repository);
        createDiscountCard(repository);
        Service service = Factory.getService();
        if (args.length == 0){
            System.out.println("Запрос пустой!");
        } else {
            Receipt receipt = service.parseTheRequest(args);
            System.out.println("--------------------------------------------------------------");
            System.out.println("DATE: " + receipt.getCreateDate());
            String qty = "QTY |";
            String description = "DESCRIPTION   |";
            String price = "PRICE |";
            String sum = "SUM   |";
            String auction = "AUCTION |";
            String discount = "DISCOUNT  |";
            String total = "TOTAL ";
            String line = qty + description + price + sum + auction + discount + total;
            System.out.println(line);
            receipt.getProductWarehouses().stream().forEach(pw -> {
                StringBuilder sb = new StringBuilder();
                double s = 0.0;

                String testLine = String.valueOf(pw.getAmount());
                int i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < qty.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                testLine = pw.getProduct().getName();
                i = testLine.length()/2;
                sb.append(testLine);
                for (int j = 0; j < description.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                testLine =  String.valueOf(pw.getProduct().getPrice());
                i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < price.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                s = pw.getAmount()*pw.getProduct().getPrice();
                testLine =  String.valueOf(s);
                i = testLine.length();
                sb.append(testLine);
                for (int j = 0; j < sum.length()-i-1; j++) {
                    sb.append(" ");
                }
                sb.append("|");

                if (pw.getProduct().getAuction()){
                    sb.append("Акция   ");
                } else {
                    sb.append("        ");
                }
                sb.append("|");

                if (pw.getProduct().getAuction() && pw.getAmount()>5){
                    sb.append("-10%      ");
                    s = s * 0.9;
                } else sb.append("          ");
                sb.append("|");

                sb.append(String.format("%.2f", s));

                System.out.println(sb);
            });
            System.out.println("--------------------------------------------------------------");
            double t = receipt.getProductWarehouses().stream()
                    .mapToDouble(pw -> {
                        if (pw.getProduct().getAuction()){
                            return pw.getAmount()*pw.getProduct().getPrice()*0.9;
                        }
                        return pw.getAmount()*pw.getProduct().getPrice();
                    })
                    .sum();
            System.out.println("Общая сумма: " + String.format("%.2f", t));
            if (receipt.getDiscountCard() != null){
                System.out.println("Была предъявлена скидочная карта : " + receipt.getDiscountCard().getNumber());
                System.out.println("К ОПЛАТЕ: " +
                                           String.format("%.2f",
                                                         (t * ((100 - receipt.getDiscountCard().getDiscount())/100))));
            } else System.out.println("К ОПЛАТЕ: " + String.format("%.2f", t));
        }

    }

    private static void createDiscountCard(Repository repository) {
        DiscountCard discountCard = new DiscountCard("1234", 5.0);
        discountCard = repository.saveDiscountCard(discountCard);
        discountCard = new DiscountCard("1254", 7.0);
        discountCard = repository.saveDiscountCard(discountCard);
        discountCard = new DiscountCard("1634", 10.3);
        discountCard = repository.saveDiscountCard(discountCard);
    }

    private static void createProducts(Repository repository) {
        Product product = new Product("Шоколад", 2.34);
        product = repository.saveProduct(product);
        product = new Product("Макороны", 3.35);
        product.setAuction(true);
        product = repository.saveProduct(product);
        product = new Product("Гречка", 4.19);
        product.setAuction(true);
        product = repository.saveProduct(product);
        product = new Product("Молоко", 1.62);
        product = repository.saveProduct(product);
        product = new Product("Хлеб", 2.09);
        product = repository.saveProduct(product);
        product = new Product("Масло", 4.11);
        product = repository.saveProduct(product);
        product = new Product("Мороженое", 2.27);
        product.setAuction(true);
        product = repository.saveProduct(product);
    }
}
