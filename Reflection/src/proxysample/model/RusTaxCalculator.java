package proxysample.model;

public class RusTaxCalculator implements TaxCalculator {
    @Override
    public double getTax(double incoming) {
        return incoming * 0.13;
    }
}
