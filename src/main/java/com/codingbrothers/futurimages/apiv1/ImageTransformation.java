package com.codingbrothers.futurimages.apiv1;

import java.util.List;

import com.google.api.server.spi.config.ApiResourceProperty;

public class ImageTransformation implements Response {

	private String id;
	private String baseImageURL;
	private List<TransformationOp> appliedTransformationOps;
	private String createdAt;
	private String name;
	private String description;
	private User createdBy;
	private String contentURL;
	private String contentType;
	private Integer width;
	private Integer height;
	private Integer size;
	private ViewType viewType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ApiResourceProperty(name = "base_image_url")
	public String getBaseImageURL() {
		return baseImageURL;
	}

	public void setBaseImageURL(String baseImageURL) {
		this.baseImageURL = baseImageURL;
	}

	@ApiResourceProperty(name = "applied_transformations")
	public List<TransformationOp> getAppliedTransformationOps() {
		return appliedTransformationOps;
	}

	public void setAppliedTransformationOps(List<TransformationOp> appliedTransformationOps) {
		this.appliedTransformationOps = appliedTransformationOps;
	}

	@ApiResourceProperty(name = "created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

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

	@ApiResourceProperty(name = "created_by")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@ApiResourceProperty(name = "content_url")
	public String getContentURL() {
		return contentURL;
	}

	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}

	@ApiResourceProperty(name = "content_type")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@ApiResourceProperty(name = "view_type")
	public ViewType getViewType() {
		return viewType;
	}

	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}

}
