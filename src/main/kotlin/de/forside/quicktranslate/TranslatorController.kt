package de.forside.quicktranslate

import tornadofx.*
import javax.json.JsonObject

class TranslatorController : Controller() {

	private val api: Rest by inject()

	init {
		api.baseURI = "https://glosbe.com/gapi/"
	}

	fun getTranslation(word: String, from: String, dest: String): GlosbeResult
		= api.get("translate?from=${from.urlEncoded}&dest=${dest.urlEncoded}&format=json&phrase=${word.urlEncoded}").one().toModel()

}

class GlosbeResult : JsonModel {
//	private val resultProperty = SimpleStringProperty()
//	var result by resultProperty

	//private val tuc = arrayOf<SimpleObjectProperty>()

	var result: String? = null
	var tuc = mutableListOf<GlosbeResultTuc>()

	override fun updateModel(json: JsonObject) {
		with (json) {
			result = string("result")
			jsonArray("tuc")?.forEach {
				tuc.add(GlosbeResultTuc(it.asJsonObject()))
			}
		}
	}
}

class GlosbeResultTuc() : JsonModel {
	var phrase: GlosbeResultPhrase? = null

	constructor(json: JsonObject) : this() {
		updateModel(json)
	}

	override fun updateModel(json: JsonObject) {
		with (json) {
			jsonObject("phrase")?.let {
				phrase = GlosbeResultPhrase(it)
			}
		}
	}
}

class GlosbeResultPhrase() : JsonModel {
	var text: String? = null
	var language: String? = null

	constructor(json: JsonObject) : this() {
		updateModel(json)
	}

	override fun updateModel(json: JsonObject) {
		with (json) {
			text = string("text")
			language = string("language")
		}
	}
}