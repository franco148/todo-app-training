package com.umss.todo.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<Email, String> {
    private String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@";
    private String type = "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+";
    private String domain = "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(regex);
    }

    @Override
    public void initialize(Email constraintAnnotation) {
        String[] domains = constraintAnnotation.domains();
        String[] types = constraintAnnotation.types();
        if(types.length != 0) {
            type = getRegex(types);
        }
        if(domains.length != 0) {
            domain = "\\." + getRegex(domains) + "$";
        }
        regex += type + domain;
    }

    private String getRegex(final String[] regexps) {
        String regularExp = "(";
        for (String regexp: regexps) {
            regularExp += regexp + "|";
        }
        regularExp = regularExp.substring(0, regularExp.length()-1);
        regularExp += "){1}";
        return regularExp;
    }
}
