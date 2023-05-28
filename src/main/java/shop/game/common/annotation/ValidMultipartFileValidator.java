package shop.game.common.annotation;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidMultipartFileValidator implements ConstraintValidator<ValidMultipartFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return !file.isEmpty();
    }
}
