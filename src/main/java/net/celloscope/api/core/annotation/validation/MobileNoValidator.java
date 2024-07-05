package net.celloscope.api.core.annotation.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



import com.google.common.base.CaseFormat;
import net.celloscope.api.core.annotation.MobileNo;
import net.celloscope.api.core.util.Messages;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DataValidationConstraint;

public class MobileNoValidator implements ConstraintValidator<MobileNo, String> {

	private List<ValidationType> validateExcept;
	private String attributeName ;
	
	@Override
    public void initialize(MobileNo object) {
    	this.validateExcept = Arrays.asList(object.exclude());
    	this.attributeName = object.attributeName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
    	String attr_name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.attributeName);
        boolean isValid = false;
        String message = "";
        
        if (value == null){
        	if (this.validateExcept.contains(ValidationType.NULL)) return true;
        	message = Messages.REQUEST_MSG_PREFIX+"null_"+attr_name;
        } else if (StringUtils.isBlank(value)){
        	if (this.validateExcept.contains(ValidationType.EMPTY)) return true;
        	message = Messages.REQUEST_MSG_PREFIX+"empty_"+attr_name;
        } else if (!(value.length() == 11 || value.length() == 14 )) {
        	if (this.validateExcept.contains(ValidationType.WRONGLENGTH)) return true;
        	message = Messages.REQUEST_MSG_PREFIX + "wronglength_"+attr_name+" : "+this.attributeName+" length must be 11 or 14!";
        } else if (!value.matches("^01[3-9][0-9]{8}$")){
        	if (this.validateExcept.contains(ValidationType.MALFORMED)) return true;
        	message = Messages.REQUEST_MSG_PREFIX + "malformed_"+attr_name+" : "+this.attributeName+" must match following regex: '^01[3-9][0-9]{8}$'!";
        } else {
        	return true;
        }
        constraintContext.disableDefaultConstraintViolation();
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return isValid;
    }

}
