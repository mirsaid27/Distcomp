package bsuir.dc.discussion

import com.datastax.oss.driver.api.core.CqlIdentifier
import org.springframework.context.annotation.Configuration
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer
import org.springframework.context.annotation.Bean

@Configuration
class CassandraConfig {

    @Bean
    fun cassandraSession(
        builder: CqlSessionBuilder,
        properties: CassandraProperties
    ): CqlSession {
        val keyspace = properties.keyspaceName

        CqlSessionBuilderCustomizer { it.withKeyspace(null as CqlIdentifier?) }
            .customize(builder)
        builder.build().use { session ->
            session.execute("""
                CREATE KEYSPACE IF NOT EXISTS $keyspace
                WITH replication = {'class':'SimpleStrategy','replication_factor':1}
            """.trimIndent())
        }

        return builder
            .withKeyspace(CqlIdentifier.fromCql(keyspace))
            .build()
    }
}
