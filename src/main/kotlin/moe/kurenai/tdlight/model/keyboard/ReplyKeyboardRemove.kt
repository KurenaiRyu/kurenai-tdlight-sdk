package moe.kurenai.tdlight.model.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import moe.kurenai.tdlight.model.message.ReplyMarkup;

import java.util.Optional;

public record ReplyKeyboardRemove(
        @JsonProperty("remove_keyboard") Boolean removeKeyboard,
        @JsonProperty("selective") Optional<Boolean> selective
) implements ReplyMarkup {
}
