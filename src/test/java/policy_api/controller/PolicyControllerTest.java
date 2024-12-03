package policy_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import policy_api.entity.Policy;

public class PolicyControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private SnsTemplate snsTemplate;

    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private PolicyController policyController ;

    private Policy policy;
    private String policyJson;
    
    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(policyController).build();
        policy.setPolicyId("123");
        policy.setPolicyName("Test Policy");
        policyJson = new ObjectMapper().writeValueAsString(policy);
    }
    @Test
    public void testInsertIntoDynamoDB() throws Exception {
        when(objectMapper.writeValueAsString(any(Policy.class))).thenReturn(policyJson);

        mockMvc.perform(post("/api/policies")
                        .header("x-api-header", "ValidApiKey12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(policyJson))
                .andExpect(status().isOk());

        verify(snsTemplate).sendNotification(eq("sender"), any(SnsNotification.class));
    }
}
