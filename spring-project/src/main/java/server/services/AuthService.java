package server.services;

public interface AuthService {
    void reset() throws InternalDbException;

    void register(String l, String p) throws
            NullFieldException,
            EmptyFieldException,
            InvalidFieldException,
            DuplicatedLoginException,
            InternalDbException;

    String authenticate(String l, String p) throws
            NullFieldException,
            EmptyFieldException;

    String authorize(String token);

    void logout(String token) throws InternalDbException;
}
