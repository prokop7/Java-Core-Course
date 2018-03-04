package services;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import services.exceptions.*;

public class AuthorizationServiceTest {
    private static AuthorizationService testObject;
    private static String l = "Anton";
    private static String p = "123";
    private static String token = null;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            testObject = new AuthorizationService("test.db");
        } catch (DbConnectionException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        try {
            testObject.reset();
        } catch (InternalDbException ignored) {
            Assert.fail();
        }
    }

    @Test
    public  void authenticate0() throws DuplicatedLoginException, InternalDbException, EmptyFieldException, NullFieldException {
        testObject.register(l, p);
    }

    @Test
    public void authenticate1() throws NullFieldException, EmptyFieldException {
        token = testObject.authenticate(l, p);
        Assert.assertNotNull(token);
    }

    @Test(expected = DuplicatedLoginException.class)
    public void authenticate2() throws
            DuplicatedLoginException,
            InternalDbException,
            EmptyFieldException,
            NullFieldException {
        testObject.register(l, p);
    }

    @Test
    public void authenticate3() throws NullFieldException, EmptyFieldException {
        Assert.assertEquals(token, testObject.authenticate(l, " " + p + " "));
    }

    @Test(expected = NullFieldException.class)
    public void authenticate4() throws NullFieldException, EmptyFieldException {
        testObject.authenticate(null, p);
    }

    @Test(expected = EmptyFieldException.class)
    public void authenticate5() throws NullFieldException, EmptyFieldException {
        testObject.authenticate("", p);
    }

    @Test()
    public void authenticate6() throws NullFieldException, EmptyFieldException {
        Assert.assertNull(testObject.authenticate(l, p + 1));
    }


}