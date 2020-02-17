package com.tikal.cmdls.model

data class Keyword (val id: Long?, val label: String)

data class Recipes(val id: Long?, val description: String, val commandId: Long, val input: String, val keywords: List<String>?)

data class Commands(val id: Long, val inputs: String, val bin: String, val npm: String?, val github: String?)






