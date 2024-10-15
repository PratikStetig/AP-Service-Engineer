package com.asianpaints.apse.service_engineer.validator;

import com.asianpaints.apse.service_engineer.annotation.DateBeforeOrEqualToday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateBeforeOrEqualTodayValidator implements ConstraintValidator<DateBeforeOrEqualToday, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }
        return !value.isAfter(LocalDate.now());
    }
}
