package user;

import models.users.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    @Test
    public void userVerifiedTest() {
        User user = new User("Ivan", "Ivanov", null, null);
        assertFalse(user.isVerified());
        user.addAddress("vyazma");
        assertFalse(user.isVerified());
        user.addPassport("123456");
        assertTrue(user.isVerified());
    }
}
