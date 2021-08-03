package com.esper.devices.domain.source.local.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "features_table")
data class Feature(
    @PrimaryKey(autoGenerate = false)
    val feature_id: String,
    val name: String
)

@Entity(
    tableName = "options_table",
    foreignKeys = [ForeignKey(
        entity = Feature::class,
        parentColumns = arrayOf("feature_id"),
        childColumns = arrayOf("feature_feature_id"),
        onDelete = CASCADE
    )]
)
data class Option(
    val name: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val feature_feature_id: String
)

data class FeatureWithOptions(
    @Embedded val user: Feature,
    @Relation(
        parentColumn = "feature_id",
        entityColumn = "feature_feature_id"
    )
    val playlists: List<Option>
)