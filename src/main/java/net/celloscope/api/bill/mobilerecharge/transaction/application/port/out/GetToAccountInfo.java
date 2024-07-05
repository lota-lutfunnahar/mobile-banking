package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeAccountEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.MobileRechargeTransBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.Account;
import net.celloscope.api.core.util.ExceptionHandlerUtil;

import java.util.List;

public interface GetToAccountInfo {
    List<MobileRechargeTransBeneficiaryEntity>  findByBeneficiaryAccountNo(String beneficiaryAccountNo) throws ExceptionHandlerUtil;
    Account  findAccountInfo(String accountOid) throws ExceptionHandlerUtil;
}
