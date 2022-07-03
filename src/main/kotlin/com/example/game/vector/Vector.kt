package com.example.game.vector

import godot.Node2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.PoolVector2Array
import godot.core.Vector2

@RegisterClass
class Vector : Node2D() {

	var pos: Vector2 = Vector2(.0, .0)
		set(pos) {
			field = pos
			doDraw = true
			update()
		}

	var dir: Vector2 = Vector2(.0, .0)
	  set(dir) {
		  field = dir
		  doDraw = true
		  update()
	  }

	var color: Color = Color.red
	  set(color) {
		  field = color
		  doDraw = true
		  update()
	  }

	var doDraw: Boolean = true
	  set(draw) {
		  field = draw
		  if (!draw) this.visible = false
		  else this.visible = true
	  }

	@RegisterFunction
	override fun _draw() {
		var ps = PoolVector2Array()
		ps.append(pos)
		var pointsTo = pos + dir
		ps.append(pointsTo)
		val norm = dir.length()
		val hat = (dir / norm) * (norm * 0.1)

		val left = pointsTo + hat.rotated(Math.PI / 2)
		val middle = pointsTo + hat
		val right = pointsTo + hat.rotated(-Math.PI / 2)
		val closed = pointsTo
		ps.apply {
			append(left)
			append(middle)
			append(right)
			append(closed)
		}
		drawPolyline(ps, color, 2.0, true)
	}

	@RegisterFunction
	override fun _ready() {
		pos = Vector2(200.0, 200.0)
		dir = Vector2(50.0, 50.0)
	}
}
