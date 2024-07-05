package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.core.service.AbsClientService;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface GetBalanceInfo {
    BigDecimal getBalance(String accountNo) throws ExceptionHandlerUtil;
}
