package com.eheinen.dynamodbissue

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import java.math.BigDecimal
import java.time.Instant

const val TABLE_NAME = "food"
const val INDEX_BY_UPDATED_AT = "index_byUpdatedAt"

@DynamoDbBean
data class FoodDocument(

    @get:DynamoDbSortKey
    var id: String = "",

    @get:DynamoDbPartitionKey
        var restaurantId: String = "",

    var state: State? = null,

    @get:DynamoDbSecondarySortKey(indexNames = [INDEX_BY_UPDATED_AT])
    var updatedAt: Instant? = null
)
