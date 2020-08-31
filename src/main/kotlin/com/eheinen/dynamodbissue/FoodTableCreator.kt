package com.eheinen.dynamodbissue

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedLocalSecondaryIndex
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.Projection
import software.amazon.awssdk.services.dynamodb.model.ProjectionType
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput

private const val READ_CAPACITY_UNITS = 1L
private const val WRITE_CAPACITY_UNITS = 1L

@Component
class FoodTableCreator(
    private val dynamoDbTableCreator: DynamoDBTableCreator,
    private val foodTable: DynamoDbAsyncTable<FoodDocument>,
    private val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun createTable() {
        val enhancedGlobalSecondaryIndex = EnhancedLocalSecondaryIndex
            .builder()
            .indexName(INDEX_BY_UPDATED_AT)
            .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
            .build()

        val provisionedThroughput = ProvisionedThroughput
            .builder()
            .readCapacityUnits(READ_CAPACITY_UNITS)
            .writeCapacityUnits(WRITE_CAPACITY_UNITS)
            .build()

        val createTableEnhancedRequest = CreateTableEnhancedRequest
            .builder()
            .localSecondaryIndices(enhancedGlobalSecondaryIndex)
            .provisionedThroughput(provisionedThroughput)
            .build()

        dynamoDbTableCreator.createTable(foodTable, createTableEnhancedRequest)
    }
}
