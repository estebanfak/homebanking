package com.mindhub.homebanking.dtos;

public class PaymentsDTO {

    private String number;
    private String securityCode;
    private double amount;
    private String detail;
    private String payingMethod;

    public PaymentsDTO() {
    }

    public PaymentsDTO(String number, String securityCode, double amount, String detail, String payingMethod) {
        this.number = number;
        this.securityCode = securityCode;
        this.amount = amount;
        this.detail = detail;
        this.payingMethod = payingMethod;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
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
    public String getPayingMethod() {
        return payingMethod;
    }
    public void setPayingMethod(String payingMethod) {
        this.payingMethod = payingMethod;
    }
}
