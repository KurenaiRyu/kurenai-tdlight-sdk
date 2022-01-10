package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.User

data class PreCheckoutQuery(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("from")
    val from: User,

    @JsonProperty("currency")
    val currency: String,

    @JsonProperty("total_amount")
    val totalAmount: Int = 0,

    @JsonProperty("invoice_payload")
    val invoicePayload: String,

    @JsonProperty("shipping_option_id")
    val shippingOptionId: String? = null,

    @JsonProperty("order_info")
    val orderInfo: OrderInfo? = null,
) {
}