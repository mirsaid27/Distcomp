package com.danilovfa.service

import com.danilovfa.database.tables.StickersDao
import com.danilovfa.model.sticker.Sticker
import io.ktor.server.plugins.*
import kotlin.jvm.Throws

class StickerService(
    private val dao: StickersDao
) {
    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun createSticker(name: String): Sticker {
        if (name.length !in 2..32) throw IllegalArgumentException("Wrong name length (not 2..32)")
        val id = dao.insertSticker(name)
        return getStickerById(id)
    }

    suspend fun getStickers(): List<Sticker> = dao.getStickers()

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun getStickerById(id: Long?): Sticker {
        if (id == null) throw IllegalArgumentException("Wring sticker id")
        return dao.getSticker(id) ?: throw NotFoundException("Sticker not found")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun updateSticker(id: Long, name: String): Sticker {
        if (name.length !in 2..32) throw IllegalArgumentException("Wrong name length (not 2..32)")

        return dao.updateSticker(id, name) ?: throw NotFoundException("StickerNotFound")
    }

    @Throws(NotFoundException::class, IllegalArgumentException::class)
    suspend fun deleteSticker(id: Long?) {
        if (id == null) throw IllegalArgumentException("Wrong sticker id")
        if(dao.deleteSticker(id).not()) throw NotFoundException("Sticker not found")
    }
}