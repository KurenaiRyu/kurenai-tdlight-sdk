package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import java.util.*

@NoArg
class Location {
    @JsonProperty("longitude")
    var longitude = 0f

    @JsonProperty("latitude")
    var latitude = 0f

    @JsonProperty("horizontal_accuracy")
    var horizontalAccuracy: Optional<Float>? = null

    @JsonProperty("live_period")
    var livePeriod: OptionalInt? = null

    @JsonProperty("heading")
    var heading: OptionalInt? = null

    @JsonProperty("proximity_alert_radius")
    var proximityAlertRadius: OptionalInt? = null
}