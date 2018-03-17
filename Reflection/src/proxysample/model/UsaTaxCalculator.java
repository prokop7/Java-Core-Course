package proxysample.model;

public class UsaTaxCalculator implements TaxCalculator {
    @Override
    public double getTax(double incoming) {
        return incoming * 0.2;
    }
}
