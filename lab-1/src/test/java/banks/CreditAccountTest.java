package banks;

import entities.bankAccounts.CreditAccount;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {
    private final User user = new User("ivan", "ivanov", null, null);

    @Test
    public void minusBalanceTest() throws LowAmountException, NotVerifiedLimitException {
        CreditAccount creditAccount = new CreditAccount(user,100, 0.1, 10000, -1000);
        creditAccount.takeMoney(1000);
        Assertions.assertEquals(creditAccount.checkBalance(), -900);
    }

    @Test
    public void checkLimitTest() {
        CreditAccount creditAccount = new CreditAccount(user, 1000,0.1, 1000, -100);
        Assertions.assertThrows(LowAmountException.class, () -> creditAccount.takeMoney(2000));
    }
}
