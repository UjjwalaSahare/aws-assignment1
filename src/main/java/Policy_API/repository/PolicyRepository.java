package Policy_API.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.data.repository.CrudRepository;
import com.amazonaws.auth.policy.Policy;
@EnableScan
@EnableDynamoDBRepositories
public interface PolicyRepository extends CrudRepository<Policy, String>  {
	
}
