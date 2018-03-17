package server.services;

import server.services.exceptions.DuplicatedLoginException;
import server.services.exceptions.EmptyFieldException;
import server.services.exceptions.InvalidFieldException;
import server.services.exceptions.NullFieldException;

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
