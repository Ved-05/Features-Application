package com.esper.devices.presentation.data

data class Feature(
    val featureId: Int,
    val name: String,
    val options: Map<Int, Option>
) {
    override fun hashCode(): Int {
        return featureId
    }

    override fun equals(other: Any?): Boolean {
        return other.hashCode() == this.hashCode()

    }

    override fun toString(): String {
        return "\n (Feature ID - ${this.featureId}, Name - ${this.name}, Options - {$options})"
    }
}
