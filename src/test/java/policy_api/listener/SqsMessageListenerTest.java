package policy_api.listener;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.sns.core.SnsTemplate;
import policy_api.entity.Policy;
import policy_api.repository.PolicyRepository;

@ExtendWith(MockitoExtension.class)
public class SqsMessageListenerTest {
	@Mock
    private SnsTemplate snsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private SqsMessageListener sqsMessageListener;

    private String mockMessage;
    private Policy policy;
    private String policyJson;
    private String snsMessage;
    
    @BeforeEach
    public void setUp() throws Exception {

        policy.setPolicyId("123");
        policy.setPolicyName("Test Policy");
        ObjectMapper realObjectMapper = new ObjectMapper();
        policyJson = realObjectMapper.writeValueAsString(policy);
        System.out.println("Constructed policyJson: " + policyJson);

        // Escape quotes in policyJson for inclusion in snsMessage
        String escapedPolicyJson = policyJson.replace("\"", "\\\"");
        snsMessage = "{\\\\\"policyId\\\\\":\\\\\"123\\\\\",\\\\\"policyName\\\\\":\\\\\"Health Insurance\\\\\"}";

        // Debugging log to check snsMessage
        System.out.println("Constructed snsMessage: " + snsMessage);

        // Mock object mapper behavior
        JsonNode rootNode = realObjectMapper.readTree(snsMessage);
        String extractedPolicyJson = rootNode.get("Message").asText();
        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        when(objectMapper.readValue(anyString(), eq(Policy.class))).thenReturn(policy);
    }
    @Test
    public void testListen() throws Exception {
    	sqsMessageListener.listen(snsMessage);

        verify(policyRepository).savePolicy(policy);
        verifyNoMoreInteractions(policyRepository);
    }
}
