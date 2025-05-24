package com.api.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@UtilityClass
public class FileUtils {
	public static final List<String> EXTENSION_ACCEPT_IMAGE = List.of("image/jpg", "image/png");

	public static File convertMultiPartFileToFile(@NonNull final MultipartFile multipartFile) {
		final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException | NullPointerException ex) {
			log.error("Error converting the multi-part file to file: {} ", ex.getMessage());
		}
		return file;
	}
}
