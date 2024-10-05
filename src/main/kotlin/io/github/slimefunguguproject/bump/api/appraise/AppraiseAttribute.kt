package io.github.slimefunguguproject.bump.api.appraise

import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier.Operation

data class AppraiseAttribute(
    val attribute: Attribute,
    val min: Double,
    val max: Double,
    val operation: Operation = Operation.ADD_NUMBER
) {

    constructor(
        attribute: Attribute,
        min: Double,
        max: Double,
        weight: Double,
        operation: Operation = Operation.ADD_NUMBER
    ) : this(attribute, min, max, operation) {
        this.weight = weight
    }

    init {
        if (min > max) error("Min value cannot be greater than max value.")
    }

    /**
     * The weight is meant to set on init, or not set and will be calculated later.
     */
    var weight: Double = UNSET_WEIGHT
        set(value) {
            require(value == UNSET_WEIGHT || value in 0.0..100.0) { "Weight must be between 0 and 100." }
            require(field < 0.0) { "Cannot set weight after the weight is already set." }
            field = value
        }

    override fun toString() =
        "AppraiseAttribute(attribute=$attribute, min=$min, max=$max, weight=$weight, operation=$operation)"

    /**
     * Get the percent of result [value] within range.
     */
    fun getPercent(value: Double): Double {
        return if (value <= min) {
            0.0
        } else if (value >= max) {
            100.0
        } else {
            (value - min) / (max - min) * 100.0
        }
    }

    /**
     * Get the weighted percent of result [value].
     */
    fun getWeightedPercent(value: Double) = getPercent(value) * weight / 100.0

    companion object {

        const val UNSET_WEIGHT = -1.0
    }
}
