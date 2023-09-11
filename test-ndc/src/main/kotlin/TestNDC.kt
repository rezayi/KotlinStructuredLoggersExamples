import org.apache.log4j.Logger
import org.apache.log4j.NDC

fun main() {
    TestMDC().fun1()
}

class TestMDC {
    private val logger: Logger = Logger.getLogger(TestMDC::class.java)

    fun fun1() {
        NDC.push("function:fun1")
        NDC.push("place:before-fun2")
        logger.info("fun1 called before fun2")
        NDC.pop()
        NDC.pop()
        fun2()
        NDC.push("function:fun1")
        NDC.push("place/after-fun2")
        logger.info("fun1 called after fun2")
        NDC.remove()
        logger.info("fun1 called final")
    }

    private fun fun2() {
        NDC.push("function/fun2")
        NDC.push("place/inside-fun2")
        logger.info("fun2 called")
        NDC.pop()
        NDC.pop()
    }
}