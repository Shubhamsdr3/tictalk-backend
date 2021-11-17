package com.pandey.shubham.data

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Referrer: Table<ReferrerEntity>(tableName = "referrer") {
    val userId = varchar("userId").primaryKey().bindTo { it.userId }
    val referrerId = varchar("referrerId").bindTo { it.referrerId }
}

interface ReferrerEntity : Entity<ReferrerEntity> {
    companion object: Entity.Factory<ReferrerEntity>()
    val userId: String
    val referrerId: String
}