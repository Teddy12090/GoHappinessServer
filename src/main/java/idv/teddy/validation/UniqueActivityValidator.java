package idv.teddy.validation;

import idv.teddy.dto.ActivityDto;
import idv.teddy.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueActivityValidator implements ConstraintValidator<UniqueActivity, ActivityDto> {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void initialize(UniqueActivity constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ActivityDto value, ConstraintValidatorContext context) {
        if (value != null)
            return activityRepository.findByUrl(value.getUrl()) == null;
        return false;
    }

}
