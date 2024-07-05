package net.celloscope.api.bill.mobilerecharge.transaction.application.port.in;

import net.celloscope.api.account.entity.AccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeAccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeCustomerEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Customer;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.IBUser;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.reactivestreams.Publisher;

import java.math.BigDecimal;
import java.util.List;

public interface MobileRechargeValidatorUseCase {
    void validateUser(IBUser user) throws ExceptionHandlerUtil;
    void isDebitCreditAccountDifferent(Account debitAccount, String creditAccount) throws ExceptionHandlerUtil;
    void isCreditAccountFromSameCustomer(List<MobileRechargeCustomerEntity> customers, String creditAccount) throws ExceptionHandlerUtil;
    void isDebitAccountActive(Account fromAccount) throws ExceptionHandlerUtil;
    void isAccountActive(Account account, String accountPrefix) throws ExceptionHandlerUtil;
    void isDebitAccountTransactional(Account fromAccount) throws ExceptionHandlerUtil;
    void isAccountTransactional(Account account, String accountPrefix) throws ExceptionHandlerUtil;
    void checkTransactionProfile(String accountNo, String product, BigDecimal transactionAmount) throws ExceptionHandlerUtil;
    void isBalanceGraterThenTotal(BigDecimal workingBalance, BigDecimal totalAmount) throws ExceptionHandlerUtil;
    void validateAccountPostingRestrictionAndProductDefinitionForDebitAccount(Account debitAccount) throws ExceptionHandlerUtil;

}
