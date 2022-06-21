package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
public class UtilsTest {

    @Test
    public void cardNumberIsCreated(){

        String cardNumber = Utils.getCardNumberFull();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
}
