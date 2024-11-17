package com.example.domain.transformation

import com.example.data.bean.EntityBean
import com.example.todolist.model.EntityModel
import javax.inject.Inject

class Transformer @Inject constructor() {

    fun transformModelToBean(model: EntityModel): EntityBean {
        return EntityBean(
            id = model.id,
            item = model.item
        )
    }

    fun transformBeanToModel(bean: EntityBean): EntityModel {
        return EntityModel(
            id = bean.id,
            item = bean.item
        )
    }
}