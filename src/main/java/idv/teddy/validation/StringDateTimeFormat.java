package idv.teddy.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringDateTimeFormatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringDateTimeFormat {
    String value();

    String message() default "{date.invalidFormat}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
