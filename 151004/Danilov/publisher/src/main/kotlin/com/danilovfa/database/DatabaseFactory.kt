package com.danilovfa.database

import com.danilovfa.database.tables.StickersTable
import com.danilovfa.database.tables.StoriesTable
import com.danilovfa.database.tables.UsersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object DatabaseFactory {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun connectAndMigrate() {
        log.info("Initialising database")
        val pool = hikari()
        Database.connect(pool)
        transaction {
            SchemaUtils.create(
                StickersTable,
                StoriesTable,
                UsersTable
            )
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig("/hikari.properties").apply {
            username = System.getenv("POSTGRES_USER") ?: "postgres"
            password = System.getenv("POSTGRES_PASSWORD") ?: "postgres"
            addDataSourceProperty("portNumber", System.getenv("POSTGRES_PORT")?.toIntOrNull() ?: 5432)
            addDataSourceProperty("databaseName", System.getenv("POSTGRES_DB") ?: "distcomp")
            addDataSourceProperty("serverName", System.getenv("POSTGRES_SERVER_NAME") ?: "db")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    private fun runFlyway(datasource: DataSource) {
        val flyway = Flyway.configure().dataSource(datasource).load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            log.error("Exception running flyway migration", e)
            throw e
        }
        log.info("Flyway migration has finished")
    }

    suspend fun <T> dbExec(
        block: () -> T
    ): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }
}