package policy_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfig {
	@Value("${spring.cloud.aws.endpoint}")
	String endpoint;
	
	@Value("${spring.cloud.aws.credentials.access-key}")
	String accesskey;
	
	@Value("${spring.cloud.aws.credentials.secret-key}")
	String secretkey;
	
	@Value("${spring.cloud.aws.region.static}")
	String region;
	
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
	return AmazonDynamoDBClientBuilder
			.standard()
			.withEndpointConfiguration(
					new AwsClientBuilder.EndpointConfiguration(endpoint, region))
			.withCredentials(new AWSStaticCredentialsProvider(
					new BasicAWSCredentials(accesskey, secretkey)))
			.build();
	}
	@Bean
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB());
    }
}
