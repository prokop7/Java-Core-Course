package server.services;

public interface AuthService {
    void reset();

    void register(String l, String p) throws
            NullFieldException,
            EmptyFieldException,
            InvalidFieldException,
            DuplicatedLoginException;

    String authenticate(String l, String p) throws
            NullFieldException,
            EmptyFieldException;

    String authorize(String token);

    void logout(String token);
}
