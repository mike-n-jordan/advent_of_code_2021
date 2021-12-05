package com.mike.advent

data class SubmarineStateV2(
    val horizontalPosition: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0,
)

fun SubmarineStateV2.perform(action: SubmarineAction): SubmarineStateV2 =
    when (action) {
        is SubmarineAction.Down -> copy(aim = aim + action.amount)
        is SubmarineAction.Up -> copy(aim = aim - action.amount)
        is SubmarineAction.Forward -> copy(
            horizontalPosition = horizontalPosition + action.amount,
            depth = depth + aim * action.amount
        )
        else -> throw UnsupportedOperationException()
    }
