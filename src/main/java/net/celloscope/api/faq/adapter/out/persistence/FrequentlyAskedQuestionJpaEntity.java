package net.celloscope.api.faq.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "frequentlyaskedquestion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrequentlyAskedQuestionJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "frequentlyaskedquestionoid")
    private String frequentlyAskedQuestionOid;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "sortorder")
    private int sortOrder;

}
