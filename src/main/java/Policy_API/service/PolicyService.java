package Policy_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.policy.Policy;

import Policy_API.repository.PolicyRepository;

@Service
public class PolicyService {
    
    @Autowired
    private PolicyRepository policyRepository;

    public void savePolicy(Policy policy) {
    	policyRepository.save(policy);
        
    }

}
