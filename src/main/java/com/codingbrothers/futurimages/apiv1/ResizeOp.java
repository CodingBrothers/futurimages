package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.domain.RelativeResize;
import com.codingbrothers.futurimages.domain.Transform;

public class ResizeOp extends TransformationOp {

	@NotNull
	@Min(0)
	private Integer percent; // add maximum value constraint

	public ResizeOp() {
		super(OpType.RESIZE);
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	@Override
	public Transform asTransform(Object... args) {
		return RelativeResize.Builder.create((int) args[0], (int) args[1]).build();
	}
}
