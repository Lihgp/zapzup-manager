package br.com.zapzup.manager.service.file.mapper

import br.com.zapzup.manager.domain.entity.File
import br.com.zapzup.manager.domain.to.file.FileTO

fun File.toTO() = fileTO(file = this)

fun FileTO.toEntity() = file(fileTO = this)

fun file(fileTO: FileTO): File =
    File(
        id = fileTO.id,
        name = fileTO.name,
        type = fileTO.type,
        fileByte = fileTO.fileByte,
        createdAt = fileTO.createdAt
    )

fun fileTO(file: File): FileTO =
    FileTO(
        id = file.id,
        name = file.name,
        type = file.type,
        fileByte = file.fileByte,
        createdAt = file.createdAt
    )