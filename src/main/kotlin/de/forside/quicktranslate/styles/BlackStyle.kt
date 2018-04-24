package de.forside.quicktranslate.styles

import javafx.scene.paint.Color
import tornadofx.Stylesheet.Companion.button
import tornadofx.Stylesheet.Companion.contextMenu
import tornadofx.Stylesheet.Companion.label
import tornadofx.Stylesheet.Companion.textField
import tornadofx.box
import tornadofx.mixin
import tornadofx.px

val BlackStyle = mixin {
	backgroundColor += Color.BLACK
	borderColor += box(Color.GRAY)
	padding = box(5.px)

	button {
		backgroundColor += Color.BLACK
		borderColor += box(Color.WHITE)
		textFill = Color.WHITE
	}

	textField {
		backgroundColor += Color.BLACK
		borderColor += box(Color.WHITE)
		textFill = Color.WHITE
		promptTextFill = Color.GRAY
	}

	label {
		textFill = Color.WHITE
	}

	contextMenu {
		backgroundColor += Color.BLACK
	}
}