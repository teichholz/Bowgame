package com.example.game.bow
import com.example.game.*
import godot.*
import godot.annotation.*
import godot.core.Vector2
import godot.extensions.getNodeAs
import godot.extensions.loadAs
import godot.global.GD
import godot.signals.signal

@RegisterClass
class Bow : AnimatedSprite() {

	/**
	 * return cos and sin depending on rotation to x-axis
	 */
	fun angle(dir: Vector2): CosAndSin {
		val xAxis = Vector2.RIGHT
		val sin = dir.cross(xAxis)
		val cos = dir.dot(xAxis)
		return CosAndSin(cos, sin)
	}

}
