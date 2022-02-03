package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.User

@NoArg
class ProximityAlertTriggered {
    @JsonProperty("traveler")
    var traveler: User? = null

    @JsonProperty("watcher")
    var watcher: User? = null

    @JsonProperty("distance")
    var distance: User? = null
}