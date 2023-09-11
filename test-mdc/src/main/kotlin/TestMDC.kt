import org.apache.log4j.Logger
import org.apache.log4j.MDC

fun main() {
    TestMDC().fun1()
}

class TestMDC {
    private val logger: Logger = Logger.getLogger(TestMDC::class.java)

    fun fun1() {
        MDC.put("function", "fun1")
        MDC.put("place", "before fun2")
        logger.info("fun1 called before fun2")
        fun2()
        MDC.put("place", "after fun2")
        logger.info("fun1 called after fun2")
        MDC.remove("place")
        logger.info("fun1 called final")
    }

    private fun fun2() {
        MDC.put("function", "fun2")
        MDC.put("place", "inside/fun2")
        logger.info("fun2 called")
    }
}