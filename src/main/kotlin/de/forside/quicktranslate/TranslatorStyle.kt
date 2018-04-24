package de.forside.quicktranslate

import de.forside.quicktranslate.styles.BlackStyle
import de.forside.quicktranslate.styles.ConsoleStyle
import de.forside.quicktranslate.styles.DefaultStyle
import javafx.scene.Node
import tornadofx.*

class TranslatorStyle : Stylesheet() {

	companion object {
		private val styleDefault by cssclass()
		private val styleBlack by cssclass()
		private val styleConsole by cssclass()
		val labelResult by cssid()

		fun setDesign(node: Node, design: Designs) {
			node.styleClass.filter {
				Designs.values().map {
					it.rule.name
				}.contains(it)
			}.forEach {
				node.removeClass(it)
			}
			node.addClass(design.rule)
		}
	}

	enum class Designs(val rule: CssRule) {
		DEFAULT(styleDefault), BLACK(styleBlack), CONSOLE(styleConsole)
	}

	init {
		styleDefault {
			+DefaultStyle
		}

		styleBlack {
			+BlackStyle
		}

		styleConsole {
			+ConsoleStyle
		}
	}

}