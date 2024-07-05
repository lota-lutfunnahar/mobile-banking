package net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.adapter.out.api;

import net.celloscope.api.bill.mobilerecharge.beneficiarymanagement.application.port.out.LastMobileRechargeBeneficiary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class MobileRechargeAdapter implements LastMobileRechargeBeneficiary {
    private final RestTemplate restTemplate;
    private final String mobileRechargeBaseUrl;
    private final String lastRechargeUrl;

    public MobileRechargeAdapter(RestTemplate restTemplate, @Value("${mobile-recharge.baseurl}") String mobileRechargeBaseUrl, @Value("${mobile-recharge.last-recharge-url}") String lastRechargeUrl) {
        this.restTemplate = restTemplate;
        this.mobileRechargeBaseUrl = mobileRechargeBaseUrl;
        this.lastRechargeUrl = lastRechargeUrl;
    }

    @Override
    public Optional<String> loadFromMobileRechargeService(String userId) {
        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("USER_ID", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<LastRechargeResponseDto> response = restTemplate.exchange(
                mobileRechargeBaseUrl + lastRechargeUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LastRechargeResponseDto.class,
                uriParam
        );

        if (Objects.requireNonNull(response.getBody()).getStatus().equalsIgnoreCase("OK")) {
            if (response.getBody().getData() != null) return Optional.of(response.getBody().getData().getMobileNo());
        }

        return Optional.empty();
    }
}
