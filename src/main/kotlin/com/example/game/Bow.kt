package com.example.game
import godot.AnimatedSprite
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class Bow : AnimatedSprite() {

	@RegisterFunction
	override fun _draw() {
		// drawCircle(this.position, 2.0, Color.aqua)
	}

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
		
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		
	}

	/**
	 * return cos and sin depending on rotation to x-axis
	 */
	fun angle(mousePos: Vector2): CosAndSin {
		val bowDir = (mousePos - center()).normalized()
		val xAxis = Vector2.RIGHT
		val sin = bowDir.cross(xAxis)
		val cos = bowDir.dot(xAxis)
		return CosAndSin(cos, sin)
	}
}
