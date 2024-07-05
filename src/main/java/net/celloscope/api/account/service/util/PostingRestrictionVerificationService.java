package net.celloscope.api.account.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.account.dto.PostingRestriction;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.StatusYesNo;
import net.celloscope.api.product.enums.OperationType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static net.celloscope.api.core.util.Messages.*;

@Slf4j
@Component
public class PostingRestrictionVerificationService {


    private final ObjectMapper objectMapper;

    public PostingRestrictionVerificationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean checkFromAccountCanDebit(String postingRestriction)  {
        try {
            return isPostingRestrictionValidForTransaction(postingRestriction, OperationType.CAN_DEBIT.getValue());
        } catch (ExceptionHandlerUtil e) {
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean checkToAccountCanCredit(String postingRestriction)  {
        try {
            return isPostingRestrictionValidForTransaction(postingRestriction, OperationType.CAN_CREDIT.getValue());
        } catch (ExceptionHandlerUtil e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean isPostingRestrictionValidForTransaction(String postingRestrictionString, String operationType) throws ExceptionHandlerUtil {
        if (postingRestrictionString == null || postingRestrictionString.isEmpty()) {
            log.error("ACCOUNT POSTING RESTRICTION IS NULL OR EMPTY" + postingRestrictionString);
            return true;
        }
        PostingRestriction postingRestriction = getPostingRestrictionObjectFromString(postingRestrictionString);
        return getOperationTypeFieldValue(postingRestriction, operationType).equals(StatusYesNo.Yes.toString());
    }

    private String getOperationTypeFieldValue(PostingRestriction postingRestriction, String operationType) throws ExceptionHandlerUtil {
        Field field;
        try {
            field = postingRestriction.getClass().getDeclaredField(operationType);
        } catch (NoSuchFieldException e) {
            log.error("CAN NOT PARSE ACCOUNT DEFINITION FROM ACCOUNT ENTITY");
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, PARSING_ACCOUNT_POSTING_RESTRICTION_FAILED);
        }
        field.setAccessible(true);
        Object operationTypedValue;
        try {
            operationTypedValue = field.get(postingRestriction);
        } catch (IllegalAccessException e) {
            log.error("CAN NOT PARSE ACCOUNT DEFINITION FROM ACCOUNT ENTITY");
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, PARSING_ACCOUNT_POSTING_RESTRICTION_FAILED);
        }
        return operationTypedValue.toString();
    }

    private PostingRestriction getPostingRestrictionObjectFromString(String postingRestrictionString) throws ExceptionHandlerUtil {
        PostingRestriction postingRestriction;
        try {
            postingRestriction = objectMapper.readValue(postingRestrictionString, PostingRestriction.class);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("CAN NOT PARSE ACCOUNT DEFINITION FROM ACCOUNT ENTITY");
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, PARSING_ACCOUNT_POSTING_RESTRICTION_FAILED);
        }
        return postingRestriction;
    }
}
