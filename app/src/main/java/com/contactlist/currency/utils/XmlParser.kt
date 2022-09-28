package com.contactlist.currency.utils

import android.util.Xml
import com.contactlist.currency.data.local.entity.ContactEntity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

object XmlParser {
    private val ns: String? = null


    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<ContactEntity> {
        inputStream.use { input ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(input, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<ContactEntity> {
        val entries = mutableListOf<ContactEntity>()

        parser.require(XmlPullParser.START_TAG, ns, "AddressBook")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "Contact") {
                entries.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return entries
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): ContactEntity {
        parser.require(XmlPullParser.START_TAG, ns, "Contact")
        var customerId: String? = null
        var companyName: String? = null
        var contactName: String? = null
        var title: String? = null
        var address: String? = null
        var city: String? = null
        var email: String? = null
        var postalCode: String? = null
        var country: String? = null
        var phone: String? = null
        var fax: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "CustomerID" -> customerId = readTitle(parser, "CustomerID")
                "ContactName" -> contactName = readTitle(parser, "ContactName")
                "CompanyName" -> companyName = readTitle(parser, "CompanyName")
                "ContactTitle" -> title = readTitle(parser, "ContactTitle")
                "country" -> country = readTitle(parser, "Country")
                "Address" -> address = readTitle(parser, "Address")
                "City" -> city = readTitle(parser, "City")
                "Email" -> email = readTitle(parser, "Email")
                "PostalCode" -> postalCode = readTitle(parser, "PostalCode")
                "Phone" -> phone = readTitle(parser, "Phone")
                "Fax" -> fax = readTitle(parser, "Fax")
                else -> skip(parser)
            }
        }
        return ContactEntity(
            customerId = customerId ?: "",
            contactName = contactName ?: "",
            companyName = companyName ?: "",
            title = title ?: "",
            address = address ?: "",
            city = city ?: "",
            country = country ?: "",
            email = email ?: "",
            postalCode = postalCode ?: "",
            phone = phone ?: "",
            fax = fax ?: ""
        )
    }

    // Processes title tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTitle(parser: XmlPullParser, tagName: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tagName)
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tagName)
        return title
    }

    // For the tags title and summary, extracts their text values.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
