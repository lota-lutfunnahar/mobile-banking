package net.celloscope.api.bill.mobilerecharge.transaction.application.service;

import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.account.service.util.PostingRestrictionVerificationService;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeAccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.in.MobileRechargeValidatorUseCase;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.core.client.TransactionProfileClient;
import net.celloscope.api.core.service.AbsClientService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.metaProperty.entity.MetaProperty;
import net.celloscope.api.metaProperty.service.MetaPropertyHelperService;
import net.celloscope.api.product.util.ProductDefinitionVerificationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static net.celloscope.api.core.util.Messages.*;

@Service
@Slf4j
public class MobileRechargeTransactionValidator implements MobileRechargeValidatorUseCase {

    @Autowired
    private ProductDefinitionVerificationService productDefinitionVerificationService;

    @Autowired
    private PostingRestrictionVerificationService postingRestrictionVerificationService;

    @Autowired
    TransactionProfileClient tpClient;

    @Value("${metaData.transaction.code}")
    private String transactionMetaCode;

    @Value("${metaData.transaction.name}")
    private String transactionMetaName;

    @Autowired
    private MetaPropertyHelperService metaPropertyService;

    private static List<MetaProperty> transactionMetaData = new ArrayList<>();

    @Override
    public void validateUser(IBUser user) throws ExceptionHandlerUtil {
        if (user == null) {
        throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
         }
        if (user.getStatus() == null || !user.getStatus().equalsIgnoreCase(ACTIVE))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, USER_INACTIVE);

        if (Strings.isBlank(user.getMobileNo()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, USER_MOBILE_NOT_FOUND);

    }

