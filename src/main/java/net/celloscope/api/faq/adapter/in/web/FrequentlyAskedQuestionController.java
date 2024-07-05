package net.celloscope.api.faq.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.celloscope.api.core.util.ExceptionHandlerUtil;
import net.celloscope.api.faq.application.port.in.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping("/v1/banks/{BANK_ID}/faq")
public class FrequentlyAskedQuestionController {

 private final LoadFrequentlyAskedQuestionUseCase loadFrequentlyAskedQuestionUseCase;

    @GetMapping()
    public ResponseEntity<FrequentlyAskedQuestionsResponse> getFrequentlyAskedQuestion(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit,
            @RequestParam(name = "searchText", defaultValue = "") String searchText){
        try{
            log.info("Request received for getting frequently asked question");
            FrequentlyAskedQuestionsResponse response = loadFrequentlyAskedQuestionUseCase.loadFrequentlyAskedQuestion(
                    new LoadFrequentlyAskedQuestionCommand(offset, limit, searchText));
            log.info("Response send for getting frequently question, response: {}", response);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
//            return ResponseEntity<String>(test,responseHeaders, HttpStatus.OK);
            return  new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
}
