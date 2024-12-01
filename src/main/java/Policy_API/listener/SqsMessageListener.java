package Policy_API.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Policy_API.entity.*;
import Policy_API.repository.*;
import io.awspring.cloud.sns.core.SnsTemplate;

@Component
public class SqsMessageListener {

    	@Autowired
    	private SnsTemplate snsTemplate;
	
	    @Autowired
	    private ObjectMapper objectMapper;

		@Autowired
		private PolicyRepository policyRepository;

	    @SqsListener("listener")
	    public void listen(String message) throws JsonProcessingException {
	        JsonNode rootNode = objectMapper.readTree(message);
	        String policyJson = rootNode.get("Message").asText();
	        Policy policy = objectMapper.readValue(policyJson, Policy.class);
	        if (policy.getPolicyName() != null && !policy.getPolicyName().isEmpty()) {
	            policyRepository.save(policy);
	        }
	    }
}
