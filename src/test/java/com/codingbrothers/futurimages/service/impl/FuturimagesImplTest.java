package com.codingbrothers.futurimages.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.jukito.UseModules;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.appengine.testing.GAETestRunner;
import com.codingbrothers.appengine.testing.GAETest;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;
import com.codingbrothers.futurimages.domain.Flip;
import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.codingbrothers.futurimages.domain.User;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.dev.BlobStorageFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig.TaskCountDownLatch;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

@RunWith(GAETestRunner.class)
@UseModules({FuturimagesTestModule.class, FuturimagesCommonModule.class})
public class FuturimagesImplTest {

	private static com.google.appengine.api.images.Image imageData = null;

	@BeforeClass
	public static void createImageData() throws Exception {
		ByteBuffer buffer;
		try (FileChannel fileChannel =
				FileChannel.open(Paths.get(FuturimagesImplTest.class.getResource("/FuturelyticsLogo.png.base64")
						.toURI()))) {
			buffer = ByteBuffer.allocate((int) fileChannel.size());
			fileChannel.read(buffer);
		}
		imageData =
				ImagesServiceFactory.makeImage(BaseEncoding.base64().decode(
						new String(buffer.array(), Charset.forName("US-ASCII"))));
	}

	@Inject
	private FuturimagesImpl futurimages;

	private TaskCountDownLatch latch;

	@Before
	public void setUp(TaskCountDownLatch latch) {
		this.latch = latch;
	}

	@Test
	@GAETest
	@Ignore
	public void testCreateImage() throws Exception {
		Image image = Image.Builder().fromUser(Key.create(User.class, "test")).build();

		futurimages.createImage(image, imageData);

		Key<Image> key = Key.create(image);

		assertTrue(ObjectifyService.ofy().load().key(key).now() == image);

		ObjectifyService.ofy().clear();

		assertTrue(ObjectifyService.ofy().load().key(key).now() != image);

		ObjectifyService.ofy().clear();

		assertTrue(latch.await(5, TimeUnit.SECONDS));

		String blobKey = ObjectifyService.ofy().load().key(key).now().getBlobKey();
		assertNotNull(blobKey);

		try (InputStream stream = BlobStorageFactory.getBlobStorage().fetchBlob(new BlobKey(blobKey))) {
			assertTrue(Arrays.equals(imageData.getImageData(), ByteStreams.toByteArray(stream)));
		}
	}

	@Test
	@GAETest
	@Ignore
	public void testCreateImageTransformation() throws Exception {
		Image image = Image.Builder().fromUser(Key.create(User.class, "test")).build();
		image = futurimages.createImage(image, imageData);

		assertTrue(latch.await(5, TimeUnit.SECONDS));
		latch.reset(1);

		ImageTransformation imageTransformation =
				futurimages.createImageTransformation(Key.create(image), Flip.Builder.create().build());

		ObjectifyService.ofy().clear();

		imageTransformation = ObjectifyService.ofy().load().key(Key.create(imageTransformation)).now();
		assertNotNull(imageTransformation);

		ObjectifyService.ofy().clear();

		assertTrue(latch.await(5, TimeUnit.SECONDS));

		String blobKey = ObjectifyService.ofy().load().key(Key.create(imageTransformation)).now().getBlobKey();
		assertNotNull(blobKey);
	}
}
