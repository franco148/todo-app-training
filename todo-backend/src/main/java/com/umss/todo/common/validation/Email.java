package com.umss.todo.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidation.class)
public @interface Email {
    String message() default "The field should be an email";
    String[] domains() default {};
    String[] types() default {};
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
