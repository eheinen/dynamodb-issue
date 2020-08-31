package com.eheinen.dynamodbissue

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * This listener is only responsible for create table on DynamoDB local.
 */
@Component
class DynamoDBSetupAfterStartupListener(
    private val foodTableCreator: FoodTableCreator
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        foodTableCreator.createTable()
    }
}
