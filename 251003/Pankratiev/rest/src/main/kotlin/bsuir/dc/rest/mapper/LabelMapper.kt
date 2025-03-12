package bsuir.dc.rest.mapper

import bsuir.dc.rest.dto.from.LabelRequestTo
import bsuir.dc.rest.dto.to.LabelResponseTo
import bsuir.dc.rest.entity.Label

fun LabelRequestTo.toEntity(): Label = Label(
    id = id,
    name = name
)

fun Label.toResponse(): LabelResponseTo = LabelResponseTo(
    id = id,
    name = name
)