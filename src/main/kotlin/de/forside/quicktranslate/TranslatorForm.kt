package de.forside.quicktranslate

import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.View

class TranslatorForm : View() {

	/*
	 * Form controls
	 */
	override val root: AnchorPane by fxml()
//	private val toolbarMain: ToolBar by fxid()

	private val vboxMain: VBox by fxid()

	private val hboxForm: HBox by fxid()
	private val textWord: TextField by fxid()
//	private val buttonTranslate: Button by fxid()

	private val hboxWindowButtons: HBox by fxid()
	private val buttonClose: Button by fxid()

	private val labelResult: Label by fxid()


	private val controller = TranslatorController()

	private var lastTranslatedWord = ""
	private var transThread: Thread? = null

	private var dragOffsetX = 0.0
	private var dragOffsetY = 0.0

	init {
		this.title = "QuickTranslate"

		/*AnchorPane.setTopAnchor(toolbarMain, 0.0)
		AnchorPane.setLeftAnchor(toolbarMain, 0.0)
		AnchorPane.setRightAnchor(toolbarMain, 0.0)*/

		AnchorPane.setTopAnchor(vboxMain, 5.0)
		AnchorPane.setLeftAnchor(vboxMain, 5.0)
		AnchorPane.setRightAnchor(vboxMain, 5.0)
		AnchorPane.setBottomAnchor(vboxMain, 5.0)

		AnchorPane.setTopAnchor(hboxForm, 0.0)
		AnchorPane.setLeftAnchor(hboxForm, 0.0)

		AnchorPane.setTopAnchor(hboxWindowButtons, 0.0)
		AnchorPane.setRightAnchor(hboxWindowButtons, 0.0)

		AnchorPane.setLeftAnchor(labelResult, 0.0)
		AnchorPane.setRightAnchor(labelResult, 0.0)

		/*buttonTranslate.setOnAction {
			getTranslation(textWord.text)
		}*/

		textWord.setOnKeyReleased {
			getTranslation(textWord.text)
		}

		buttonClose.setOnAction {
			Platform.exit()
		}

		root.setOnMousePressed { event ->
			dragOffsetX = event.screenX - primaryStage.x
			dragOffsetY = event.screenY - primaryStage.y
		}

		root.setOnMouseDragged { event ->
			primaryStage.x = event.screenX - dragOffsetX
			primaryStage.y = event.screenY - dragOffsetY
		}
	}

	private fun getTranslation(word: String) {
		if (word != lastTranslatedWord) {
			lastTranslatedWord = word

			transThread?.interrupt()
			transThread = TransThread(word)
			transThread?.start()
		}
	}

	private inner class TransThread(private val word: String) : Thread() {
		override fun run() {
			try {
//				println("$id Translating")
				sleep(500)
				if (isInterrupted) {
//					println("$id interrupted")
					return
				}

				runAsync {
					controller.getTranslation(word)
				} ui { result ->
					when (result.tuc.size) {
						0 -> {
							labelResult.text = "No translation found"
//							println("$id no translation found")
						}

						else -> {
							val resultString = StringBuilder()
							result.tuc.forEachIndexed { index, glosbeResultTuc ->
								resultString.append(glosbeResultTuc.phrase?.text)
								if (index < result.tuc.size - 1)
									resultString.append("; ")
							}
							labelResult.text = resultString.toString()
//							println("$id finished: $resultString")
						}
					}
				}
			} catch (e: InterruptedException) {
//				println("$id interrupted ex")
			}
		}
	}

}
