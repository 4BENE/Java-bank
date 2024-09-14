package entities.bank;

import models.date.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CentralBank {
    public List<Bank> allBanks = new ArrayList<Bank>();
    public void skipTime(Date date){
        for(var x : allBanks){
            for(var y :x.accounts){
                y.skipTime(date);
            }
        }
    }
    public void createNewBank(String name,double defaultPercents, double creditLimit,Date depositBlockingDate, double notVerifyLimit){
        allBanks.add(new Bank(name,defaultPercents, creditLimit, depositBlockingDate, notVerifyLimit));
    }
    public void addNewBank(Bank bank){
        allBanks.add(bank);
    }

    public Bank getBank(String bankName) {
        for (var x : allBanks){
            if(Objects.equals(x.name, bankName)){
                return x;
            }
        }
        throw new IllegalArgumentException("This bank doesnt exist");
    }
}
