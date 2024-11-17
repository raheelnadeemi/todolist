package com.example.data.repository

import com.example.data.bean.EntityBean
import com.example.data.database.Dao
import com.example.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dao: Dao) : Repository {

    override suspend fun insertItem(bean: EntityBean) {
        dao.insert(bean)
    }

    override suspend fun fetchItems(): List<EntityBean> =
        dao.fetch()


}