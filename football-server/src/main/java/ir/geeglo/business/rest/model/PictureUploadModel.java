package ir.geeglo.business.rest.model;

public class PictureUploadModel {
    private String imageData;
    private String previousImage;

    public PictureUploadModel() {
    }

    public PictureUploadModel(String imageData) {
        this.imageData = imageData;
    }

    public PictureUploadModel(String imageData, String previousImage) {
        this.imageData = imageData;
        this.previousImage = previousImage;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getPreviousImage() {
        return previousImage;
    }

    public void setPreviousImage(String previousImage) {
        this.previousImage = previousImage;
    }
}
