package net.celloscope.api.restriction.adapter.out.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
@NoArgsConstructor
@RedisHash(value = "BeneficiarySearchRestriction")
public class BeneficiarySearchRestriction implements Serializable {

    @Id
    @org.springframework.data.annotation.Id
    private String userId;

    private ConcurrentLinkedQueue<Date> dates;

    public BeneficiarySearchRestriction(String userId, ConcurrentLinkedQueue<Date> dates) {
        this.userId = userId;
        this.dates = dates;
    }
}