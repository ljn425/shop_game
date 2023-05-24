package shop.game.common.annotation;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidMultipartFileValidator implements ConstraintValidator<ValidMultipartFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        //System.out.println("file = " + file);
        //System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        //System.out.println("file.isEmpty() = " + file.isEmpty());
        return !file.isEmpty();
    }
}
