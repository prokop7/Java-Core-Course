package proxysample;

import proxysample.model.RusTaxCalculator;
import proxysample.model.TaxCalculator;
import proxysample.model.UsaTaxCalculator;
import proxysample.utils.TaxInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        double summ = random.nextDouble() * 100_000;
//        RusTaxCalculator rusTax = new RusTaxCalculator();
        UsaTaxCalculator usaTax = new UsaTaxCalculator();
        TaxCalculator proxyCalculator = (TaxCalculator) Proxy.newProxyInstance(
                TaxCalculator.class.getClassLoader(),
                new Class[]{TaxCalculator.class},
                new TaxInvocationHandler(new RusTaxCalculator()));

        while (true) {
            if (summ < 50_000)
                System.out.printf("Rus: %s%n", proxyCalculator.getTax(summ));
            else
                System.out.printf("Usa: %s%n", usaTax.getTax(summ));
            summ = random.nextDouble() * 100_000;
        }
    }
}
