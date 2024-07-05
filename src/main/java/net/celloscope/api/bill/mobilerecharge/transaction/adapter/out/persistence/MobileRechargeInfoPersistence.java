package net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.entity.*;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.mapper.UserMapper;
import net.celloscope.api.bill.mobilerecharge.transaction.adapter.out.persistence.repository.*;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetProductInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetToAccountInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.application.port.out.GetUserInfo;
import net.celloscope.api.bill.mobilerecharge.transaction.domain.*;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
class MobileRechargeInfoPersistence implements GetUserInfo, GetProductInfo, GetToAccountInfo {

    private final MobileRechargeIbUserRepository ibUserRepository;
    private final MobileRechargeIbConsumerRepository ibConsumerRepository;
    private final MobileRechargeProductRepository productRepository;
    private final MobileRechargeTransBeneficiaryRepository beneficiaryRepository;
    private final MobileRechargeAccountRepository accountRepository;
    private final ModelMapper mapper;
    private final UserMapper userMapper;

    @Override
    public Product findByProductOid(String productOid) {
        MobileRechargeProductEntity productEntity = productRepository.findByProductOid(productOid);
        return mapper.map(productEntity, Product.class);
    }

    @Override
    public List<MobileRechargeTransBeneficiaryEntity> findByBeneficiaryAccountNo(String beneficiaryAccountNo) throws ExceptionHandlerUtil {
        List<MobileRechargeTransBeneficiaryEntity> beneficiaryEntity = beneficiaryRepository.findByBeneficiaryAccountNo(beneficiaryAccountNo);
        if(beneficiaryEntity == null){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found");
        }
        return beneficiaryEntity;
    }

    @Override
    public Account findAccountInfo(String accountOid) throws ExceptionHandlerUtil {
        AccountSingleEntity entities = accountRepository.findByAccountOid(accountOid);
        return mapper.map(entities, Account.class);
    }

    @Override
    public IBUser findByUserId(String userId) throws ExceptionHandlerUtil {
        MobileRechargeIbUserEntity userEntity = ibUserRepository.findByUserId(userId);
        if(userEntity == null){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found");
        }
        return userMapper.mapToDomainEntity(userEntity);
    }

    @Override
    public MobileRechargeIbConsumerEntity findByIbUserOid(String ibUserOid) throws ExceptionHandlerUtil {
        MobileRechargeIbConsumerEntity consumerEntity = ibConsumerRepository.findByIbUserOid(ibUserOid);
        return consumerEntity;
    }
}
