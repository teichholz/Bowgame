package com.example.game

import com.example.game.bow.Bow
import com.example.game.vector.Vector
import godot.*
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Color
import godot.core.NodePath
import godot.core.Vector2
import godot.extensions.getNodeAs
import godot.extensions.loadAs
import godot.global.GD

// Extensions
fun AnimatedSprite.center(): Vector2 {
	return this.position
}

fun <T> Node.getOwner(): T {
	return owner as T
}

inline fun <reified T> Node.node(path: String): T {
	var node = getNode(NodePath(path)) ?: {
		GD.printErr("${path} does not exist")
		System.exit(404)
	}

	if (node !is T) {
		GD.printErr("${node} should be of type ${T::class.java} but is ${node.javaClass}")
		System.exit(404)
	}

	return node as T
}

inline fun <reified T : Node> ResourceLoader.scene(sceneName: String, typeHint: String = "", noCache: Boolean = false): T {
	val scene = ResourceLoader.loadAs<PackedScene>("res://${sceneName}.tscn") ?: {
		GD.printErr("res://${sceneName}.tscn")
		System.exit(404)
	} as PackedScene

	val node = scene.instance()!!

	if (node !is T) {
		GD.printErr("${node} should be of type ${T::class.java} but is ${node.javaClass}")
		System.exit(404)
	}
	return node as T
}


@RegisterClass
class Simple: Node2D() {

	lateinit var bow: Bow
	var mousePos: Vector2 = getGlobalMousePosition()
	var time: Double = 0.0

	@RegisterFunction
	override fun _ready() {
		bow = getNodeAs<Bow>("Bow")!!
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		time += delta

		if(time > 0.1) {
			time = 0.0
		}
	}

	@RegisterFunction
	override fun _draw() {
		drawCircle(bow.center(), 4.0, Color.aqua)
	}


	@RegisterFunction
	override fun _input(event: InputEvent) {
		if (event is InputEventMouseMotion) {
			// get direction the cursor points to from bow
			mousePos = event.position
		}
		if (event is InputEventMouseButton) {
			if (event.pressed) {
				// bow.shootArrow(bow.angle(mousePos), 300.0)
			}
		}
	}

}
