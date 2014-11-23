package com.codingbrothers.futurimages.apiv1;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path.Node;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.ConstraintDescriptor;

import com.codingbrothers.futurimages.apiv1.ClientError.Error;
import com.codingbrothers.futurimages.apiv1.util.AnyOf;
import com.codingbrothers.futurimages.apiv1.util.ImageContentMustMatchContentType;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Responses {

	private static final Logger logger = Logger.getLogger(Responses.class.getName());

	// simple erroneous response generator; in the future, replacing it by sth more advanced/flexible would be a must -
	// for v1 api purposes it'll do the trick, tho
	public static ClientError createClientError(Set<ConstraintViolation<Object>> violations,
			ResourceBundle resourceBundle) {
		ClientError clientError = new ClientError();
		clientError.setMessage(resourceBundle.getString("ClientError.message.validationFailed"));

		for (ConstraintViolation<Object> violation : violations) {
			ConstraintDescriptor<?> desc = violation.getConstraintDescriptor();

			String resource = getResourceCompositeNameForConstraintViolation(violation);
			String field = getFieldNameForConstraintViolation(violation);

			// generic missing, missingField errors
			if (desc.getAnnotation() instanceof NotNull) {
				if (field != null) {
					clientError.addError(Error.missingFieldError(resource, field));
				} else {
					clientError.addError(Error.missingError(resource));
				}
			}

			// AnyOf violations
			if (desc.getAnnotation() instanceof AnyOf) {
				clientError.addError(Error.invalidError(resource, field));
			}
			
			// ImageContentMustMatchContentType violation
			if (desc.getAnnotation() instanceof ImageContentMustMatchContentType) {
				// ImageContentMustMatchContentTypeValidator already bound the error to the 'content' property
				// => calling Error.invalidError(resource, field) here is sufficient
				clientError.addError(Error.invalidError(resource, field));
			}
		}

		return clientError;
	}

	private static String getFieldNameForConstraintViolation(ConstraintViolation<Object> violation) {
		Node lastNodeInPath = Iterables.getLast(violation.getPropertyPath());
		if (lastNodeInPath != null && lastNodeInPath.getKind() == ElementKind.PROPERTY) {
			String propName = lastNodeInPath.getName();

			try {
				Class<? extends Object> beanClazz = violation.getLeafBean().getClass();
				PropertyDescriptor propDescriptor = new PropertyDescriptor(propName, beanClazz);
				ApiResourceProperty arpAnnot = propDescriptor.getReadMethod() != null ? propDescriptor.getReadMethod()
						.getAnnotation(ApiResourceProperty.class) : null;
				if (arpAnnot != null && arpAnnot.name() != null)
					return arpAnnot.name();
				arpAnnot = propDescriptor.getWriteMethod() != null ? propDescriptor.getWriteMethod().getAnnotation(
						ApiResourceProperty.class) : null;
				if (arpAnnot != null && arpAnnot.name() != null)
					return arpAnnot.name();
				arpAnnot = beanClazz.getDeclaredField(propName).getAnnotation(ApiResourceProperty.class);
				if (arpAnnot != null && arpAnnot.name() != null)
					return arpAnnot.name();
			} catch (NoSuchFieldException | SecurityException | IntrospectionException e) {
				logger.severe("Couldn't found out field's name of property ConstraintViolation. This shouldn't happen in production!");
			}

			return propName;
		} else {
			return null;
		}
	}

	private static String getResourceCompositeNameForConstraintViolation(ConstraintViolation<Object> violation) {
		List<Node> pathNodeList = Lists.newArrayList(violation.getPropertyPath());
		Node lastNodeInPath = Iterables.getLast(pathNodeList);
		boolean isLastNodePropertyNode = lastNodeInPath != null && lastNodeInPath.getKind() == ElementKind.PROPERTY;
		return Joiner.on(ClientError.Error.RESOURCE_PATH_SEPARATOR).join(
				Iterables.transform(Iterables.filter(
						Iterables.limit(pathNodeList, pathNodeList.size() - (isLastNodePropertyNode ? 1 : 0)),
						new Predicate<Node>() {

							@Override
							public boolean apply(Node node) {
								return node.getKind() == ElementKind.BEAN || node.getKind() == ElementKind.PROPERTY;
							}
						}), new Function<Node, String>() {

					@Override
					public String apply(Node node) {
						return node.getName();
					}
				}));
	}
}
