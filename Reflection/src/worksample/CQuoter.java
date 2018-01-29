package worksample;

public class CQuoter implements Quoter {
    @Override
    @ShutupTimeout(timeout = 1000)
    public void sayQuote() {
        System.out.println("Nothing");
    }
}
