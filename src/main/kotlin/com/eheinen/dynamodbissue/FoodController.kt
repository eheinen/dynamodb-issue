package com.eheinen.dynamodbissue

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Instant
import java.time.temporal.ChronoUnit

@RestController
class FoodController(
    private val foodRepository: FoodRepository
) {

    @GetMapping("/food")
    fun getFood(): Flux<FoodDocument> {
        val foodDocument = FoodDocument("1", "1", State.COOKING, Instant.now().minus(3, ChronoUnit.YEARS))

        foodRepository.saveFood(foodDocument)

        return foodRepository.findFood("1", Instant.now().minus(5, ChronoUnit.YEARS), Instant.now())
    }
}
