package de.forside.quicktranslate.styles

import javafx.scene.paint.Color
import tornadofx.Stylesheet.Companion.textField
import tornadofx.box
import tornadofx.mixin
import tornadofx.px

val DefaultStyle = mixin {
	padding = box(5.px)

	textField {
		promptTextFill = Color.GRAY
	}
}