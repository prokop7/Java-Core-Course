package server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import server.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getByLogin(String login);
    Account getAccountByLoginAndPassword(String login, String password);
    Account getAccountByToken(String token);
}
