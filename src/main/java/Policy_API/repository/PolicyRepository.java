package Policy_API.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;

import Policy_API.entity.Policy;

@EnableScheduling
@EnableJpaRepositories
public interface PolicyRepository extends CrudRepository<Policy, String>  {
	
}
