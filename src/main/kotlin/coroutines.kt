////Coroutine dispatcher
import arrow.core.Either
import arrow.effects.IO
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.javafx.JavaFx as UI

suspend fun <T : Any> IO<T>.await(): Either<Throwable, T> =
    suspendCoroutine { continuation ->
        unsafeRunAsync(continuation::resume)
    }

internal suspend fun main() =
    IO { "Hello Coroutine" }
        .await()
        // Wait for result on Coroutine dispatcher
        .fold(
            { println("Error $it") },
            { println("Success $it") }
        )
