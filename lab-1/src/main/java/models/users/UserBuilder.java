package models.users;
import org.jetbrains.annotations.Nullable;

public class UserBuilder {
    private String name;
    private String surname;
    @Nullable private String address;
    @Nullable private String passport;
    public UserBuilder withNameAndSurname(String name, String surname){
        this.name = name;
        this.surname = surname;
        return this;
    }
    public UserBuilder withAddress(@Nullable String address){
        this.address = address;
        return this;
    }

    public UserBuilder withPassport(@Nullable String passport){
        this.passport = passport;
        return this;
    }
    public User build(){
     return new User(name, surname, address,passport);
    }
}
