package worksample;

public class AQuoter implements Quoter{
    @Override
    @ShutupTimeout(timeout = 2000)
    public void sayQuote() {
        System.out.println("Drop!");
    }

}
