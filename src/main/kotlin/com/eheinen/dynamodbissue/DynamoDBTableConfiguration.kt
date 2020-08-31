package com.eheinen.dynamodbissue

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Configuration
class DynamoDBTableConfiguration() {
    @Bean
    fun foodTable(
        dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        @Value("\${aws.dynamodb.tablePrefix}") tablePrefix: String
    ): DynamoDbAsyncTable<FoodDocument> {
        return dynamoDbEnhancedAsyncClient.table(
            "$tablePrefix$TABLE_NAME",
            TableSchema.fromBean(FoodDocument::class.java)
        )
    }

    @Bean
    fun foodIndex(
        dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        @Value("\${aws.dynamodb.tablePrefix}") tablePrefix: String
    ): DynamoDbAsyncIndex<FoodDocument> {
        return dynamoDbEnhancedAsyncClient.table(
            "$tablePrefix$TABLE_NAME",
            TableSchema.fromBean(FoodDocument::class.java)
        ).index(INDEX_BY_UPDATED_AT)
    }
}
