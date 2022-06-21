package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;

public final class Utils {
//---------------------------------------------------RANDOM ACCOUNT NUMBER ---------------------------------------------
    public static String getRandomAccountNumber() {
        int aux = (int) ((Math.random() * (100000000 - 0)) + 0);
        if(aux < 10000000 && aux >= 1000000) {
            return "VIN-0" + aux;
        } else if(aux < 1000000 && aux >= 100000) {
            return "VIN-00" + aux;
        } else if(aux < 100000 && aux >= 10000){
            return "VIN-000" + aux;
        } else if(aux < 10000 && aux >= 1000){
            return "VIN-0000" + aux;
        } else if(aux < 1000 && aux >= 100){
            return "VIN-00000" + aux;
        } else if (aux < 100 && aux >= 10) {
            return "VIN-000000" + aux;
        }else if (aux < 10) {
            return "VIN-0000000" + aux;
        }
           return "VIN-" + aux;
    }

    public static String generateNewAccountNumber(AccountRepository accountRepository) {
        String aux;
        do {
            aux = getRandomAccountNumber();
        } while (accountRepository.findByNumber(aux) != null);
        return aux;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------RANDOM CARD NUMBER-------------------------------------------------
    public static int getRandomCardNumberHeader() {
        return (int) ((Math.random() * (5000 - 4000)) + 4000);
    }
    public static String getRandomCardNumberBody() {
        int aux = (int) ((Math.random() * (10000 - 0)) + 0);
        if(aux < 1000 && aux >= 100){
            return "0" + aux;
        } else if (aux < 100 && aux >= 10) {
            return "00" + aux;
        }else if (aux < 10) {
            return "000" + aux;
        }
        return Integer.toString(aux);
    }
    public static String getCardNumberFull(){
        String a = Integer.toString(getRandomCardNumberHeader());
        String b = getRandomCardNumberBody();
        String c = getRandomCardNumberBody();
        String d = getRandomCardNumberBody();
        return a + b + c + d;
    }
    public static String generateNewCardNumber(CardRepository cardRepository) {
        String aux;
        do {
            aux = getCardNumberFull();
        } while (cardRepository.findByNumber(aux) != null);
        return aux;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------RANDOM CVV NUMBER--------------------------------------------------
    public static String getRandomCvvNumber() {
        int aux = (int) ((Math.random() * (1000 - 0)) + 0);
        if(aux < 100 && aux >= 10){
            return "0" + aux;
        }else if (aux < 10) {
            return "00" + aux;
        }
        return Integer.toString(aux);
    }
//----------------------------------------------------------------------------------------------------------------------

    private Utils() {}
}
