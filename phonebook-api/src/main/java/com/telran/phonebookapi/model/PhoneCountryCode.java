package com.telran.phonebookapi.model;

import lombok.Setter;

public enum PhoneCountryCode {
    AT("+43"),
    BE("+32"),
    HU("+36"),
    DE("+49"),
    DK("+45"),
    IL("+39"),
    ES("+34"),
    IT("+39"),
    NO("+47"),
    PL("+48"),
    PT("+351"),
    FI("+358"),
    FR("+33"),
    CZ("+420"),
    EE("+372");

    private final String code;

    PhoneCountryCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
