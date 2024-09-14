package banks;

import entities.bank.Bank;
import entities.bankAccounts.DebitAccount;
import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.date.Date;
import models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DebitAccountTest {

    private final User user = new User("Ivan", "Ivanov", "ITMO", null);

    @Test
    public void percentsTest() {
        DebitAccount debitAccount = new DebitAccount(user, 5000, 0.1, 1000);
        debitAccount.skipTime(new Date(0,1,0));
        assertEquals(debitAccount.checkBalance(), 5000 + 5000 * 0.1 / 12);
    }

    @Test
    public void checkLimitVerifyTest() {
        DebitAccount debitAccount = new DebitAccount(user, 100, 0.1, 50);
        assertThrows(NotVerifiedLimitException.class, () -> debitAccount.takeMoney(60));
    }

    @Test
    public void checkLimitTest() {
        DebitAccount debitAccount = new DebitAccount(user, 100, 0.1, 50);
        assertThrows(LowAmountException.class, () -> debitAccount.takeMoney(1000));
    }

    @Test
    public void undoTransactionsTest() throws LowAmountException, NotVerifiedLimitException, DateAccessException {
        DebitAccount debitAccount = new DebitAccount(user, 1000, 10, 20000);
        debitAccount.takeMoney(100);
        debitAccount.putMoney(200);
        Bank bank = new Bank("Sber", 0.1, 1000, new Date(0 ,1 ,0),1000 );
        bank.addUser(user.name,user.surname,user.address,user.passport);
        bank.accounts.add(debitAccount);
        bank.undoTransaction(debitAccount, 1);
        assertEquals(debitAccount.checkBalance(), 900);
        bank.undoTransaction(debitAccount, 0);
        assertEquals(debitAccount.checkBalance(), 1000);
    }

}
