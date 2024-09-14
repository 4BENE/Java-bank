package banks;

import entities.bankAccounts.DepositAccount;
import exceptions.DateAccessException;
import models.date.Date;
import models.users.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositAccountTest {
    private final User user = new User("Ivan", "Ivanov", "Chizhiiiii", "666");
    @Test
    public void dateLimitTest() {
        DepositAccount depositAccount = new DepositAccount(user, 1000, new Date(0,1,0), 100,0.1);
        assertThrows(DateAccessException.class, ()->depositAccount.takeMoney(100));
        depositAccount.skipTime(new Date(0,1,0));
        assertDoesNotThrow(() -> depositAccount.takeMoney(100));
    }
}
