package server.persistence;

import org.springframework.data.repository.Repository;
import server.model.Account;

public interface AccountRepository extends Repository<Account, Integer> {
    Account getByLogin(String login);
}
