package com.eheinen.dynamodbissue

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
class DynamoConfig(
    @Value("\${aws.region}") private val awsRegion: String,
    private val awsCredentialsProvider: AwsCredentialsProvider
) {

    @Bean(name = ["amazonDynamoDB"])
    fun amazonDynamoDBLocalAsyncClient(@Value("\${aws.dynamodb.endpoint}") endpoint: String): DynamoDbAsyncClient {
        return DynamoDbAsyncClient.builder()
            .endpointOverride(URI.create(endpoint))
            .region(Region.of(awsRegion))
            .credentialsProvider(awsCredentialsProvider)
            .build()
    }

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbAsyncClient: DynamoDbAsyncClient): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(dynamoDbAsyncClient).build()
    }
}
