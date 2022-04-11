package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.media.Location
import moe.kurenai.tdlight.model.message.User

@NoArg
class ChosenInlineResult(
    var resultId: String,
    var from: User,
    var query: String,
    var location: Location?,
    var inlineMessageId: String?
)