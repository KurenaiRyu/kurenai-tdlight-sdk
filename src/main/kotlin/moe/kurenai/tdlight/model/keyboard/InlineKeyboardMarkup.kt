package moe.kurenai.tdlight.model.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import moe.kurenai.tdlight.model.message.ReplyMarkup;

import java.util.List;

public record InlineKeyboardMarkup(
        @JsonProperty("inline_keyboard") List<List<InlineKeyboardButton>> inlineKeyboard
) implements ReplyMarkup {
}
