package com.mike.advent

data class SubmarineStateV1(
    val horizontalPosition: Int = 0,
    val depth: Int = 0,
)

fun SubmarineStateV1.perform(action: SubmarineAction): SubmarineStateV1 =
    when (action) {
        is SubmarineAction.Down -> copy(depth = depth + action.amount)
        is SubmarineAction.Forward -> copy(horizontalPosition = horizontalPosition + action.amount)
        is SubmarineAction.Up -> copy(depth = depth - action.amount)
        else -> throw UnsupportedOperationException()
    }

sealed interface SubmarineAction {

    data class Forward(val amount: Int) : SubmarineAction

    data class Down(val amount: Int) : SubmarineAction

    data class Up(val amount: Int) : SubmarineAction
}

private val keywordUp = "up"
private val keyworkDown = "down"
private val keyworkForward = "forward"

fun String.toSubmarineAction(): SubmarineAction =
    when {
        contains(keywordUp) -> SubmarineAction.Up(substring(keywordUp.length + 1).toInt())
        contains(keyworkDown) -> SubmarineAction.Down(substring(keyworkDown.length + 1).toInt())
        contains(keyworkForward) -> SubmarineAction.Forward(substring(keyworkForward.length + 1).toInt())
        else -> throw UnsupportedOperationException()
    }
