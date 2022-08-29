import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner buffer = new Scanner(System.in);
        Fibonacci Data = new Fibonacci();

        System.out.println("\n\tCheck formula for Fn, Fm and Fk Fibonacci numbers! ");

        System.out.print("Enter n, m, k: ");
        int N = buffer.nextInt();
        int M = buffer.nextInt();
        int K = buffer.nextInt(); buffer.nextLine();

        System.out.println("Result for formula: "+Data.chek(N, M, K));
        buffer.close();

    }
}