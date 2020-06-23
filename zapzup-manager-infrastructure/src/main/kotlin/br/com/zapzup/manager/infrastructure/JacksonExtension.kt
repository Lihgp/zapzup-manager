package br.com.zapzup.manager.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream

object JacksonExtension {

    val jacksonObjectMapper: ObjectMapper by lazy {
        ObjectMapper().registerModule(KotlinModule())
            .also { it.registerModule(SimpleModule()) }
            .also { it.registerModule(JavaTimeModule()) }
            .also { it.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) }
            .also { it.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true) }
            .also { it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }
            .also { it.setSerializationInclusion(JsonInclude.Include.NON_NULL) }
            .also { it.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE) }
    }

}

fun <T> JsonNode.toObject(t: Class<T>): T? =
    JacksonExtension.jacksonObjectMapper.convertValue(this, t)

fun <T> String.jsonToObject(t: Class<T>): T? =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

fun <T> ByteArray.jsonToObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

fun <T> String.jsonToObject(typeReference: TypeReference<T>): T? =
    JacksonExtension.jacksonObjectMapper.readValue(this, typeReference)

fun <T> T.objectToJson(): String =
    JacksonExtension.jacksonObjectMapper.writeValueAsString(this)

fun <T> T.objectToJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.valueToTree<JsonNode>(this)

fun <T> List<T>.convertValue(): JsonNode =
    JacksonExtension.jacksonObjectMapper.convertValue(this, JsonNode::class.java)

fun String.stringToJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.readTree(this)

fun Map<String, Any>.toJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.valueToTree<JsonNode>(this)

fun <T : JsonNode> Map<String, Any>.valueToTree(): T =
    JacksonExtension.jacksonObjectMapper.valueToTree(this)

fun <T : JsonNode> List<Any>.valueToTree(): T =
    JacksonExtension.jacksonObjectMapper.valueToTree(this)

fun <T> InputStream.jsonToObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

fun InputStream.jsonToString(): String =
    JacksonExtension.jacksonObjectMapper.readTree(this).toString()

fun <T> InputStream.jsonToObject(typeReference: TypeReference<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, typeReference)

object JsonResourceUtils {

    fun <T> getPayload(resource: String, clazz: Class<T>): T {
        val resourceAsStream: InputStream = javaClass.classLoader.getResourceAsStream("payload/$resource")
        return resourceAsStream.jsonToObject(clazz)
    }

    fun getPayload(resource: String): String {
        val resourceAsStream: InputStream = javaClass.classLoader.getResourceAsStream("payload/$resource")
        return resourceAsStream.jsonToString()
    }

    fun <T> getPayload(resource: String, typeReference: TypeReference<T>): T {
        val resourceAsStream: InputStream = javaClass.classLoader.getResourceAsStream("payload/$resource")
        return resourceAsStream.jsonToObject(typeReference)
    }
}