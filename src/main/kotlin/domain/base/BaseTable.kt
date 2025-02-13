package domain.base

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


open class BaseTable(name: String) : Table(name) {
    val createdAt = datetime("created_at")
    val createdBy = long("created_by")
    val lastModifiedAt = datetime("last_modified_at")
    val lastModifiedBy = long("last_modified_by")
}