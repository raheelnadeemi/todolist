package com.example.data.transformation

import com.example.data.database.TodoEntity
import com.example.domain.wrapper.TodoItem


fun TodoItem.toData(): TodoEntity {
    return TodoEntity(
        id = this.id,
        item = this.item
    )
}

fun TodoEntity.toDomain(): TodoItem {
    return TodoItem(
        id = this.id,
        item = this.item
    )
}