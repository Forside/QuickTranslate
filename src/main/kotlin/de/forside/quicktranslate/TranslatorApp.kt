package de.forside.quicktranslate

import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.App
import tornadofx.launch

class TranslatorApp : App(TranslatorForm::class, TranslatorStyle::class) {

	init {
		setUserAgentStylesheet(STYLESHEET_MODENA)
	}

	override fun start(stage: Stage) {
		stage.isAlwaysOnTop = true
		stage.initStyle(StageStyle.UNDECORATED)
		super.start(stage)
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch<TranslatorApp>(args)
		}
	}

}
