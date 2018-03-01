package dao;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostgreProviderTest {

    private DatabaseProvider testObject;

    @BeforeEach
    void setUp() {
        this.testObject = new PostgreProvider();
    }

    @AfterEach
    void tearDown() {
        this.testObject.reset();
    }

    @Test
    void authenticate() {
        String l = "Anton";
        String p = "123";
        Assert.assertTrue(testObject.register(l, p));
        String token = testObject.authenticate(l, p);
        Assert.assertNotNull(token);
        Assert.assertEquals(token, testObject.authenticate(l, " " + p + " "));
        Assert.assertEquals(token,testObject.authenticate(" " + l + " ", p));
        Assert.assertEquals(token, testObject.authenticate(l + " ", " " + p));
        Assert.assertNull(testObject.authenticate(l, p + 1));
        Assert.assertNull(testObject.authenticate(l + 1, p));
    }

    @Test
    void registerEmpty() {
        String l = "Anton";
        String p = "";

        Assert.assertFalse(testObject.register(l, p));

        l = "";
        p = "123";
        Assert.assertFalse(testObject.register(l, p));
    }

    @Test
    void register() {
        Assert.assertTrue(testObject.register("Anton", "123"));
        Assert.assertFalse(testObject.register("Anton", "123"));
        Assert.assertFalse(testObject.register("Anton ", "123"));
        Assert.assertFalse(testObject.register(" Anton ", "123"));
    }
}