package centralBank;

import entities.bank.Bank;
import entities.bank.CentralBank;
import models.date.Date;
import models.users.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CentralBankTest {

    private final User user1;
    private final User user2;
    private final Bank bank1;

    private final Bank bank2;


    public CentralBankTest() {
        user1 = new User("Ivan", "Chizhikov", null, null);
        bank1 = new Bank("Sber", 0.1, 1000,new Date(0,1,0) ,1000);
        bank1.addUser(user1.name, user1.surname,user1.address,user1.passport);
        bank2 = new Bank("Big", 10, 1000, new Date(0,1,0),1000);
        user2 = new User("Maria", "Chizhikova", null, null);
        bank2.addUser(user2.name, user2.surname,user2.address,user2.passport);
    }

    @Test
    public void addBankTest() {
        CentralBank centralBank = new CentralBank();
        centralBank.addNewBank(bank1);
        assertEquals(centralBank.getBank(bank1.name),bank1);
    }


    @Test
    public void createBankTest() {
        CentralBank centralBank = new CentralBank();
        centralBank.createNewBank("AB",0.1,10,new Date(0,1,0),100);
        assertEquals(centralBank.getBank("AB").defaultPercents,0.1);
        assertEquals(centralBank.getBank("AB").notVerifiedLimit,100);
        assertEquals(centralBank.getBank("AB").creditLimit,10);
        assertEquals(centralBank.getBank("AB").depositBlockingDate.getMonth(),new Date(0,1,0).getMonth());
    }

    @Test
    public void getBankTest(){
        CentralBank centralBank = new CentralBank();
        centralBank.addNewBank(bank1);
        centralBank.addNewBank(bank2);
        assertThrows(IllegalArgumentException.class, () -> centralBank.getBank("not normal name"));
        assertEquals(centralBank.getBank("Sber"),bank1);
    }

}
