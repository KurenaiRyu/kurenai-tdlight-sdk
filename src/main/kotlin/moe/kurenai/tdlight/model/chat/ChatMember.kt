package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import moe.kurenai.tdlight.model.media.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "status"
)
@JsonSubTypes(
    value = [
        JsonSubTypes.Type(value = ChatMemberOwner::class, name = "creator"),
        JsonSubTypes.Type(value = ChatMemberAdministrator::class, name = "administrator"),
        JsonSubTypes.Type(value = ChatMemberMember::class, name = "member"),
        JsonSubTypes.Type(value = ChatMemberRestricted::class, name = "restricted"),
        JsonSubTypes.Type(value = ChatMemberLeft::class, name = "left"),
        JsonSubTypes.Type(value = ChatMemberBanned::class, name = "kicked")
    ]
)
interface ChatMember 