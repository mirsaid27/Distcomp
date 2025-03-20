package bsuir.dc.rest.repository

import bsuir.dc.rest.entity.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<Label, Long> {
}
