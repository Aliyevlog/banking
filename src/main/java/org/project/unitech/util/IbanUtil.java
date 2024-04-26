package org.project.unitech.util;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Component;

@Component
public class IbanUtil {

    public static String generateAzerbaijaniIban() {
        return Iban.random(CountryCode.AZ).toString();
    }
}
