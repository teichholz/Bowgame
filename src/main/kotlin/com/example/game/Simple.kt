package com.example.game

import godot.*
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.Vector2
import godot.extensions.getNodeAs
import godot.global.GD

// Extensions
fun AnimatedSprite.center(): Vector2 {
	return this.position
}

@RegisterClass
class Simple: Node2D() {

	lateinit var bow: Bow
	lateinit var gCurve: GCurve
	var mousePos: Vector2 = getGlobalMousePosition()
	var time: Double = 0.0

	@RegisterFunction
	override fun _ready() {
		GD.print("Hello world!")
		bow = getNodeAs<Bow>("Bow")!!
		gCurve = getNodeAs<GCurve>("GCurve")!!
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		time += delta

		bow.lookAt(mousePos)

		gCurve.draw(bow.center(), 25.0, bow.angle(mousePos))
		if(time > 0.1) {
			time = 0.0
		}
	}

	@RegisterFunction
	override fun _draw() {
		bow.let {
			drawCircle(it.center(), 2.0, Color.aqua)
		}

	}


	@RegisterFunction
	override fun _input(event: InputEvent) {
		if (event is InputEventMouseMotion) {
			// get direction the cursor points to from bow
			mousePos = event.position
		}
	}

}
