package policy_api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import policy_api.entity.Policy;

@Repository
public class PolicyRepository {
	@Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void savePolicy(Policy policy) {
        dynamoDBMapper.save(policy);
    }
}
