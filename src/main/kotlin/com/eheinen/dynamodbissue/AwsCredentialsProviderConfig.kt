package com.eheinen.dynamodbissue

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider

@Configuration
class AwsCredentialsProviderConfig {

    @Bean
    @Primary
    fun defaultCredentials(
        @Value("\${aws.accessKey}") accessKey: String,
        @Value("\${aws.secretKey}") secretKey: String
    ): AwsCredentialsProvider {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))
    }
}
