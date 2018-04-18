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

	override val root: AnchorPane by fxml()
//	private val toolbarMain: ToolBar by fxid()

	private val vboxMain: VBox by fxid()

	private val hboxForm: HBox by fxid()
	private val textInput: TextField by fxid()
	private val buttonTranslate: Button by fxid()

	private val hboxWindowButtons: HBox by fxid()
	private val buttonClose: Button by fxid()

	private val labelResult: Label by fxid()

	private val controller = TranslatorController()

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

		buttonTranslate.setOnAction {
			println("Translating!")

			runAsync {
				controller.getTranslation(textInput.text)
			} ui { result ->
				when (result.tuc.size) {
					0 -> labelResult.text = "No translation found"

					else -> {
						val resultString = StringBuilder()
						result.tuc.forEachIndexed { index, glosbeResultTuc ->
							resultString.append(glosbeResultTuc.phrase?.text)
							if (index < result.tuc.size - 1)
								resultString.append("; ")
						}
						labelResult.text = resultString.toString()
					}
				}
			}
		}

		buttonClose.setOnAction {
			Platform.exit()
		}
	}

}
