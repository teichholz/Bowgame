package com.example.game.bow

import com.example.game.node
import com.example.game.scene
import com.example.game.vector.Vector
import godot.*
import godot.annotation.*
import godot.core.Color
import godot.core.PoolVector2Array
import godot.core.Vector2
import godot.extensions.getNodeAs
import godot.extensions.loadAs
import godot.global.GD
import godot.signals.signal
import java.lang.Double.min
import java.lang.Math.round

@RegisterClass
class Orchestrator : Node2D() {

	@RegisterProperty
	@Export
	var gravity: Double = 50.0 /* px/s*s */

	@RegisterProperty
	@Export
	var minSpeed: Double = 50.0 /* px/s */

	@RegisterProperty
	@Export
	var maxSpeed: Double = 250.0 /* px/s */
	  set(s) {
		  field = s
		  speed = s
	  }

	var speed = maxSpeed;

	@RegisterProperty
	@Export
	var drawTime: Double = 2.0 /* s */

	// Childs
	val bow: Bow by lazy { node("Bow") }
	val gCurve: GCurve by lazy { node("Bow/GCurve") }
	val speedBar: ProgressBar by lazy { node("Bow/SpeedBar") }
	val vector: Vector by lazy { node("Bow/SpeedBar") }

	// Vars
	var canFix = true
	var fix = false
	var shoot = false
	var drawTimer = 0.0


	// Signals
	@RegisterSignal
	val signalArrowEnteredBody by signal<Node>("body")

	// Called when the node enters the scene tree for the first time.
	@RegisterFunction
	override fun _ready() {
	}

	// Called every frame. 'delta' is the elapsed time since the previous frame.
	@RegisterFunction
	override fun _process(delta: Double) {
		if (!fix) {
			bow.position = getGlobalMousePosition()
			bow.rotation = 0.0
			gCurve.doNotDraw()
			drawTimer = 0.0
			bow.frame = 0
			speedBar.value = 0.0
		}

		if (fix) {
			drawTimer += delta
			val zeroToOneSpeed = (min(drawTimer, drawTime) / drawTime)
			val speed = zeroToOneSpeed * (maxSpeed - minSpeed) + minSpeed
			bow.animation = "pull"
			speedBar.value = zeroToOneSpeed * speedBar.maxValue
			val frameToUse : Long = round(zeroToOneSpeed * 8)
			bow.frame = frameToUse

			var mousePos = getGlobalMousePosition()
			var oppositeDir = (mousePos - bow.position).normalized().times(-1)
			bow.rotation = oppositeDir.angle()
			val cosAndSin = bow.angle(oppositeDir)

			gCurve.draw(bow.position, speed, gravity, cosAndSin)
			if (shoot) {
				val arrow: Arrow = ResourceLoader.scene<Arrow>("Arrow")
				addChild(arrow)
				arrow.shootFrom(bow.position, cosAndSin, speed, gravity, maxSpeed)
				arrow.signalArrowEnteredBody.connect(this, this::arrowEnteredBody)
				shoot = false
				fix = false
			}

		}
	}

	@RegisterFunction
	override fun _input(event: InputEvent) {
		if (fix && event.isActionReleased("mouse_down")) {
			shoot = true
		}

		if (event.isActionPressed("mouse_down")) {
			if (canFix)
				fix = true
		}
	}

	@RegisterFunction
	fun arrowEnteredBody(body: Node) {
		signalArrowEnteredBody.emit(body)
	}
}
