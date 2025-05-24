package com.api.service;

import com.api.utils.Utils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ImageService {
	/**
	 * Resize image to the target width height
	 *
	 * @param file image file
	 * @param dMin minimum image's down-scaled dimension size
	 * @return resized image byte[] as input stream
	 * @throws IOException input stream read/write error
	 */
	public InputStream resizeImage(@NonNull MultipartFile file, Integer dMin)
		throws IOException {

		// validate file null
		if (file.isEmpty()) {
			throw new FileNotFoundException("Upload image file is empty");
		}

		// resized the image and write it back as input stream
		try (InputStream fileInputStream = file.getInputStream();
			 ByteArrayOutputStream os = new ByteArrayOutputStream()) {

			// read base image
			final BufferedImage bufferedImage = ImageIO.read(fileInputStream);

			// down-scale the original image
			int[] scaledSize = downScaleImageSize(bufferedImage.getWidth(), bufferedImage.getHeight(), dMin);

			// init buffered image
			BufferedImage resizedImage = new BufferedImage(scaledSize[0], scaledSize[1], BufferedImage.TYPE_INT_RGB);
			// draw thumbnail image with custom target width height
			Graphics2D graphics2D = resizedImage.createGraphics();
			graphics2D.drawImage(ImageIO.read(fileInputStream), 0, 0, scaledSize[0], scaledSize[1], null);
			graphics2D.dispose();

			ImageIO.write(resizedImage, "jpeg", os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException ioe) {
			Utils.logStackTrace(ioe, log);
		}
		return InputStream.nullInputStream();
	}

	/**
	 * get the maximum-able down-scaled size of the image based on the target min-size
	 *
	 * @param imageWidth    the current image width
	 * @param imageHeight   the current image height
	 * @param targetMinSize the target min-size
	 * @return the down-scaled sizes
	 */
	private int[] downScaleImageSize(int imageWidth, int imageHeight, Integer targetMinSize) {
		int scaledWidth = imageWidth;
		int scaledHeight = imageHeight;
		if (scaledWidth > targetMinSize && scaledHeight > targetMinSize) {
			// if the image's width is smaller than its height, scaled by image width
			if (imageWidth < imageHeight) {
				scaledWidth = (int) (imageWidth / ((float) imageWidth / targetMinSize));
				scaledHeight = (int) (scaledWidth / ((float) imageWidth / imageHeight));
			} else {
				scaledHeight = (int) (imageHeight / ((float) imageHeight / targetMinSize));
				scaledWidth = (int) (scaledHeight / ((float) imageHeight / scaledWidth));
			}
		}
		return new int[]{scaledWidth, scaledHeight};
	}
}
