package sample;

import java.text.DecimalFormat;

public class Payment {
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestPercent() {
        return interestPercent;
    }

    public void setInterestPercent(double interestPercent) {
        this.interestPercent = interestPercent;
    }

    public double getAmountLeft() { return amountLeft; }

    public void setAmountLeft(double amountLeft) {
        this.amountLeft = amountLeft;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(double interestPayment) {
        this.interestPayment = interestPayment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    private double amount;
    private double interestPercent;
    private double amountLeft;
    private double interestPayment;
    private int number;
    private double monthlyPayment;
    private double creditAmount;

    public double getCurrentSum() { return currentSum; }

    public void setCurrentSum(double currentSum) { this.currentSum = currentSum; }

    private double currentSum;

    private String numberStr;

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }

    public String getMonthlyPaymentStr() {
        return monthlyPaymentStr;
    }

    public void setMonthlyPaymentStr(String monthlyPaymentStr) {
        this.monthlyPaymentStr = monthlyPaymentStr;
    }

    public String getCreditAmountStr() {
        return creditAmountStr;
    }

    public void setCreditAmountStr(String creditAmountStr) {
        this.creditAmountStr = creditAmountStr;
    }

    public String getInterestPaymentStr() {
        return interestPaymentStr;
    }

    public void setInterestPaymentStr(String interestPaymentStr) {
        this.interestPaymentStr = interestPaymentStr;
    }

    public String getAmountLeftStr() {
        return amountLeftStr;
    }

    public void setAmountLeftStr(String amountLeftStr) {
        this.amountLeftStr = amountLeftStr;
    }

    private static DecimalFormat df = new DecimalFormat("#.##");

    private String monthlyPaymentStr;
    private String creditAmountStr;
    private String interestPaymentStr;
    private String amountLeftStr;

    Payment(double creditAmount, double monthlyPayment, int number, double amount, double amountLeft, double interestPayment, double currentSum) {
        this.amount = amount;
        this.interestPayment = interestPayment;
        this.amountLeft = amountLeft;
        this.number = number;
        this.monthlyPayment = monthlyPayment;
        this.creditAmount = creditAmount;
        this.currentSum = currentSum;

        this.numberStr = toString(number);
        this.monthlyPaymentStr = toString(monthlyPayment);
        this.creditAmountStr = toString(creditAmount);
        this.interestPaymentStr = toString(interestPayment);
        this.amountLeftStr = toString(amountLeft);
    }

    public String toString() {
        String str = toString(number) + ".     | " +
                toString(monthlyPayment) + "     | " +
                toString(creditAmount) + "     | " +
                toString(interestPayment) + "     | " +
                toString(amountLeft);
        return str;
    }

    public static String printHeader() {
        String str = "Nr.      Mėnesio įmoka           Kreditas         Palūkanos        Mokėti liko\n";
        return str;
    }

    public static String toString(int x) {
        return String.valueOf(x);
    }
    public static String toString(double x) {
        return String.valueOf(String.format("%.2f", x)) + " €";
    }
}
