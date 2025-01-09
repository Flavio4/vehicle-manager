package com.rtz.vehicle_manager.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import com.rtz.vehicle_manager.configurations.CloudinaryConfiguration;
import com.rtz.vehicle_manager.entities.Image;
import com.rtz.vehicle_manager.errors.ImageNotfoundException;
import com.rtz.vehicle_manager.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CloudinaryConfiguration cloudinaryConfiguration;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Method that is responsible for uploading images to Cloudinary and generating the Image entity
     * In case of error when uploading an image, a rollback of the uploaded images is performed and an exception is thrown
     * @param images List of images to upload
     * @return List of Image entities
     */
    public List<Image> processAndUploadCarImages(List<MultipartFile> images) {
        List<Image> carImages = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            // Check if the file is an image
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Unos de los archivos cargados no es una imagen");
            }
            try {
                //Create a new Image entity and upload it to Cloudinary
                Image carImage = new Image();
                carImage.setName(generateCarImageName());
                String imageUrl = uploadImageToCloudinary(image, carImage.getName());
                carImage.setUrl(imageUrl);
                //Add the image to the list
                carImages.add(carImage);
                //Save the image url to delete it in case of error
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                //Rollback the uploaded images
                rollbackUploadedImages(imageUrls);
                throw new RuntimeException("Ha ocurrido un error al subir la imagen: " + e.getMessage());
            }

        }
        return carImages;
    }

    /**
     * Method that is responsible for deleting an image from Cloudinary and the database
     * In case of error when deleting an image, an exception is thrown
     * @param imageId ID of the image to delete
     */
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotfoundException("No se ha encontrado la imagen con ID: " + imageId));
        try {
            // Public_ID is folder/imageName
            String publicId = cloudinaryConfiguration.getFolder() + "/" + image.getName();
            cloudinary.uploader().destroy(publicId, null);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new RuntimeException("Ha ocurrido un error al eliminar la imagen: " + e.getMessage());
        }
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
     * Method that is responsible for performing a rollback of the uploaded images
     * @param imageUrls List of image URLs to delete
     */
    private void rollbackUploadedImages(List<String> imageUrls) {
        if (!imageUrls.isEmpty()) {
            List<String> imagesPublicId= new ArrayList<>();
            for (String url : imageUrls) {
                String publicId = url.substring(url.indexOf("v1/") + 3);
                System.out.println("Public id: "+ publicId);
                imagesPublicId.add(publicId);
            }
            try {
                ApiResponse deleteResult = cloudinary.api().deleteResources(imagesPublicId, Collections.emptyMap());
                System.out.println("Delete result: " + deleteResult);
            } catch (Exception ex) {
                throw new RuntimeException("Ha ocurrido un error al realizar el rollback de las imágenes: " + ex.getMessage());
            }
        }
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
