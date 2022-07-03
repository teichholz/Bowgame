package com.example.game.bow

import com.example.game.CosAndSin
import godot.AnimatedSprite
import godot.Node
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.annotation.RegisterSignal
import godot.core.Vector2
import godot.signals.signal
import kotlin.math.atan2

@RegisterClass
class Arrow : AnimatedSprite() {

	var isFlying: Boolean = false
	var velocity: Vector2 = Vector2.ZERO
	var gravity: Double = 0.0

	var timeIncrease: Double = 0.0


	@RegisterSignal
	val signalArrowEnteredBody by signal<Node>("body")

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		if (isFlying) {
			var oldPos = position

			// would be cool to also consider how long the arrow to takes to fly out of the window
			val normalizedDelta = delta * timeIncrease

			// position
			// Apply gravity by getting the velocity we need to add
			velocity.y += gravity * normalizedDelta
			// Velocity to change in position
			val newx = position.x + velocity.x * normalizedDelta
			val newy = position.y + velocity.y * normalizedDelta
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

	@RegisterFunction
	fun bodyEntered(body: Node) {
			signalArrowEnteredBody.emit(body)
	}

	fun shootFrom(position: Vector2, cosAndSin: CosAndSin, speed: Double, gravity: Double, maxSpeed: Double) {
		this.visible = true
		this.isFlying = true
		this.position = position
		this.gravity = gravity
		this.timeIncrease = maxSpeed / speed;
		val sx = cosAndSin.cos * speed
		val sy = cosAndSin.sin * speed
		velocity = Vector2(sx, -sy)
	}

}
