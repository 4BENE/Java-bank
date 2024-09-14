package services;

import entities.bank.Bank;
import models.account.AccountType;
import models.date.Date;
import models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    private final User user = new User("Ivan", "Chizhikov", "vyazma", null);
    private final User user1 = new User("Masha", "Chizhikova", null, "767676" );
    private final Bank bank;

    public BankTest() {
        bank = new Bank("SBEERBANK", 0.1, 100, new Date(0,1,0), 1000);
        bank.addUser(user.name,user.surname,user.address,user.passport);
        bank.addUser(user1.name,user1.surname,user1.address,user1.passport);
    }

    @Test
    public void createNewAccountTest() {
        bank.createNewAccount(AccountType.credit,1000, user);
        assertEquals(bank.findAccounts(user).getFirst().checkBalance(), 1000);
        assertEquals(bank.findAccounts(user).getFirst().getAccountType(), AccountType.credit);
    }

    @Test
    public void notificationTest() {
        user.subscribe(bank);
        user1.subscribe(bank);

        bank.makeChanges(0.2,1000);
        assertEquals(user.readNotification(user.getAllNotification().getFirst()),user1.readNotification(user1.getAllNotification().getFirst()));
        assertEquals(user.readNotification(user.getAllNotification().getFirst()), "NEW CHANGES!\nNow new percents is " + 0.2 + "new limit is" + 1000.0);
    }
}
