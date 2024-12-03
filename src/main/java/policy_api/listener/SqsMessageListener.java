package policy_api.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import policy_api.entity.Policy;
import policy_api.repository.PolicyRepository;

@Component
public class SqsMessageListener {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SnsTemplate snsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

	@Autowired
	private PolicyRepository policyRepository;

    @SqsListener("listener")
    public void listen(String message) throws JsonProcessingException {
    	log.info("message - {}", message);
        JsonNode rootNode = objectMapper.readTree(message);
        String policyJson = rootNode.get("Message").asText();
        log.info("read the json - {}", policyJson);
        Policy policy = objectMapper.readValue(policyJson, Policy.class);
        log.info("jaimini - {}, {}, {}", policy.getPolicyId(), policy.getPolicyName());
        if (policy.getPolicyName() != null && !policy.getPolicyName().isEmpty()) {
            policyRepository.savePolicy(policy);
        }
    }
}
