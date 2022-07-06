import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    private static final Marker EXCEPTIONS_MARKER = MarkerFactory.getMarker("EXCEPTIONS");
    private static final Marker BAD_PATH_MARKER = MarkerFactory.getMarker("BAD_PATH");

    public static void copyFolder(String sourceDirectory, String destinationDirectory) {
        Path source = Paths.get(sourceDirectory);
        if (Files.notExists(source)) {
            System.out.println("Введен некорректный адрес исходной папки (файла)");
            LOGGER.info(BAD_PATH_MARKER, "Некорректный адрес источника:" + System.lineSeparator() + sourceDirectory);
            return;
        }

        Path destination = Paths.get(destinationDirectory);
        if (Files.notExists(destination)) {
            try {
                Files.createDirectories(destination);
            } catch (Exception e) {
                LOGGER.error(EXCEPTIONS_MARKER, "ошибка создания директории назначения", e);
                e.printStackTrace();
                return;
            }
        }

        if (Files.isDirectory(source)){
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)){
                stream.forEach(file -> {
                    if (Files.isDirectory(file.toAbsolutePath())) {
                        copyFolder(file.toAbsolutePath().toString(), destinationDirectory + "\\" + file.getFileName());
                    } else {
                        try {
                            Files.copy(file.toAbsolutePath(), destination.resolve(file.getFileName()));
                        } catch (IOException e) {
                            LOGGER.error(EXCEPTIONS_MARKER, "Ошибка копирования файла", e);
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                LOGGER.error(EXCEPTIONS_MARKER, "ошибка при копировании директории", e);
                e.printStackTrace();
            }
        } else {
            try {
                Files.copy(source, destination.resolve(source.getFileName()));
            } catch (IOException e) {
                LOGGER.error(EXCEPTIONS_MARKER, "Ошибка копирования файла", e);
                e.printStackTrace();
            }

        }
    }
}
