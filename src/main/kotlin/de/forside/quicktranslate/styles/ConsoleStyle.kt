package de.forside.quicktranslate.styles

import de.forside.quicktranslate.TranslatorStyle
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet.Companion.button
import tornadofx.Stylesheet.Companion.contextMenu
import tornadofx.Stylesheet.Companion.label
import tornadofx.Stylesheet.Companion.textField
import tornadofx.box
import tornadofx.mixin
import tornadofx.px

val ConsoleStyle = mixin {
	backgroundColor += Color.BLACK
	borderStyle += BorderStrokeStyle.NONE

	button {
		backgroundColor += Color.BLACK
		borderStyle += BorderStrokeStyle.NONE
		textFill = Color.LIMEGREEN
		fontFamily = "Lucida Console"
	}

	textField {
		backgroundColor += Color.BLACK
		borderStyle += BorderStrokeStyle.NONE
		textFill = Color.LIMEGREEN
		promptTextFill = Color.LIMEGREEN
		padding = box(2.px, 0.px, 0.px, 2.px)
		fontSize = 18.px
		fontFamily = "Lucida Console"
		fontWeight = FontWeight.BOLD
	}

	label {
		textFill = Color.LIMEGREEN
		fontFamily = "Lucida Console"
	}

	TranslatorStyle.labelResult {
		labelPadding = box(0.px, 0.px, 2.px, 2.px)
		fontSize = 18.px
		fontWeight = FontWeight.BOLD
	}

	contextMenu {
		backgroundColor += Color.BLACK
	}
}