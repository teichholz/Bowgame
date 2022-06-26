package com.example.game

import godot.Line2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.PoolVector2Array
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class GCurve : Line2D() {

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
		this.jointMode = 2
		//addPoint(Vector2(50, 50))
		//addPoint(Vector2(100, 100))
		//addPoint(Vector2(200, 200))
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		
	}

	fun draw(origin: Vector2, speed: Double /* px/s */ , cosAndSin: CosAndSin) {
		clearPoints()
		position = origin
		val sx = cosAndSin.cos * speed
		val sy = cosAndSin.sin * speed
		val g = 0.5 // -1px/t^2
		fun rx(t: Double): Double { return position.x + sx * t }
		fun ry(t: Double): Double { return position.y + -sy * t + 0.5 * g * t * t  }
		val delta = 0.5
		var t: Double = 0.0
		val points = PoolVector2Array()
		while (t < 100.0) {
			val p = Vector2(rx(t), ry(t))
			points.append(toLocal(p))
			t += delta
		}
		this.points = points
	}

	override fun toString(): String {
		return "GCurve: ${this.points.toList()}"
	}
}
