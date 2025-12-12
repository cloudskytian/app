package moe.crx.overport.utils

import kotlinx.coroutines.*
import kotlin.math.max

object PrintUtil {
    const val PROGRESS_LENGTH = 20L
    var progressTick = 0
    val scope = CoroutineScope(Dispatchers.IO)

    fun updateLine(block: () -> Unit): Job {
        return scope.launch {
            while (isActive) {
                drawLine(block)
                delay(300)
            }
        }
    }

    fun drawLine(block: () -> Unit) {
        block()
        progressTick++
        print("\r")
    }

    fun drawSpinner() {
        val progressSpinner = when (progressTick % 4) {
            0 -> '|'
            1 -> '/'
            2 -> '-'
            3 -> '\\'
            else -> '*'
        }

        print(progressSpinner)
        print(" ")
    }

    fun drawProgressBar(done: Long, total: Long?) {
        print("[")

        val doneChars = if (total != null) done * PROGRESS_LENGTH / total else 0
        (1..doneChars).forEach { _ ->
            print('#')
        }
        if (doneChars != PROGRESS_LENGTH) {
            val plusPosition = progressTick % (PROGRESS_LENGTH - doneChars)
            (1..plusPosition).forEach { _ ->
                print(' ')
            }
            print('+')
            (1..PROGRESS_LENGTH - 1 - plusPosition - doneChars).forEach { _ ->
                print(' ')
            }
        }

        print("] ")
    }

    fun fspace(): String {
        return "    "
    }

    fun uspace(key: Any, list: Collection<Any>): String {
        val max = list.map { it.toString().length }.maxOfOrNull { it } ?: 0
        return " ".repeat(max(max - key.toString().length, 0) + 4)
    }
}