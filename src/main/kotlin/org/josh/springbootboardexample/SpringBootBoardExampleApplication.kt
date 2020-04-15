package org.josh.springbootboardexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SpringBootBoardExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringBootBoardExampleApplication>(*args)
}
