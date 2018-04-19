package de.forside.quicktranslate

import javafx.scene.paint.Color
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass

class TranslatorStyle : Stylesheet() {
	companion object {
		val body by cssclass()
	}

	init {
		body {
			backgroundColor += Color.BLACK
			borderColor += box(Color.GRAY)
		}

		button {
			backgroundColor += Color.BLACK
			borderColor += box(Color.WHITE)
			textFill = Color.WHITE
		}

		textField {
			backgroundColor += Color.BLACK
			borderColor += box(Color.WHITE)
			textFill = Color.WHITE
		}

		label {
			textFill = Color.WHITE
		}

		contextMenu {
			backgroundColor += Color.BLACK
		}
	}
}