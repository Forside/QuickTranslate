package de.forside.quicktranslate

import tornadofx.launch

fun main(args: Array<String>) {
	/*//val frame = JFrame(MainForm::class.java.canonicalName)
	val frame = MainForm("QuickTranslate")
	//frame.contentPane = MainForm().contentPane
//	frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//	frame.pack()
	frame.isVisible = true*/


	launch<TranslatorApp>(args)
}