import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static Scanner buff = new Scanner(System.in);
    static final SimpleDateFormat SDFORM = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) throws ParseException {
        System.out.print("\n\tEnter number of products: ");
        int numb = buff.nextInt();

        List<Product> products = new ArrayList<>();

        System.out.println("\n\tSet data in order: ID, Name, Producer, Price, Expiration_date");
        for(int i = 1; i <= numb; i++){
            Product tempElem = new Product(i);
            products.add(tempElem);
        }

        showByProducer(products);
        showByProducerAndPrice(products);
        showByDate(products);
    }

    static void showByProducer(List<Product> arr){
        System.out.print("\n\tSort by producer: ");
        String tempName = buff.next();
        System.out.print("\tList:");
        for(Product i: arr){
            if((i.getProducer()).equals(tempName)) {
                System.out.print(" " + i.getName());
            }
        }
    }

    static void showByProducerAndPrice(List<Product> arr){
        System.out.print("\n\n\tSort by producer and price: ");
        String tempName = buff.next();
        System.out.print("\tMax price of product: ");
        int tempPrice = buff.nextInt();

        System.out.print("\tList:");
        for(Product i: arr){
            if((i.getProducer()).equals(tempName) && i.getPrice() <= tempPrice ) {
                System.out.print(" " + i.getName());
            }
        }
    }

    static void showByDate(List<Product> arr) throws ParseException {
        System.out.print("\n\n\tSort by lowest expiration date: ");
        Date temp_date = SDFORM.parse(buff.next());
        System.out.print("\tList:");
        for(Product i: arr){
            if((i.getEd()).compareTo(temp_date) > 0 ) {
                System.out.print(" " + i.getName());
            }
        }
    }
}