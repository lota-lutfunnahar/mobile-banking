package net.celloscope.api.transaction.service.util;

import net.celloscope.api.account.entity.AccountEntity;
import net.celloscope.api.account.service.util.PostingRestrictionVerificationService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.product.util.ProductDefinitionVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static net.celloscope.api.core.util.Messages.CREDIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION;
import static net.celloscope.api.core.util.Messages.DEBIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION;

@Component
public class AccountValidationUtilService {
    private final ProductDefinitionVerificationService productDefinitionVerificationService;
    private final PostingRestrictionVerificationService postingRestrictionVerificationService;

    public AccountValidationUtilService(ProductDefinitionVerificationService productDefinitionVerificationService, PostingRestrictionVerificationService postingRestrictionVerificationService) {
        this.productDefinitionVerificationService = productDefinitionVerificationService;
        this.postingRestrictionVerificationService = postingRestrictionVerificationService;
    }

    public void validateAccountPostingRestrictionAndProductDefinition(AccountEntity debitAccount, AccountEntity creditAccount) throws ExceptionHandlerUtil {
        validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(debitAccount);
        validateAccountPostingRestrictionAndProductDefinitionForCreditAccount(creditAccount);
    }

    public void validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(AccountEntity debitAccount) throws ExceptionHandlerUtil {
        if (!productDefinitionVerificationService.checkFromAccountCanDebit(debitAccount.getProductOid()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, DEBIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

        if (!postingRestrictionVerificationService.checkFromAccountCanDebit(debitAccount.getPostingRestrict()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, DEBIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

    }

    public void validateAccountPostingRestrictionAndProductDefinitionForCreditAccount(AccountEntity creditAccount) throws ExceptionHandlerUtil {
        if (!productDefinitionVerificationService.checkToAccountCanCredit(creditAccount.getProductOid()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, CREDIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

        if (!postingRestrictionVerificationService.checkToAccountCanCredit(creditAccount.getPostingRestrict()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, CREDIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);
    }
}
