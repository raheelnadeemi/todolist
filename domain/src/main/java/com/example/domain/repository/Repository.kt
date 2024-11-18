package com.example.domain.repository

import com.example.domain.model.EntityModel

interface Repository {

    suspend fun insertItem(bean: EntityModel)

    suspend fun fetchItems(): List<EntityModel>
}