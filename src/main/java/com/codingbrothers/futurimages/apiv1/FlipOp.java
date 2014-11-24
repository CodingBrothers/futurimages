package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.domain.Flip;
import com.codingbrothers.futurimages.domain.Transform;

public class FlipOp extends TransformationOp {

	public static enum Direction {
		HORIZONTAL, VERTICAL
	}

	@NotNull
	private Direction direction;

	public FlipOp() {
		super(OpType.FLIP);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public Transform asTransform(Object... args) {
		return Flip.Builder.create().setHorizontal(direction != Direction.VERTICAL).build();
	}

}
