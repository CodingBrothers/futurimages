package com.codingbrothers.futurimages.apiv1;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;

import com.codingbrothers.appengine.testing.GAETest;
import com.codingbrothers.futurimages.apiv1.ClientError.Error;

public class ImagesV1Test extends ApiTestSupport<ImagesV1> {

	// TODO - add/finalize all v1 API tests!!! Not really the TDD approach, yes. But better late than never.

	@Test
	@GAETest
	public void imageToUploadMustBeValid() throws Exception {
		ImageToUpload image = new ImageToUpload();

		// name, content, and contentType must not be null/blank
		image.setName("");
		Response res = api.uploadImage(image, user);
		assertThat(res, instanceOf(ClientError.class));
		ClientError errRes = (ClientError) res;
		assertThat(errRes.getErrors(), IsCollectionContaining.hasItems(Error.invalidError("", "name"),
				Error.missingFieldError("", "content"), Error.missingFieldError("", "content_type")));

		// only a subset of image media types is allowed
		image.setContentType("image/svg+xml");
		res = api.uploadImage(image, user);
		assertThat(res, instanceOf(ClientError.class));
		errRes = (ClientError) res;
		assertThat(errRes.getErrors(), IsCollectionContaining.hasItems(Error.invalidError("", "content_type")));

		// content must match provided contentType and must be a Base64 string
		image.setName("Futurelytics Logo");
		try (FileChannel fLogoFC =
				FileChannel.open(Paths.get(ImagesV1Test.class.getResource("/FuturelyticsLogo.png.base64").toURI()))) {
			ByteBuffer buffer = ByteBuffer.allocate((int) fLogoFC.size());
			fLogoFC.read(buffer);
			image.setContent(new String(buffer.array(), Charset.forName("US-ASCII")));
		}
		image.setContentType("image/jpeg"); // the content is of type image/png
		res = api.uploadImage(image, user);
		assertThat(res, instanceOf(ClientError.class));
		errRes = (ClientError) res;
		assertThat(errRes.getErrors(), IsCollectionContaining.hasItems(Error.invalidError("", "content")));

		image.setContentType("image/png");
		res = api.uploadImage(image, user);
		assertThat(res, not(instanceOf(ClientError.class)));
	}

	@Test
	@GAETest
	public void imageUploadRequiresUserContext() throws Exception {
		ImageToUpload image = new ImageToUpload();
		image.setName("Futurelytics Logo");
		image.setContentType("image/png");
		try (FileChannel fLogoFC =
				FileChannel.open(Paths.get(ImagesV1Test.class.getResource("/FuturelyticsLogo.png.base64").toURI()))) {
			ByteBuffer buffer = ByteBuffer.allocate((int) fLogoFC.size());
			fLogoFC.read(buffer);
			image.setContent(new String(buffer.array(), Charset.forName("US-ASCII")));
		}

		Response res = api.uploadImage(image, null); // passing null as 'user' parameter value
		assertThat(res, instanceOf(ClientError.class));

		res = api.uploadImage(image, user);
		assertThat(res, not(instanceOf(ClientError.class)));
	}

}
