import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private String producer;
    private double price;
    private Date ed;
    static Scanner buff = new Scanner(System.in);
    static final SimpleDateFormat SDFORM = new SimpleDateFormat("dd.MM.yyyy");
    public Product(int number) throws ParseException {
        System.out.print("\tFor product №"+ number + ": ");
        id = buff.nextInt(); name = buff.next();
        producer = buff.next(); price = buff.nextDouble();
        ed = SDFORM.parse(buff.next());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setEd(Date ed) {
        this.ed = ed;
    }

    public static Scanner getBuff() {
        return buff;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public double getPrice() {
        return price;
    }

    public Date getEd() {
        return ed;
    }
}
