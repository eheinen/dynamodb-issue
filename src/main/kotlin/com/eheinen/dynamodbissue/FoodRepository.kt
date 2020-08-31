package com.eheinen.dynamodbissue

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest
import java.time.Instant

@Repository
class FoodRepository(
    private val foodTable: DynamoDbAsyncTable<FoodDocument>,
    private val foodIndex: DynamoDbAsyncIndex<FoodDocument>
) {
    fun saveFood(foodDocument: FoodDocument) = Mono.fromFuture(foodTable.putItem(foodDocument))

    fun findFood(
        restaurantId: String,
        updatedAtFrom: Instant,
        updatedAtTo: Instant
    ): Flux<FoodDocument> {
        val queryConditional = QueryConditional.sortBetween(
            Key.builder()
                .partitionValue(restaurantId)
                .sortValue(updatedAtFrom.toString())
                .build(),
            Key.builder()
                .partitionValue(restaurantId)
                .sortValue(updatedAtTo.toString())
                .build()
        )

        val queryEnhancedRequest = QueryEnhancedRequest
            .builder()
            .queryConditional(queryConditional)
            .build()

        return foodIndex
            .query(queryEnhancedRequest)
            .flatMapIterable { it.items() }
            .toFlux()
    }
}
