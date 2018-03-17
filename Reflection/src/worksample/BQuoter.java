package worksample;

public class BQuoter implements Quoter {
    @Override
    @ShutupTimeout(timeout = 500)
    public void sayQuote() {
        System.out.println("/Autism");
    }
}
