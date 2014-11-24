package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.apiv1.util.EnumLowerCaseStringTransformer;
import com.codingbrothers.futurimages.domain.Transform;

public abstract class TransformationOp {

	public static enum OpType {
		RESIZE, FLIP, ROTATE;

		public static class OpTypeTransformer extends EnumLowerCaseStringTransformer<OpType> {

			public OpTypeTransformer() {
				super(OpType.class);
			}

		}
	}

	@NotNull
	private OpType type;

	public TransformationOp(OpType type) {
		this.type = type;
	}

	public OpType getType() {
		return type;
	}

	public void setType(OpType type) {
		this.type = type;
	}
	
	public abstract Transform asTransform(Object... args);

}
