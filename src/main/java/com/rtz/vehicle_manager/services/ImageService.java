package com.rtz.vehicle_manager.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.rtz.vehicle_manager.configurations.CloudinaryConfiguration;
import com.rtz.vehicle_manager.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CloudinaryConfiguration cloudinaryConfiguration;

    /**
     * Method that is responsible for uploading images to Cloudinary and generating the Image entity
     * In case of error when uploading an image, a rollback of the uploaded images is performed and an exception is thrown
     * @param images List of images to upload
     * @return List of Image entities
     */
    public List<Image> processAndUploadCarImages(List<MultipartFile> images) {
        List<Image> carImages = new ArrayList<>();
        for (MultipartFile image : images) {
            // Check if the file is an image
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Unos de los archivos cargados no es una imagen");
            }
            String imageName = generateCarImageName();
            Image carImage = new Image();
            carImage.setName(imageName);
            try {
                carImage.setUrl(uploadImageToCloudinary(image, imageName));
            } catch (IOException e) {
                // TODO: Rollback uploaded images
                throw new RuntimeException("Error uploading image");
            }
            carImages.add(carImage);
        }
        return carImages;
    }

    /**
     * Method that is responsible for uploading an image to Cloudinary
     * In case of error when uploading an image, an exception is thrown
     * @param file Image to upload
     * @return URL of the uploaded image
     */
    private String uploadImageToCloudinary(MultipartFile file, String fileName) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", cloudinaryConfiguration.getFolder());
        options.put("public_id", fileName);
        Map<?,?> uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
        String publicId = (String) uploadedFile.get("public_id");
        Transformation<?> transformation = new Transformation<>();
        transformation.quality("auto");
        return cloudinary.url().secure(true).transformation(transformation).generate(publicId);
    }

    /**
     * Method that generates a name for the image
     * @return Image name
     */
    private String generateCarImageName() {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        return "vehicle_" + uuid.toString()+ "-" + now.toString();
    }


}
