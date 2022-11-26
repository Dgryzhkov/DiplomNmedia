package com.example.diplomnmedia.dto

import com.example.diplomnmedia.enumeration.AttachmentType

data class Attachment(
    val url: String,
    val type: AttachmentType
)