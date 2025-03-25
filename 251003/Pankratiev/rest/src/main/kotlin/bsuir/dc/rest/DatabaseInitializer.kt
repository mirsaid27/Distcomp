package bsuir.dc.rest

import bsuir.dc.rest.entity.Writer
import bsuir.dc.rest.repository.WriterRepository
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class DatabaseInitializer(
    private val writerRepository: WriterRepository
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val writer = Writer(
            login = "egor.pankratiew@gmail.com",
            password = "******",
            firstname = "Егор",
            lastname = "Панкратьев"
        )
        writerRepository.save(writer)
    }
}
