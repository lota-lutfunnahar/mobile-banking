package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

public interface ValidCountReferenceStatus {
    int  countByReferenceOidAndStatusContainingIgnoreCase(String TransactionOid, String invalid);
}
