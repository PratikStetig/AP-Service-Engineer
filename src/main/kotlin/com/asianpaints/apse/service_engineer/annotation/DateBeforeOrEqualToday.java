package com.asianpaints.apse.service_engineer.annotation;

import com.asianpaints.apse.service_engineer.validator.DateBeforeOrEqualTodayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateBeforeOrEqualTodayValidator.class)
public @interface DateBeforeOrEqualToday {
    String message() default "Date must be before or equal to today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}