package server.udp.authenticate_chain;

import server.Account;
import server.SocketWrapper;

public interface AuthenticateStep {
    boolean handle(Account account, SocketWrapper socket);
}
