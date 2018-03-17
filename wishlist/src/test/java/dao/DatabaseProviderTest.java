package dao;

import dao.exceptions.InternalExecutionException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseProviderTest {
    private static DatabaseProvider dao;
    private String l = "Login";
    private String p = "Password";
    private String token = "Token";

    @BeforeClass
    public static void setUp() throws Exception {
        dao = SqliteProvider.getProvider("C:\\Users\\Monopolis\\Desktop\\test.db");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dao.reset();
    }

    @Test
    public void addRecord01() throws InternalExecutionException {
        dao.addRecord(l, p);
        assertTrue(dao.containsLogin(l));
        assertTrue(dao.checkPassword(l, p));
    }

    @Test(expected = InternalExecutionException.class)
    public void addRecord02() throws InternalExecutionException {
        dao.addRecord(l, p);
    }

    @Test
    public void assignToken01() throws InternalExecutionException {
        dao.assignToken(l, token);
        assertEquals(l, dao.getLoginByToken(token));
        assertNull(dao.getLoginByToken("AnotherToken"));
        assertEquals(token, dao.getTokenByLogin(l));
        dao.removeToken(token);
        assertNull(dao.getLoginByToken(token));
        assertNull(dao.getTokenByLogin(l));
    }
}