package policy_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import jakarta.validation.Valid;
import policy_api.entity.Policy;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
	  	@Autowired
	    private SnsTemplate snsTemplate;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @PostMapping
	    @ResponseBody
	    public String insertIntoDynamoDB(@RequestHeader("x-api-header") String apiKey, @Valid @RequestBody Policy policy) throws JsonProcessingException {
	        String policyJson = objectMapper.writeValueAsString(policy);

	     
	        snsTemplate.sendNotification("sender", SnsNotification.of(policyJson));

	        return "Message published to SNS topic sender";
	    }
}
