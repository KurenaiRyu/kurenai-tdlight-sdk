package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.Optional
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.media.Animation
import java.util.OptionalInt
import moe.kurenai.tdlight.model.encrypt.EncryptedPassportElement
import moe.kurenai.tdlight.model.encrypt.EncryptedCredentials
import moe.kurenai.tdlight.model.media.ShippingAddress
import moe.kurenai.tdlight.model.media.OrderInfo

class SuccessfulPayment {
    @JsonProperty("currency")
    var currency: String? = null

    @JsonProperty("total_amount")
    var totalAmount = 0

    @JsonProperty("invoice_payload")
    var invoicePayload: String? = null

    @JsonProperty("shipping_option_id")
    var shippingOptionId: Optional<String>? = null

    @JsonProperty("order_info")
    var orderInfo: Optional<OrderInfo>? = null

    @JsonProperty("telegram_payment_charge_id")
    var telegramPaymentChargeId: String? = null

    @JsonProperty("provider_payment_charge_id")
    var providerPaymentChargeId: String? = null
}