package server;

public interface Authenticator {
    void passwordChange(Account account);
    Account authenticate(SocketWrapper socket);
}
