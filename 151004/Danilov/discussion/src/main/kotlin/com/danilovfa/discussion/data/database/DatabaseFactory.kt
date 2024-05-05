package com.danilovfa.discussion.data.database

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session


class DatabaseFactory {
    companion object {
        fun createAndConnect(): Session {
            val cluster = Cluster.builder()
                .withoutMetrics()
                .addContactPoints("cassandra1", "cassandra2", "cassandra3")
                .build()

            return cluster.connect("distcomp")
        }
    }
}