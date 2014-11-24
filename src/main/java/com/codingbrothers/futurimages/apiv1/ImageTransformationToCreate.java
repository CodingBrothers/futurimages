package com.codingbrothers.futurimages.apiv1;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ImageTransformationToCreate implements Response {

	@NotBlank
	private String name;
	private String description;
	@NotEmpty
	@Valid
	private List<TransformationOp> transformations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TransformationOp> getTransformations() {
		return transformations;
	}

	public void setTransformations(List<TransformationOp> transformations) {
		this.transformations = transformations;
	}

}
