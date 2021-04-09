package sample;

public class UserInput {
    private double amount;
    private double interest;
    private boolean annuet;

    public double getMonthlyPercent() { return monthlyPercent; }

    public void setMonthlyPercent(double monthlyPercent) { this.monthlyPercent = monthlyPercent; }

    private double monthlyPercent;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() { return interest; }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public boolean isAnnuet() {
        return annuet;
    }

    public void setAnnuet(boolean annuet) {
        this.annuet = annuet;
    }

    public boolean isLinear() {
        return linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    private boolean linear;
    private int months;

    UserInput (double amount, double interest, boolean annuet, boolean linear, int months) {
        this.amount = amount;
        this.interest = interest;
        this.annuet = annuet;
        this.linear = linear;
        this.months = months;
        this.monthlyPercent = interest/12;
    }
}
