package services;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {
    @Test
    public void isInvalid() {
        Assert.assertTrue(Validator.isInvalid("123%"));
        Assert.assertTrue(Validator.isInvalid(""));
        Assert.assertTrue(Validator.isInvalid("12"));
        Assert.assertFalse(Validator.isInvalid("1234"));
        Assert.assertFalse(Validator.isInvalid("password"));
    }

}