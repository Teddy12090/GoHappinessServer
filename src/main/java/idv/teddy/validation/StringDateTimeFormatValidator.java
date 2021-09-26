package idv.teddy.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringDateTimeFormatValidator implements ConstraintValidator<StringDateTimeFormat, String> {

    private SimpleDateFormat dateFormat;

    @Override
    public void initialize(StringDateTimeFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        dateFormat = new SimpleDateFormat(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (value != null)
                dateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
