package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.Chat
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardMarkup
import moe.kurenai.tdlight.model.media.*
import moe.kurenai.tdlight.model.poll.Poll
import moe.kurenai.tdlight.model.voice.Voice
import moe.kurenai.tdlight.model.voice.VoiceChatEnded
import moe.kurenai.tdlight.model.voice.VoiceChatParticipantsInvited
import moe.kurenai.tdlight.model.voice.VoiceChatScheduled

data class Message(
    @JsonProperty("message_id") val messageId: Long? = null,
    @JsonProperty("from") val user: User? = null,
    @JsonProperty("sender_chat") val senderChat: Chat? = null,
    @JsonProperty("date") val date: Long,
    @JsonProperty("chat") val chat: Chat,
    @JsonProperty("forward_from") val forwardFrom: User? = null,
    @JsonProperty("forward_from_chat") val forwardFromChat: Chat? = null,
    @JsonProperty("forward_from_message_id") val forwardFromMessageId: Int? = null,
    @JsonProperty("forward_signature") val forwardSignature: String? = null,
    @JsonProperty("forward_sender_name") val forwardSenderName: String? = null,
    @JsonProperty("forward_date") val forwardDate: Int? = null,
    @JsonProperty("is_automatic_forward") val isAutomaticForward: Boolean,
    @JsonProperty("reply_to_message") val replyToMessage: Message? = null,
    @JsonProperty("via_bot") val viaBot: User? = null,
    @JsonProperty("edit_date") val editDate: Int? = null,
    @JsonProperty("has_protected_content") val hasProtectedContent: Boolean,
    @JsonProperty("media_group_id") val mediaGroupId: String? = null,
    @JsonProperty("author_signature") val authorSignature: String? = null,
    @JsonProperty("text") val text: String? = null,
    @JsonProperty("entities") val entities: List<MessageEntity>? = null,
    @JsonProperty("animation") val animation: Animation? = null,
    @JsonProperty("audio") val audio: Audio? = null,
    @JsonProperty("document") val document: Document? = null,
    @JsonProperty("photo") val photo: List<PhotoSize>? = null,
    @JsonProperty("sticker") val sticker: Sticker? = null,
    @JsonProperty("video") val video: Video? = null,
    @JsonProperty("video_note") val video_note: VideoNote? = null,
    @JsonProperty("voice") val voice: Voice? = null,
    @JsonProperty("caption") val caption: String? = null,
    @JsonProperty("caption_entities") val captionEntities: List<MessageEntity>? = null,
    @JsonProperty("contact") val contact: Contact? = null,
    @JsonProperty("dice") val dice: Dice? = null,
    @JsonProperty("game") val game: Game? = null,
    @JsonProperty("poll") val poll: Poll? = null,
    @JsonProperty("location") val location: Location? = null,
    @JsonProperty("new_chat_members") val newChatMembers: List<User>? = null,
    @JsonProperty("left_chat_member") val leftChatMember: User? = null,
    @JsonProperty("new_chat_title") val newChatTitle: String? = null,
    @JsonProperty("new_chat_photo") val newChatPhoto: List<PhotoSize>? = null,
    @JsonProperty("delete_chat_photo") val deleteChatPhoto: Boolean? = null,
    @JsonProperty("group_chat_created") val groupChatCreated: Boolean? = null,
    @JsonProperty("supergroup_chat_created") val supergroupChatCreated: Boolean? = null,
    @JsonProperty("message_auto_delete_timer_changed") val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged? = null,
    @JsonProperty("migrate_to_chat_id") val migrateToChatId: Int? = null,
    @JsonProperty("migrate_from_chat_id") val migrateFromChatId: Int? = null,
    @JsonProperty("pinned_message") val pinnedMessage: Message? = null,
    @JsonProperty("invoice") val invoice: Invoice? = null,
    @JsonProperty("successful_payment") val successfulPayment: SuccessfulPayment? = null,
    @JsonProperty("connected_website") val connectedWebsite: String? = null,
    @JsonProperty("passport_data") val passportData: PassportData? = null,
    @JsonProperty("proximity_alert_triggered") val proximityAlertTriggered: ProximityAlertTriggered? = null,
    @JsonProperty("voice_chat_scheduled") val voiceChatScheduled: VoiceChatScheduled? = null,
    @JsonProperty("voice_chat_ended") val voiceChatEnded: VoiceChatEnded? = null,
    @JsonProperty("voice_chat_participants_invited") val voiceChatParticipantsInvited: VoiceChatParticipantsInvited? = null,
    @JsonProperty("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
) {
    init {
        if (text != null && text.isNotBlank() && entities != null && entities.isNotEmpty()) {
            entities.forEach { entity ->
                entity.text = text.substring(entity.offset, entity.offset + entity.length)
            }
        }
        if (caption != null && caption.isNotBlank() && captionEntities != null && captionEntities.isNotEmpty()) {
            captionEntities.forEach { entity ->
                entity.text = caption.substring(entity.offset, entity.offset + entity.length)
            }
        }
    }
}