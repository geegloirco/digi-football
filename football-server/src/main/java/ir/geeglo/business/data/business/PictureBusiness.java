package ir.geeglo.business.data.business;

import com.google.api.client.util.Base64;
import ir.geeglo.business.rest.model.PictureUploadModel;
import ir.piana.dev.secure.random.SecureRandomMaker;
import ir.piana.dev.secure.random.SecureRandomType;
import ir.piana.dev.secure.util.Base64Converter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@PropertySource(value = "classpath:application.properties")
public class PictureBusiness {
    static Logger logger = Logger.getLogger(PictureBusiness.class);
    @Value("${image.store.path}")
    private String imageStorePath;

    @PostConstruct
    public void postConstruct() {
        if(imageStorePath == null){
            throw new RuntimeException("imageStorePath is null");
        }
    }

    private Map<String, String> pictureMap = new LinkedHashMap();

    public String add(PictureUploadModel uploadModel) {
        if(uploadModel.getPreviousImage() != null)
            return replace(uploadModel);

        String key = null;
        try {
            key = Base64Converter.toBase64String(
                    SecureRandomMaker.makeByteArray(
                            16, SecureRandomType.SHA_1_PRNG));
            pictureMap.put(key, uploadModel.getImageData());
            return key;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public String replace(PictureUploadModel uploadModel) {
        String key = null;
        if(uploadModel.getPreviousImage() != null &&
                uploadModel.getImageData() != null) {
            pictureMap.remove(uploadModel.getPreviousImage());
            try {
//                key = Base64Converter.toBase64String(
//                        SecureRandomMaker.makeByteArray(
//                                16, SecureRandomType.SHA_1_PRNG));
                pictureMap.put(uploadModel.getPreviousImage(),
                        uploadModel.getImageData());
                return uploadModel.getImageData();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return null;
    }

    public boolean remove(String imageKey) {
        if(imageKey != null) {
            pictureMap.remove(imageKey);
            return true;
        }
        return false;
    }

    public String saveImage(String key) {
        String imgStr = pictureMap.get(key);
        String imgType = imgStr.substring(11, imgStr.indexOf(';'));
        String imgName = null;
        try {
            imgName = Base64Converter.toBase64String(
                    SecureRandomMaker.makeByteArray(
                            64, SecureRandomType.SHA_1_PRNG));

            imgName = imgName.replaceAll("/", "+");

        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        byte[] data = Base64.decodeBase64(imgStr.substring(imgStr.indexOf("base64") + 7));

        try {
            File imageFolder = new File(imageStorePath);
            if(!imageFolder.exists())
                imageFolder.mkdirs();
            String filePath = imageStorePath.concat("/")
                    .concat(imgName).concat(".").concat(imgType);
            try (OutputStream stream = new FileOutputStream(filePath)) {
                stream.write(data);
            }
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
        return "image/".concat(imgName).concat(".").concat(imgType);
    }

    public boolean exist(String key) {
        if(pictureMap.containsKey(key))
            return true;
        return false;
    }
}
