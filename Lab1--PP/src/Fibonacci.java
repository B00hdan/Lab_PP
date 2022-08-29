public class Fibonacci {
    static final double SQRT5 = Math.sqrt(5);
    static final double GR = (SQRT5 + 1) / 2;
    public boolean chek(int n, int m, int k) {
        long func1;
        long func2;
        if (m < k) {
            System.out.println("m can't be lover then k");
            return false;
        }
        func1 = fibo(m) * fibo((n + k)) - fibo(k) * fibo((n + m));
        func2 = (long) Math.pow(-1, k)* fibo((m - k)) * fibo(n);
        System.out.println("Fm*F(n+k)-Fk*F(n+m) = " + func1);
        System.out.println("(-1)^k*F(m-k)*Fn = " + func2);
        return func1 == func2;
    }
    public long fibo(int n) {
        return (long) (Math.pow(GR, n) / SQRT5 + 0.5);
    }
}
