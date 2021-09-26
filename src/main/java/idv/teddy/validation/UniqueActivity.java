package idv.teddy.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueActivityValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueActivity {
    String message() default "{activity.url.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
