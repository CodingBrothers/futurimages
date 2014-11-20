package com.codingbrothers.futurimages.apiv1;

import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;

public class ImagesV1Test extends ApiTestSupport<ImagesV1> {

	@Test
	public void imageToUploadMustBeValid() {
		ImageToUpload image = new ImageToUpload();
		
		// name, content, and contentType must not be null
		Response res = api.uploadImage(image, user);
		assertThat(res, instanceOf(ClientError.class));
//		assertThat(res, IsCollectionContaining<T>);
	}

}
