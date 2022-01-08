package moe.kurenai.tdlight.model.login

data class AuthorizationState(
    val authorizationState: AuthorizationStateType,
    val token: String? = null,
    val timeout: Int? = null,
    val passwordHint: String? = null,
    val hasRecoveryEmailAddress: Boolean? = null,
)

enum class AuthorizationStateType {
    WAIT_CODE,WAIT_PASSWORD,WAIT_REGISTRATION,READY,UNKNOWN
}