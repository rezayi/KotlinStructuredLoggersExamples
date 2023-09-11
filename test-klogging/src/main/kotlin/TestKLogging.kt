import io.klogging.Klogger
import io.klogging.Level
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.loggingConfiguration
import io.klogging.context.addToContext
import io.klogging.context.logContext
import io.klogging.context.withLogContext
import io.klogging.logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

suspend fun main() {
    TestKLogging().fun1()
}

class TestKLogging {
    private var logger: Klogger

    init {
        loggingConfiguration {
            ANSI_CONSOLE()
            minDirectLogLevel(Level.INFO)
        }
        logger = logger("main")
    }

    suspend fun fun1() = coroutineScope {
        withLogContext {
            addToContext("fun#" to 1, "fun1" to "hi")
            logger.info("Hello, fun1")
            fun2()
            logger.info("Bye, fun1")
        }
    }

    private suspend fun fun2() {
        withContext(currentCoroutineContext().plus(logContext())) {
            addToContext("fun2" to "Hi")
            logger.info { "Hello, fun2" }
        }
    }
}