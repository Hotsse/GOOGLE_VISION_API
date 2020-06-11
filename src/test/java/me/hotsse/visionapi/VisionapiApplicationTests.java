package me.hotsse.visionapi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisionapiApplicationTests {

	@Test
	void contextLoads() {
		
		String filePath = "C:/testimg6.jpg";
		String credentialPath = "C:/credentials/nx-ocr-test-356d16dcc6c4.json";
		
		try {
			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialPath))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			
			List<AnnotateImageRequest> requests = new ArrayList<>();

			ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			
			requests.add(request);
		  
			ImageAnnotatorSettings imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
					.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
					.build();

			try (ImageAnnotatorClient client = ImageAnnotatorClient.create(imageAnnotatorSettings)) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();
				
				String alltext = "";

				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						System.out.printf("Error: %s\n", res.getError().getMessage());
						return;
					}

					// For full list of available annotations, see http://g.co/cloud/vision/docs
					for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
						alltext += annotation.getDescription();
						System.out.printf("Text: %s\n", annotation.getDescription());
						System.out.printf("Position : %s\n", annotation.getBoundingPoly());
					}
				}
				
				System.out.printf("%s\n", alltext);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
