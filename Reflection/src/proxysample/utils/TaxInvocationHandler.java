package proxysample.utils;

import proxysample.model.TaxCalculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TaxInvocationHandler implements InvocationHandler {
    private TaxCalculator taxCalculator;

    public TaxInvocationHandler(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(taxCalculator, args);
        System.out.printf("%s costs %d%n", method.getName(), System.currentTimeMillis() - startTime);
        return result;
    }
}