    @Override
    public void isDebitCreditAccountDifferent(Account debitAccount, String creditAccount) throws ExceptionHandlerUtil {
        log.info("Checking if debit credit account is different for {}, {}", debitAccount, creditAccount);
        if (debitAccount.getBankAccountNo().equals(creditAccount)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, SAME_FROM_TO_ACCOUNT);
        }

    }

    @Override
    public void isCreditAccountFromSameCustomer(List<MobileRechargeCustomerEntity> customers, String creditAccount) throws ExceptionHandlerUtil {
        log.info("Checking if credited account is from same customer for {}, {}", customers, creditAccount);

        MobileRechargeAccountEntity accountEntity = customers.stream()
                .flatMap(cus -> cus.getAccounts().stream())
                .filter(ac -> ac.getBankAccountNo().equals(creditAccount))
                .findFirst().orElse(null);
        if (accountEntity != null) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, ACCOUNT_IS_FROM_REQUESTED_CUSTOMER);
        }

    }

    @Override
    public void isDebitAccountActive(Account fromAccount) throws ExceptionHandlerUtil {
        log.info("Checking if debit account is active {}, {}", fromAccount);

        if (!fromAccount.getStatus().equalsIgnoreCase(OPERATIVE)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, "From " + ACCOUNT_NOT_OPERATIVE);
        }

    }

    @Override
    public void isAccountActive(Account account, String accountPrefix) throws ExceptionHandlerUtil {
        log.info("Checking if account is active {}, {}", accountPrefix, account );

        if (!account.getStatus().equalsIgnoreCase(OPERATIVE)) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, accountPrefix + " " + ACCOUNT_NOT_OPERATIVE);
        }

    }

    @Override
    public void isDebitAccountTransactional(Account fromAccount) throws ExceptionHandlerUtil {
//        List<String> savingOrCurrentAccount = Arrays.asList(SAVINGS_ACCOUNT, CURRENT_ACCOUNT);

        log.info("Checking if debit account is transactional {}, {}", fromAccount );

        transactionMetaData = metaPropertyService.getValueJson(transactionMetaCode, transactionMetaName);

        List<String> transactionalAccount = transactionMetaData.get(0).getValue();

        if (!transactionalAccount.contains(fromAccount.getProductType())) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, "From " + ACCOUNT_NOT_TRANSACTIONAL);
        }

    }

    @Override
    public void isAccountTransactional(Account account, String accountPrefix) throws ExceptionHandlerUtil {
        log.info("Checking if account is transactional {}, {}", accountPrefix, account );

        transactionMetaData = metaPropertyService.getValueJson(transactionMetaCode, transactionMetaName);

        List<String> transactionalAccount = transactionMetaData.get(0).getValue();

        if (!transactionalAccount.contains(account.getProductType())) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, accountPrefix + " " + ACCOUNT_NOT_TRANSACTIONAL);
        }

    }

    @Override
    public void checkTransactionProfile(String accountNo, String product, BigDecimal transactionAmount) throws ExceptionHandlerUtil {
        log.info("Checking transactional profile for account no {}, product {}, amount {}", accountNo, product, transactionAmount );
        ResponseEntity<AbsClientService.TransactionProfileValidateResponse> response = tpClient.getTpValidateResponse(accountNo, product, transactionAmount);
        log.info("Checking transactional profile response {} for account no {}, product {}, amount {}", response, accountNo, product, transactionAmount );

        if(!response.getStatusCode().is2xxSuccessful() || !response.getBody().getBody().isData()) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, "Transaction limit exceed");
        }

    }

    @Override
    public void isBalanceGraterThenTotal(BigDecimal workingBalance, BigDecimal totalAmount) throws ExceptionHandlerUtil {
        log.info("Checking balance  greater than working balance {}, {}", totalAmount, workingBalance );

        if(workingBalance.compareTo(totalAmount) < 0){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, "Working balance is less then total amount.");
        }

    }

    public void validateAccountPostingRestrictionAndProductDefinition(Account debitAccount, Account creditAccount) throws ExceptionHandlerUtil {
        validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(debitAccount);
        validateAccountPostingRestrictionAndProductDefinitionForCreditAccount(creditAccount);
    }

    @Override
    public void validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(Account debitAccount) throws ExceptionHandlerUtil {
        if (!productDefinitionVerificationService.checkFromAccountCanDebit(debitAccount.getProductOid()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, DEBIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

        if (!postingRestrictionVerificationService.checkFromAccountCanDebit(debitAccount.getPostingRestrict()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, DEBIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

    }

    public void validateAccountPostingRestrictionAndProductDefinitionForCreditAccount(Account creditAccount) throws ExceptionHandlerUtil {
        if (!productDefinitionVerificationService.checkToAccountCanCredit(creditAccount.getProductOid()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, CREDIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);

        if (!postingRestrictionVerificationService.checkToAccountCanCredit(creditAccount.getPostingRestrict()))
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, CREDIT_ACCOUNT_IS_NOT_ELIGIBLE_FOR_TRANSACTION);
    }


    /* private static List<MetaProperty> transactionBkashMetaData = new ArrayList<>();*/

     /*public void isBkashAccountTransactionProfile(BigDecimal amount) throws ExceptionHandlerUtil {
        log.info("Checking if amount is transactional {}", amount);

        transactionBkashMetaData = metaPropertyService.getValueJson(transactionBkashMetaCode, transactionBkashMetaName);

        List<String> transactionalBkashMaxAmount = transactionBkashMetaData.get(0).getValue();
        List<String> transactionalBkashMinAmount = transactionBkashMetaData.get(1).getValue();

        if (amount.compareTo(new BigDecimal(transactionalBkashMinAmount.get(0)))  == -1) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, AMOUNT_MIN_NOT_TRANSACTIONAL);
        }
        if (amount.compareTo(new BigDecimal(transactionalBkashMaxAmount.get(0))) == 1) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, AMOUNT_MAX_NOT_TRANSACTIONAL);
        }
        if (amount.compareTo(new BigDecimal(transactionalBkashMaxAmount.get(0))) == 0) {
            throw new ExceptionHandlerUtil(HttpStatus.NOT_ACCEPTABLE, AMOUNT_MAX_NOT_TRANSACTIONAL);
        }
    }*/


}
