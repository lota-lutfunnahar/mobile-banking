package net.celloscope.api.branchDetails.adapter.in.web;
import brave.Span;
import brave.Tracer;
import brave.Tracing;
import net.celloscope.api.branchDetails.application.port.in.GetBranchesByRegionNameUseCase;
import net.celloscope.api.branchDetails.application.port.in.GetRegionNameUseCase;
import net.celloscope.api.branchDetails.domain.Branch;
import net.celloscope.api.core.filter.RequestFilter;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BranchDetailsController.class)
class BranchDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    GetBranchesByRegionNameUseCase getBranchesByRegionName;
    @MockBean
    GetRegionNameUseCase getRegionNameUseCase;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private RequestFilter requestFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).addFilter(requestFilter).build();

        when(tracer.nextSpan()).thenReturn(span);
        when(tracer.nextSpan().name("mockSpan")).thenReturn(span);
        when(span.start()).thenReturn(span);

        Tracing tracing = Tracing.newBuilder().build();
        doReturn(tracing.tracer().withSpanInScope(span)).when(tracer).withSpanInScope(span);
        doNothing().when(span).finish();
    }

    @Mock
    private Tracer tracer;
    @Mock
    private Span span;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String requestId = RandomStringUtils.random(10, true, true);
    private static final String requestTime = DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
    private static final String requestTimeOutInSec = "30";
    private static final List<Branch> branchList = Arrays.asList(
            new Branch("sad56fd", "5sa55", "CHARIGRAM", "sfd", "54656", "34536", "DHAKA REGION"),
            new Branch("sadsadsa56fd", "dfd5", "sg", "sfsad", "546cdsf56", "3453346", "Lp"));
    private  static final List<String> regions = Arrays.asList("Dhaka Region", "Faridpur Region");

    @Test
    public void shouldReturnIsOkWhenRequestIsValid() throws Exception {
        String regionName = "DHAKA REGION";
        when(getBranchesByRegionName.getBranchesByRegionName(regionName)).thenReturn(branchList);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bkb/branches")
                        .header("Request-Timeout-In-Seconds", requestTimeOutInSec)
                        .header("Request-Id", requestId)
                        .header("Request-Time", requestTime)
                        .param("regionName", "DHAKA REGION")
                        .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenRequestIsNotValid() throws Exception {
        String regionName = "DHAKA REGION";
        when(getBranchesByRegionName.getBranchesByRegionName(regionName)).thenReturn(branchList);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bkb/branches")
                        .header("Request-Timeout-In-Seconds", requestTimeOutInSec)
                        .header("Request-Id", requestId)
                        .header("Request-Time", requestTime)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnIsOkWhenRequestForRegionsIsValid() throws Exception {
        when(getRegionNameUseCase.getRegionName()).thenReturn(regions);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bkb/regions")
                        .header("Request-Timeout-In-Seconds", requestTimeOutInSec)
                        .header("Request-Id", requestId)
                        .header("Request-Time", requestTime)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenRequestForRegionsIsNotValid() throws Exception {
        when(getRegionNameUseCase.getRegionName()).thenReturn(regions);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bkb/regions")
                        .header("Request-Timeout-In-Seconds", "")
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }
}