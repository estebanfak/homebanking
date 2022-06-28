package com.mindhub.homebanking.dtos;

public class PaymentsDTO {

    private String cardNumber;
    private String securityCode;
    private double amount;
    private String detail;

    public PaymentsDTO() {}

    public PaymentsDTO(String cardNumber, String securityCode, double amount, String detail) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.amount = amount;
        this.detail = detail;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getSecurityCode() {
        return securityCode;
    }
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
