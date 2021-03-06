package de.forside.quicktranslate

import javafx.application.Platform
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import javafx.util.Duration
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import tornadofx.View
import tornadofx.runLater
import kotlin.collections.set

class TranslatorForm : View(), NativeKeyListener {

	/*
	 *******************************
	 * Form controls
	 *******************************
	 */
	override val root: AnchorPane by fxml()

	private val vboxMain: VBox by fxid()

	private val hboxForm: HBox by fxid()
	private val textWord: TextField by fxid()

	private val hboxWindowButtons: HBox by fxid()
	private val buttonClose: Button by fxid()

	private val labelResult: Label by fxid()

	//******************************


	private val controller = TranslatorController()

	private var lastTranslatedWord = ""
	private var transThread: Thread? = null

	private var dragOffsetX = 0.0
	private var dragOffsetY = 0.0

	private val keysPressed = mutableMapOf<Int, Boolean>()
	private val hotkeys = mutableMapOf<List<Int>, ()->Unit>()

	private val langs = arrayOf(
			Pair("deu", "German"),
			Pair("eng", "English")
	)
	private var langFrom = 0
	private var langDest = 0

	init {
		title = "QuickTranslate"
		currentStage?.initStyle(StageStyle.UNDECORATED)
//		currentStage?.isAlwaysOnTop = true
		TranslatorStyle.setDesign(root, TranslatorStyle.Designs.BLACK)


		AnchorPane.setTopAnchor(vboxMain, 0.0)
		AnchorPane.setLeftAnchor(vboxMain, 0.0)
		AnchorPane.setRightAnchor(vboxMain, 0.0)
//		AnchorPane.setBottomAnchor(vboxMain, 0.0)

		AnchorPane.setTopAnchor(hboxForm, 0.0)
		AnchorPane.setLeftAnchor(hboxForm, 0.0)

		AnchorPane.setTopAnchor(hboxWindowButtons, 0.0)
		AnchorPane.setRightAnchor(hboxWindowButtons, 0.0)

		AnchorPane.setLeftAnchor(labelResult, 0.0)
		AnchorPane.setRightAnchor(labelResult, 0.0)

		textWord.setOnKeyReleased {
			getTranslation(textWord.text, langs[langFrom].first, langs[langDest].first)
		}

		buttonClose.setOnAction {
			Platform.exit()
		}

		root.setOnMousePressed { e ->
			if (e.isPrimaryButtonDown) {
				dragOffsetX = e.screenX - primaryStage.x
				dragOffsetY = e.screenY - primaryStage.y
			}
		}

		root.setOnMouseDragged { e ->
			if (e.isPrimaryButtonDown) {
				primaryStage.x = e.screenX - dragOffsetX
				primaryStage.y = e.screenY - dragOffsetY
			}
		}

		root.setOnKeyPressed { e ->
			when (e.code) {
				KeyCode.ESCAPE -> resetTranslation()

				else -> {}
			}
		}

		if (GlobalScreen.isNativeHookRegistered()) {
			GlobalScreen.addNativeKeyListener(this)

			registerHotkey(NativeKeyEvent.VC_CONTROL, NativeKeyEvent.VC_ALT, NativeKeyEvent.VC_T) {
				currentStage?.toFront()
				currentStage?.requestFocus()
				resetTranslation()
			}
		}

		initMenu()

		vboxMain.heightProperty().addListener { _, _, _ ->
			root.autosize()
			currentStage?.sizeToScene()
		}
		vboxMain.widthProperty().addListener { _, _, newValue ->
			root.autosize()
			currentStage?.sizeToScene()
			labelResult.maxWidth = newValue.toDouble()
		}
	}

	private fun initMenu() {
		val menuFrom = ToggleGroup().let { group ->
			langs.map { lang ->
				RadioMenuItem(lang.second).apply {
					toggleGroup = group
					setOnAction {
						langFrom = parentMenu.items.indexOf(this)
					}
				}
			}.let { items ->
				group.selectToggle(items[0])
				langFrom = 0
				Menu("From", null, *items.toTypedArray())
			}
		}


		val menuDest = ToggleGroup().let { group ->
			langs.map { lang ->
				RadioMenuItem(lang.second).apply {
					toggleGroup = group
					setOnAction {
						langDest = parentMenu.items.indexOf(this)
					}
				}
			}.let { items ->
				group.selectToggle(items[1])
				langDest = 1
				Menu("Dest", null, *items.toTypedArray())
			}
		}


		val itemOnTop = RadioMenuItem("Always on top").apply {
			setOnAction {
				currentStage?.isAlwaysOnTop = isSelected
			}
		}


		val menuDesign = ToggleGroup().let { group ->
			TranslatorStyle.Designs.values().map { design ->
				RadioMenuItem(design.name.toLowerCase().capitalize()).apply {
					toggleGroup = group
					setOnAction {
						TranslatorStyle.setDesign(root, design)
					}
				}
			}.let { items ->
				group.selectToggle(items[1])
				Menu("Design", null, *items.toTypedArray())
			}
		}


		val menu = ContextMenu(menuFrom, menuDest, menuDesign, itemOnTop)
		menu.isHideOnEscape = true
		menu.isAutoHide = true

		root.setOnContextMenuRequested { e ->
			menu.show(currentWindow, e.screenX, e.screenY)
		}
	}

	private fun resetTranslation() {
		textWord.clear()
		labelResult.text = "Translate a word"
		textWord.requestFocus()
	}

	private fun getTranslation(word: String, from: String, dest: String) {
		if (word != lastTranslatedWord) {
			lastTranslatedWord = word

			when {
				langFrom == langDest -> labelResult.text = "'From' and 'Dest' are equal"

				word.isEmpty() -> resetTranslation()

				else -> {
					transThread?.interrupt()
					transThread = TransThread(word, from, dest)
					transThread?.start()
				}
			}
		}
	}

	private fun registerHotkey(vararg keys: Int, body: ()->Unit) {
		hotkeys[keys.toList()] = body
	}

	override fun nativeKeyTyped(e: NativeKeyEvent) {
	}

	override fun nativeKeyPressed(e: NativeKeyEvent) {
		keysPressed[e.keyCode] = true

		hotkeys.forEach { keys, body ->
			var keyCount = keys.size

			keys.forEach {
				if (keysPressed[it] == true)
					keyCount--
			}

			if (keyCount == 0) {
				runLater(Duration.millis(100.0)) {
					body()
				}
			}
		}
	}

	override fun nativeKeyReleased(e: NativeKeyEvent) {
		keysPressed[e.keyCode] = false
	}

	private inner class TransThread(private val word: String, private val from: String, private var dest: String) : Thread() {
		override fun run() {
			try {
				sleep(500)
				if (isInterrupted) {
					return
				}

				runAsync {
					controller.getTranslation(word, from, dest)
				} ui { result ->
					when (result.tuc.size) {
						0 -> {
							labelResult.text = "No translation found"
						}

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
			} catch (e: InterruptedException) {}
		}
	}

}
