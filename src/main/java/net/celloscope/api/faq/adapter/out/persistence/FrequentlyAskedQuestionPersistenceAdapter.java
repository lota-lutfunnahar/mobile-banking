package net.celloscope.api.faq.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.LoadFrequentlyAskedQuestionCommand;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsGateway;
import net.celloscope.api.faq.application.port.out.FrequentlyAskedQuestionDsResponse;
import net.celloscope.api.core.common.PersistenceAdapter;
import net.celloscope.api.faq.domain.FrequentlyAskedQuestion;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
public class FrequentlyAskedQuestionPersistenceAdapter implements FrequentlyAskedQuestionDsGateway {

    private final FrequentlyAskedQuestionRepository frequentlyAskedQuestionRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public FrequentlyAskedQuestionDsResponse loadFrequentlyAskedQuestions(LoadFrequentlyAskedQuestionCommand request) throws ExceptionHandlerUtil {
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit(), Sort.by(Sort.Direction.ASC, "sortOrder"));
        Page<FrequentlyAskedQuestionJpaEntity> frequentlyAskedQuestionJpaEntityList = frequentlyAskedQuestionRepository.findAll(pageable);
        return new FrequentlyAskedQuestionDsResponse(
                modelMapper.map(frequentlyAskedQuestionJpaEntityList.getContent(), new TypeToken<List<FrequentlyAskedQuestion>>() {}.getType()),
                (int) frequentlyAskedQuestionJpaEntityList.getTotalElements());
    }
}
