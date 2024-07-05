package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransactionEntity;
import net.celloscope.api.transaction.entity.TransactionEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MobileRechargeTransactionRepository extends JpaRepository<MobileRechargeTransactionEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeTransactionEntity findByTransactionOid(String refId);

    @NotFound(action = NotFoundAction.IGNORE)
    MobileRechargeTransactionEntity findByRequestId(String requestId);

    @NotFound(action = NotFoundAction.IGNORE)
    @Query(value = "select * from (select distinct on (creditedaccount) creditedaccount, bankoid, transactionoid, intermediateStatus, " +
            "transactionrequestdate, cbsTransactionDate, debitedaccount, transtype, createdon, transAmount, chargeamount, vatamount, cbstransactiondate, transstatus, failurereason, " +
            "requestid, cbstraceno, cbsreferenceno, narration, remarks, traceid, debitedaccountoid, debitedaccountcustomeroid, " +
            "debitedaccountcbscustomerid, debitedaccountbranchoid, creditedaccountoid, creditedaccountcustomeroid, " +
            "creditedaccountcbscustomerid, creditedaccountbranchoid, currency, createdby, editedby, editedon, offsetotp, otpresentcount, " +
            "otpSentOn, otpVerifiedOn, ibUserOid,creditedAccountTitle, debitedAccountTitle  from transaction " +
            "where lower(transtype) = lower(?1) and debitedaccount in ?2) as x " +
            "order by createdon desc limit ?3", nativeQuery = true)
    List<TransactionEntity> findDistinctCreditedAccountByTransTypeIgnoreCaseAndDebitedAccountInOrderByCreatedOnDesc(String transType, List<String> debitedAccounts, int limit);

    @NotFound(action = NotFoundAction.IGNORE)
    @Query(
            value = "select * from \"transaction\" where createdon between ?1 and ?2 ",
            countQuery = "select count(*) from \"transaction\" where createdon between ?1 and ?2 ",
            nativeQuery = true)
    Page<TransactionEntity> findByTransactionNative(Date fromDate, Date toDate, Pageable pageable);

    @NotFound(action = NotFoundAction.IGNORE)
    @Query(
            value = "select * from \"transaction\" where createdon between ?1 and ?2 and " +
                    "( lower(transtype) like %?3%  or lower(transstatus) like %?3% or " +
                    "lower(cbsreferenceno) like %?3% or debitedaccount like %?3% or creditedaccount like %?3% ) ",
            countQuery = "select count(*) from \"transaction\" where createdon between ?1 and ?2 and " +
                    "( lower(transtype) like %?3%  or lower(transstatus) like %?3% or " +
                    "lower(cbsreferenceno) like %?3% or debitedaccount like %?3% or creditedaccount like %?3% ) ",
            nativeQuery = true)
    Page<TransactionEntity> findByTransactionNative(Date fromDate, Date toDate, String searchText, Pageable pageable);


    @NotFound(action = NotFoundAction.IGNORE)
    Page<TransactionEntity> findByCreatedBy(String userId, Pageable pageable);

    @NotFound(action = NotFoundAction.IGNORE)
    Page<TransactionEntity> findByCreatedByAndDebitedAccountOid(String userId, String accountOid, Pageable pageable);
}
