package serilog

import UserDestructor
import serilogj.Log
import serilogj.LoggerConfiguration
import serilogj.context.LogContext
import serilogj.core.enrichers.LogContextEnricher
import serilogj.debugging.SelfLog
import serilogj.events.LogEventLevel
import serilogj.formatting.json.JsonFormatter
import serilogj.sinks.coloredconsole.ColoredConsoleSinkConfigurator.coloredConsole
import serilogj.sinks.rollingfile.RollingFileSinkConfigurator.rollingFile
import java.util.*

fun main() {
    TestSerilog().fun1()
}

class TestSerilog {
    fun fun1() {
        SelfLog.setOut(System.out)

        Log.setLogger(
            LoggerConfiguration()
                .setMinimumLevel(LogEventLevel.Debug)
                .with(LogContextEnricher())
                .with(UserDestructor())
                .writeTo(coloredConsole("[{Timestamp} {Level}] {Message} ({Properties}){NewLine}{Exception}"))
                .writeTo(
                    rollingFile(
                        "test-{Date}.log",
                        1024_000L,
                        10,
                        true,
                        JsonFormatter(false, "\n", true, Locale.ENGLISH)
                    )
                )
//                .writeTo(seq("http://localhost:5341/"))
                .createLogger()
        )

        test()
        Log.information("finally")
        Log.closeAndFlush()
    }

    private fun test(){
        try {
            val prop=LogContext
                .pushProperty("Operation1", "Hi")
            LogContext
                .pushProperty("Operation2", "HAY")
//                .use {
            fun2(1)
            LogContext.pushProperty("Operation3", "Bye")
                .use {
                    fun2(2)
                }
//                }
            prop.close()
            Log.information("In inner operation")
        } catch (ex: Exception) {
            Log.error(ex, "An error occurred")
        }
    }

    private fun fun2(no: Int) {
        Log.information("In outer operation$no")
    }
}