package com.mr.mr_test.model.net

import android.net.Uri

class PageLinks(linkHeader: String) {
    var first: Int? = null
    var last: Int? = null
    var next: Int? = null
    var prev: Int? = null

    companion object {
        private const val DELIM_LINKS = ","
        private const val DELIM_LINK_PARAM = ";"

        private const val META_SINCE = "since"
        private const val META_FIRST = "first"
        private const val META_LAST = "last"
        private const val META_NEXT = "next"
        private const val META_PREV = "prev"
        private const val META_REL = "rel"
    }

    init {
        val links = linkHeader.split(DELIM_LINKS).toTypedArray()
        for (link in links) {
            val segments = link.split(DELIM_LINK_PARAM).toTypedArray()

            if (segments.size < 2) continue

            var linkPart = segments[0].trim { it <= ' ' }

            if (!linkPart.startsWith("<") || !linkPart.endsWith(">"))
                continue

            linkPart = linkPart
                .substring(1, linkPart.length - 1)

            val uri = Uri.parse(linkPart)
            val since: Int? = uri.getQueryParameter(META_SINCE)?.toInt()

            for (i in 1 until segments.size) {
                val rel = segments[i].trim { it <= ' ' }.split("=".toRegex()).toTypedArray()

                if (rel.size < 2 || META_REL != rel[0]) continue

                var relValue = rel[1]
                if (relValue.startsWith("\"") && relValue.endsWith("\""))
                    relValue = relValue.substring(1, relValue.length - 1)

                when (relValue) {
                    META_FIRST -> {
                        first = since
                    }
                    META_LAST -> {
                        last = since
                    }
                    META_NEXT -> {
                        next = since
                    }
                    META_PREV -> {
                        prev = since
                    }
                }
            }
        }
    }
}