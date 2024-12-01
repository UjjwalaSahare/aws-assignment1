package Policy_API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.message.SnsNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Policy_API.entity.*;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

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

     
        snsTemplate.sendNotification("sender", policyJson);

        return "Message published to SNS topic sender";
    }
    

}
