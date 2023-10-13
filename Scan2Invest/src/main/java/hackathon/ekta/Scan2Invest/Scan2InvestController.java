package hackathon.ekta.Scan2Invest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

@RestController
public class Scan2InvestController {

  @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestPart("file") MultipartFile file, ModelMap model) throws IOException {
        List<String> s = new ArrayList<>();
        ImageToCompany img = new ImageToCompany();
        try {
          s= img.getCompany(file);

        } catch (Exception e) {
          
          e.printStackTrace();
        }
        
          byte[] data = StreamUtils.copyToByteArray(file.getInputStream());
          String i = Base64.encodeBase64String(data);
      
        model.addAttribute("stringList", s);
        model.addAttribute("image", i);
        return new ModelAndView("result", model);
    }
}