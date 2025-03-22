package bsuir.dc.rest.repository

import bsuir.dc.rest.entity.Writer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WriterRepository : JpaRepository<Writer, Long> {
    fun findByLoginIgnoreCase(login: String): Writer?
}