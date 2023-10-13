package hackathon.ekta.Scan2Invest;


import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
public class ImageToCompany {

    public List<String> getCompany(MultipartFile file) throws Exception {
        List<String> stringList = new ArrayList<>();
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            byte[] data = StreamUtils.copyToByteArray(file.getInputStream());
            ByteString imgBytes = ByteString.copyFrom(data);
      
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Type.LOGO_DETECTION).build();
            AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);
      
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            
            for (AnnotateImageResponse res : responses) {
              if (res.hasError()) {
                System.out.format("Error: %s%n", res.getError().getMessage());
                return stringList;
              }
              for (EntityAnnotation annotation : res.getLogoAnnotationsList()) {
                System.out.println(annotation.getDescription());
                stringList.add(annotation.getDescription());
            }
      
            }
          }
          return stringList;
    }
    
}
