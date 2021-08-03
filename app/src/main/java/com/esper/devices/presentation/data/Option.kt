package com.esper.devices.presentation.data

import java.io.Serializable

data class Option(
    val optionId: Int,
    val name: String,
    val iconUrl: String
) : Serializable {
    var isSelected: Boolean = false
    var isAllowed: Boolean = true

    override fun hashCode(): Int {
        return optionId
    }

    override fun equals(other: Any?): Boolean {
        return other.hashCode() == this.hashCode()
    }

    override fun toString(): String {
        return "\n (ID - ${this.optionId}, Name - ${this.name}, isSelected - ${this.isSelected}, " +
                "isAllowed - ${this.isAllowed})"
    }
}
