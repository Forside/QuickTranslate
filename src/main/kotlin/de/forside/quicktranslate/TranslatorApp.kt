package de.forside.quicktranslate

import javafx.stage.Stage
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import tornadofx.App
import tornadofx.launch
import java.util.logging.Level
import java.util.logging.Logger

class TranslatorApp : App(TranslatorForm::class, TranslatorStyle::class) {

	init {
		setUserAgentStylesheet(STYLESHEET_MODENA)
	}

	override fun start(stage: Stage) {
//		stage.isAlwaysOnTop = true
//		stage.initStyle(StageStyle.UNDECORATED)

		try {
			val nativeHookLogger = Logger.getLogger(GlobalScreen::class.java.`package`.name)
			nativeHookLogger.level = Level.OFF
			nativeHookLogger.useParentHandlers = false
			GlobalScreen.registerNativeHook()
		} catch (e: NativeHookException) {
			println("There was a problem registering the native key hook.${System.lineSeparator()}${e.message}")
		}

		super.start(stage)
	}

	override fun stop() {
		try {
			GlobalScreen.unregisterNativeHook()
		} catch (e: NativeHookException) {
			e.printStackTrace()
		}
		super.stop()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch<TranslatorApp>(args)
		}
	}

}
