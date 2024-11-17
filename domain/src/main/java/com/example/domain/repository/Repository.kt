package com.example.domain.repository

import com.example.data.bean.EntityBean

interface Repository {

    suspend fun insertItem(bean: EntityBean)

    suspend fun fetchItems(): List<EntityBean>
}