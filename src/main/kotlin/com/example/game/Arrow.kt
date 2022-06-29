package com.example.game

import godot.AnimatedSprite
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2
import kotlin.math.atan2

@RegisterClass
class Arrow : AnimatedSprite() {

	var isFlying: Boolean = false
	var velocity: Vector2 = Vector2.ZERO

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
		visible = false
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		if (isFlying) {
			var oldPos = position

			// position
			// Apply gravity by getting the velocity we need to add
			velocity.y += Constants.gravity * delta
			// Velocity to change in position
			val newx = position.x + velocity.x * delta
			val newy = position.y + velocity.y * delta
			this.position = Vector2(newx, newy)

			// rotation
			val newPos = position
			val dxyvec = newPos - oldPos
			this.rotation = atan2(dxyvec.y, dxyvec.x)
		}
	}

	@RegisterFunction
	fun exited_screen() {
		this.visible = false
		this.isFlying = false
		this.velocity = Vector2.ZERO
	}

	fun shootFrom(position: Vector2, cosAndSin: CosAndSin, speed: Double) {
		visible = true
		isFlying = true
		this.position = position
		val sx = cosAndSin.cos * speed
		val sy = cosAndSin.sin * speed
		velocity = Vector2(sx, -sy)
	}

}
