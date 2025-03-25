package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.LabelRequestTo
import bsuir.dc.rest.dto.to.LabelResponseTo
import bsuir.dc.rest.entity.Label

fun LabelRequestTo.toEntity() = Label(
    id = this.id,
    name = this.name
)

fun Label.toResponse() = LabelResponseTo(
    id = this.id,
    name = this.name
)
