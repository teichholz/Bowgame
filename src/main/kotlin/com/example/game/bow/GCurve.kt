package com.example.game.bow

import com.example.game.Constants
import com.example.game.CosAndSin
import godot.Node2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.PoolVector2Array
import godot.core.Vector2

@RegisterClass
class GCurve : Node2D() {

	var points: PoolVector2Array = PoolVector2Array()

	@RegisterFunction
	override fun _ready() {
		visible = false
	}

	@RegisterFunction
	override fun _draw() {
		for (point in points) {
			drawCircle(point, 2.0, Color.white)
		}
	}

	fun draw(origin: Vector2, speed: Double /* px/s */, gravity: Double , cosAndSin: CosAndSin) {
		//clearPoints()
		visible = true
		this.position = origin
		val sx = cosAndSin.cos * speed
		val sy = cosAndSin.sin * speed
		val g = gravity // px/s^2
		fun rx(t: Double): Double { return position.x + sx * t }
		fun ry(t: Double): Double { return position.y + -sy * t + 0.5 * g * t * t  }
		val delta = 0.25
		var t: Double = delta
		val ps = PoolVector2Array()
		while (t < 100.0) {
			val p = Vector2(rx(t), ry(t))
			ps.append(toLocal(p))
			t += delta
		}
		this.points = ps
		update()
	}

	fun doNotDraw() {
		visible = false
	}

	override fun toString(): String {
		return "GCurve: ${this.points.toList()}"
	}
}
