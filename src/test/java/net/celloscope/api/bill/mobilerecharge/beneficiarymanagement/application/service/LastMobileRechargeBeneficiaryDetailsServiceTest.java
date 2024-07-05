package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.service;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.api.LastRechargeResponseDto;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.api.MobileRechargeDto;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.IBUserPersistenceEntity;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.IBUserRepositoryForMobileRecharge;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryEntity;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.persistence.MobileRechargeBeneficiaryRepository;
import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.in.dto.LastMobileRechargeBeneficiaryResponse;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.core.util.Messages;
import net.celloscope.api.user.entity.IbUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LastMobileRechargeBeneficiaryDetailsServiceTest {
    @Autowired
    private LastMobileRechargeBeneficiaryDetailsService service;

    @MockBean
    private IBUserRepositoryForMobileRecharge ibUserRepository;

    @MockBean
    private MobileRechargeBeneficiaryRepository mobileRechargeBeneficiaryRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void contextLoad(){
        assertThat(service).isNotNull();
    }

    @Test
    void givenAValidUserId_returnsTheLastMobileRechargeBeneficiaryForThatUserId() throws Exception{
        //given
        String userId = "01521308198";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("USER_ID", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //when
        when(ibUserRepository.findByUserId(any())).thenReturn(Optional.of(IBUserPersistenceEntity.builder().ibUserOid(UUID.randomUUID().toString()).build()));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq( new HttpEntity<>(headers) ), eq( LastRechargeResponseDto.class), eq( uriParam ))).thenReturn(buildResponseEntity("OK"));
        when(mobileRechargeBeneficiaryRepository.findByUserIdAndBeneficiaryAccountNo(any(), any())).thenReturn(buildBeneficiaryEntity());
        //then
        LastMobileRechargeBeneficiaryResponse response = service.getLastMobileRechargeBeneficiary(userId);
        assertThat(response.getData().size()).isEqualTo(1);
        assertThat(response.getUserMessage()).isEqualTo(Messages.BENEFICIARY_LIST_RETREIVED_SUCCESSFULLY);

    }

    @Test
    void givenInvalidUserId_throwsNotFoundException(){
        //given
        String userId = "01521308198";
        //when
        when(ibUserRepository.findByUserId(any())).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> service.getLastMobileRechargeBeneficiary(userId)).isInstanceOf(ExceptionHandlerUtil.class).hasMessage(Messages.USER_NOT_FOUND);

    }

    @Test
    void givenValidUserId_throwsBeneficiaryNotFoundExceptionIfNoRechargeIsDonePreviously(){
        //given
        String userId = "01521308198";
        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("USER_ID", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //when
        when(ibUserRepository.findByUserId(any())).thenReturn(Optional.of(IBUserPersistenceEntity.builder().ibUserOid(UUID.randomUUID().toString()).build()));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq( new HttpEntity<>(headers) ), eq( LastRechargeResponseDto.class), eq( uriParam ))).thenReturn(buildResponseEntity("NOT_OK"));
        //then
        assertThatThrownBy(() -> service.getLastMobileRechargeBeneficiary(userId));
    }

    @Test
    void givenValidUserId_returnsAnEmptyListIfNoBeneficiaryFoundWithTheLastRechargedMobileNo() throws Exception{
        //given
        String userId = "01521308198";
        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("USER_ID", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //when
        when(ibUserRepository.findByUserId(any())).thenReturn(Optional.of(IBUserPersistenceEntity.builder().ibUserOid(UUID.randomUUID().toString()).build()));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq( new HttpEntity<>(headers) ), eq( LastRechargeResponseDto.class), eq( uriParam ))).thenReturn(buildResponseEntity("OK"));
        when(mobileRechargeBeneficiaryRepository.findByUserIdAndBeneficiaryAccountNo(any(), any())).thenReturn(Optional.empty());
        //then
        LastMobileRechargeBeneficiaryResponse response = service.getLastMobileRechargeBeneficiary(userId);
        assertThat(response.getUserMessage()).isEqualTo(Messages.BENEFICIARY_ACCOUNT_NOT_FOUND);
        assertThat(response.getData().size()).isEqualTo(0);
    }

    private ResponseEntity<LastRechargeResponseDto> buildResponseEntity(String status){
        MobileRechargeDto mobileRechargeDto = MobileRechargeDto.builder()
                .mobileNo("01716645196")
                .amount(10)
                .build();

        LastRechargeResponseDto lastRechargeResponse = LastRechargeResponseDto.builder()
                .status(status)
                .data(mobileRechargeDto)
                .build();

        return new ResponseEntity<>(lastRechargeResponse, HttpStatus.OK);
    }

    private Optional<MobileRechargeBeneficiaryEntity> buildBeneficiaryEntity(){
        return Optional.of(
                MobileRechargeBeneficiaryEntity.builder()
                        .beneficiaryAccountNo("01716645196")
                        .mobileRechargeBeneficiaryOid(UUID.randomUUID().toString())
                        .operator("ROBI")
                        .build()
        );
    }

}