package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.domain.Rotate;
import com.codingbrothers.futurimages.domain.Transform;

public class RotateOp extends TransformationOp {

	public static enum Direction {
		LEFT, RIGHT
	}

	@NotNull
	private Direction direction;
	@NotNull
	private Integer degrees; // add minimum/maximum, modulo constraints

	public RotateOp() {
		super(OpType.ROTATE);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Integer getDegrees() {
		return degrees;
	}

	public void setDegrees(Integer degrees) {
		this.degrees = degrees;
	}

	@Override
	public Transform asTransform(Object... args) {
		return Rotate.Builder.create()
				.degrees((direction == Direction.LEFT ? Math.abs(-degrees - 180) : degrees) % 360).build();
	}
}
