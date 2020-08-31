package com.eheinen.dynamodbissue

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException

@Component
class DynamoDBTableCreator {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun <T> createTable(dynamoDBTable: DynamoDbAsyncTable<T>, createTableEnhancedRequest: CreateTableEnhancedRequest) {
        log.info("Creating table '${dynamoDBTable.tableName()}'.")

        dynamoDBTable.createTable(createTableEnhancedRequest)
            .thenAccept {
                log.info("Table '${dynamoDBTable.tableName()}' created.")
            }.exceptionally { exception ->
                when (exception.cause) {
                    is ResourceInUseException -> {
                        log.info("Table '${dynamoDBTable.tableName()}' already exists. Skipping creation.")
                        return@exceptionally null
                    }
                    else -> {
                        log.error("Error creating table '${dynamoDBTable.tableName()}'", exception)
                        throw exception
                    }
                }
            }.join()
    }
}
