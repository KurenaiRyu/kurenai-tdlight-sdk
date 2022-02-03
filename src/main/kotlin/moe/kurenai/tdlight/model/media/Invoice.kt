package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
class Invoice {
    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("start_parameter")
    var startParameter: String? = null

    @JsonProperty("currency")
    var currency: String? = null

    @JsonProperty("total_amount")
    var totalAmount = 0
}