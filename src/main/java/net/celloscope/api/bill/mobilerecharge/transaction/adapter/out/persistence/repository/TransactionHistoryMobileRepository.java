package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository;

import com.google.common.base.Strings;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.TransactionHistory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TransactionHistoryMobileRepository {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public List<TransactionHistory> getTransactionHistoryList(String userId, String status, int offset, int limit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = "select t.debitedAccount as debitAccount, t.transactionOid as originatorConversationId , " +
                " m.beneficiaryAccountNo as creditAccount, m.shortName as creditAccountTitle, " +
                " t.remarks as remarks,  m.operator as operatorType , m.connectionType as connectionType, " +
                " trunc(t.transAmount,2) as debitAmount," +
                " t.bankOid as bankOid, t.requestId as transactionRefId " +
                " from MobileRechargeTransBeneficiaryEntity m " +
                " left join  MobileRechargeTransactionEntity t on t.customerAccount = m.beneficiaryAccountNo  " +
                " where t.createdBy = '" + userId + "' and t.transType = 'robi'" ;
/*
        if (!Strings.isNullOrEmpty(status) && status.equalsIgnoreCase("Pending")) {
            sql += " AND t.transStatus in ('Pending', 'waitingForReconcile')";
        }else if (!Strings.isNullOrEmpty(status)) {
            sql += " AND t.transStatus = '" + status + "'";
        }*/

        sql +=  " order by t.createdOn desc";

        Query query = entityManager.createQuery(sql);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);


        @SuppressWarnings({ "deprecation", "unchecked" })
        List<TransactionHistory> list = query.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(TransactionHistory.class)).list();

        entityManager.close();
        return list;
    }

    /*public int countTransactionList(String userId, String bankAccountNo, String status) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = "select count(t.bkashTransactionOid) " +
                "from BkashTransactionJpaEntity t where t.createdBy = '" + userId + "' " ;

        if (!Strings.isNullOrEmpty(bankAccountNo)
                && !bankAccountNo.equalsIgnoreCase("undefined") && !bankAccountNo.equalsIgnoreCase("null")) {
            sql += " AND t.debitedAccount = '" + bankAccountNo + "'";
        }

        if (!Strings.isNullOrEmpty(status) && status.equalsIgnoreCase("Pending")) {
            sql += " AND t.transStatus in ('Pending', 'waitingForReconcile')";
        }else if (!Strings.isNullOrEmpty(status)) {
            sql += " AND t.transStatus = '" + status + "'";
        }

        Query query = entityManager.createQuery(sql);

        Long count = (Long) query.getSingleResult();

        entityManager.close();
        return count.intValue();
    }*/
}
