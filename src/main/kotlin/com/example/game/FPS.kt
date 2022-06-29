package com.example.game

import godot.Engine
import godot.Label
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction

@RegisterClass
class FPS : Label() {

	@RegisterFunction
	override fun _process(delta: Double) {
		text = Engine.getFramesPerSecond().toString()
	}
}
