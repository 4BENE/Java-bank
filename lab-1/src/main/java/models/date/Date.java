package models.date;

public class Date {
    int days;
    int month;
    int years;
    public Date(int days, int month, int years){
        this.days = days;
        this.month = month;
        this.years = years;
    }
    public void spendTime(Date date){
        int days = date.days;
        int month = date.month;
        int years = date.years;
        this.days += days;
        if(days > 31){
            this.month += this.days / 31;
            this.days = this.days % 31;
        }
        this.month += month;
        if(month > 12){
            this.years += this.month / 12;
            this.month = this.month % 12;
        }
        this.years += years;
    }
    public void blockingSkip(Date date){
        int days = date.days;
        int month = date.month;
        int years = date.years;
        if (date.isEmpty()) return;
        this.days -= days;
        if (this.days < 0){
            this.days = 31 + this.days;
            this.month -= 1;
        }
        this.month -= month;
        if (this.month < 0) {
            this.month = 12 + this.month;
            this.years -=1;
        }
        this.years -= years;
        if (this.years < 0){
            this.years = 0;
            this.month = 0;
            this.days = 0;
        }
    }
    public int getDays(){
        return days;
    }
    public int getMonth(){
        return month;
    }
    public int getYears(){
        return years;
    }
    public void copyTime(Date date){
        this.days = date.getDays();
        this.month = date.getMonth();
        this.years = date.getYears();
    }
    public boolean isEmpty(){
        return days == 0 && month == 0 && years == 0;
    }
    public void getFree(){
        days = 0;
        month = 0;
        years = 0;
    }
}
