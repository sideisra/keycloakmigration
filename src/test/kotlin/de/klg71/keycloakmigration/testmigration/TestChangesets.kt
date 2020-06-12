package de.klg71.keycloakmigration.testmigration

import com.fathzer.soft.javaluator.AbstractEvaluator
import com.fathzer.soft.javaluator.Operator
import com.fathzer.soft.javaluator.Parameters
import com.xenomachina.argparser.mainBody

/**
 * executes the changesets from test/resources
 *
 * INFO: This file must has its run configuration working dir set to src/test/resources
 */
fun main(args: Array<String>) = mainBody {
    val evaluator = createSimpleBooleanEvaluator()
    var expression = "true && false"
    println(expression + " = " + evaluator.evaluate(expression))
    expression = "true || false"
    println(expression + " = " + evaluator.evaluate(expression))
    expression = "!true"
    println(expression + " = " + evaluator.evaluate(expression))
    expression = "bla == blubb"
    println(expression + " = " + evaluator.evaluate(expression))
    expression = "bla == bla"
    println(expression + " = " + evaluator.evaluate(expression))
    expression = "\$ENV == Test"
    println(expression + " = " + evaluator.evaluate(expression))
    // migrate(ArgParser(args).parseInto(::CommandLineMigrationArgs))
}

fun createSimpleBooleanEvaluator(): SimpleBooleanEvaluator {
    val params = Parameters()
    params.add(AND)
    params.add(OR)
    params.add(NEGATE)
    params.add(EQUALS)

    return SimpleBooleanEvaluator(params)
}

val NEGATE =
    Operator("!", 1, Operator.Associativity.RIGHT, 4)
val EQUALS =
    Operator("==", 2, Operator.Associativity.LEFT, 3)
val AND =
    Operator("&&", 2, Operator.Associativity.LEFT, 2)
val OR =
    Operator("||", 2, Operator.Associativity.LEFT, 1)

class SimpleBooleanEvaluator(parameters: Parameters?) : AbstractEvaluator<String>(parameters) {

    override fun toValue(literal: String?, evaluationContext: Any?): String? {
        println("literal: $literal")
        if (literal != null && literal.startsWith("$")) {
            val envVar = literal.substring(1)
            println("envVar: $envVar")
            return System.getenv(envVar)
        } else {
            return literal
        }
    }

    override fun evaluate(
        operator: Operator,
        operands: Iterator<String>,
        evaluationContext: Any?
    ): String? {
        return when (operator) {
            NEGATE -> {
                (!operands.next().toBoolean()).toString()
            }
            OR -> {
                val o1 = operands.next().toBoolean()
                val o2 = operands.next().toBoolean()
                (o1 || o2).toString()
            }
            AND -> {
                val o1 = operands.next().toBoolean()
                val o2 = operands.next().toBoolean()
                (o1 && o2).toString()
            }
            EQUALS -> {
                val o1 = operands.next()
                val o2 = operands.next()
                println("EQUALS: $o1 == $o2")
                (o1 == o2).toString()
            }
            else -> {
                super.evaluate(operator, operands, evaluationContext)
            }
        }
    }
}
