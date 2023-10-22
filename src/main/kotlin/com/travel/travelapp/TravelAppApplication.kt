package com.travel.travelapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TravelAppApplication

fun main(args: Array<String>) {
	runApplication<TravelAppApplication>(*args)
}
