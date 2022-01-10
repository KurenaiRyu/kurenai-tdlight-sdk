package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.User

data class ShippingQuery(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("from")
    val from: User,

    @JsonProperty("invoice_payload")
    val invoicePayload: String,

    @JsonProperty("shipping_address")
    val shippingAddress: ShippingAddress,
) {
}