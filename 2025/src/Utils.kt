import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/** Reads lines from the given input txt file. */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/** Converts string to md5 hash. */
fun String.md5(): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Reads a list of integers from a string.
 */
fun readIntsFromString(input: String): List<Int> {
    return input.trim().split("\\s+".toRegex()).mapNotNull {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            null // Skip invalid numbers
        }
    }
}