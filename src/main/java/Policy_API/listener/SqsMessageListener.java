package Policy_API.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Policy_API.entity.Policy;
import Policy_API.repository.PolicyRepository;
import io.awspring.cloud.sns.core.SnsTemplate;

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
	            policyRepository.save(policy);
	        }
	    }
}
