package ir.geeglo.business.utility;

import ir.piana.dev.secure.random.SecureRandomMaker;
import ir.piana.dev.secure.random.SecureRandomType;
import ir.piana.dev.secure.util.Base64Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Repository
@PropertySource(value = "classpath:application.properties")
public class ImageUtility {
    @Value("${resource.asset.path}")
    private String assetPath;

    public String saveImage(String imageBase64, String dirName) {
        String imgStr = imageBase64;
        String imgType = imgStr.substring(11, imgStr.indexOf(';'));
        String imgName = null;
        String imagePath = null;
        try {
            imgName = Base64Converter.toBase64String(
                    SecureRandomMaker.makeByteArray(
                            64, SecureRandomType.SHA_1_PRNG));

            imgName = imgName.replaceAll("/", "+");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] data = Base64Converter.fromBase64String(
                imgStr.substring(imgStr.indexOf("base64") + 7));
        try {
            File imageFile = new File(assetPath.concat("/".concat(dirName)));
            imageFile.mkdirs();
            String filePath = assetPath.concat("/".concat(dirName).concat("/"))
                    .concat(imgName).concat(".").concat(imgType);
            try (OutputStream stream = new FileOutputStream(filePath)) {
                stream.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "piana-asset".concat("/".concat(dirName).concat("/"))
                .concat(imgName).concat(".").concat(imgType);
    }
}
