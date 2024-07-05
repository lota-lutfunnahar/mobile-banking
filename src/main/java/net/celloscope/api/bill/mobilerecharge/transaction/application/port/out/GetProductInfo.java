package net.celloscope.api.bill.mobilerecharge.transaction.application.port.out;

import net.celloscope.api.bill.mobilerecharge.transaction.domain.Product;

public interface GetProductInfo {
    Product findByProductOid(String productOid);
}
