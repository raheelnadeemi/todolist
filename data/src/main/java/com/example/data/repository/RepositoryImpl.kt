package com.example.data.repository

import com.example.data.database.Dao
import com.example.data.transformation.Transformer
import com.example.domain.model.EntityModel
import com.example.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao,
    private val transformer: Transformer
) : Repository {

    override suspend fun insertItem(bean: EntityModel) {
        dao.insert(transformer.transformModelToBean(bean))
    }

    override suspend fun fetchItems(): List<EntityModel> {
        return dao.fetch().map {
            transformer.transformBeanToModel(it)
        }
    }



}