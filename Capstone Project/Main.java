import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    // Define the data directory
    private static final String DATA_DIR = "path/to/data";
    private static final String OUTPUT_DIR = "path/to/output";

    // Function to process images
    private static void processImage(String imagePath) {
        try {
            // Load the image
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (image == null) {
                System.err.println("Could not open or find the image: " + imagePath);
                return;
            }

            // Perform image processing operations
            // Example: Convert to grayscale
            BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = grayscaleImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            // Save the processed image
            String savePath = OUTPUT_DIR + "/" + Paths.get(imagePath).getFileName().toString();
            ImageIO.write(grayscaleImage, "jpg", new File(savePath));
            System.out.println("Processed image: " + imagePath);
        } catch (IOException e) {
            System.err.println("Error processing the image: " + e.getMessage());
        }
    }

    // Helper function to apply a filter to a signal
    private static List<Double> applyFilter(List<Double> signal, int windowSize) {
        List<Double> filteredSignal = new ArrayList<>(Collections.nCopies(signal.size(), 0.0));
        for (int i = 0; i < signal.size(); ++i) {
            double sum = 0.0;
            int count = 0;
            for (int j = -windowSize / 2; j <= windowSize / 2; ++j) {
                if (i + j >= 0 && i + j < signal.size()) {
                    sum += signal.get(i + j);
                    ++count;
                }
            }
            filteredSignal.set(i, sum / count);
        }
        return filteredSignal;
    }

    // Function to process signals
    private static void processSignal(String signalPath) {
        // Load the signal data
        List<Double> signalData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(signalPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                signalData.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            System.err.println("Could not open the file: " + signalPath);
            return;
        }

        // Perform signal processing operations
        // Example: Apply a filter
        List<Double> filteredSignal = applyFilter(signalData, 5);

        // Save the processed signal
        String savePath = OUTPUT_DIR + "/" + Paths.get(signalPath).getFileName().toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            for (Double value : filteredSignal) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save the file: " + savePath);
        }
        System.out.println("Processed signal: " + signalPath);
    }

    public static void main(String[] args) {
        // Create the output directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
        } catch (IOException e) {
            System.err.println("Could not create output directory: " + OUTPUT_DIR);
            return;
        }

        // Process images
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIR))) {
            List<Path> imagePaths = paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".jpg") || p.toString().endsWith(".png"))
                    .collect(Collectors.toList());
            for (Path path : imagePaths) {
                processImage(path.toString());
            }
        } catch (IOException e) {
            System.err.println("Error reading data directory: " + DATA_DIR);
        }

        // Process signals
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIR))) {
            List<Path> signalPaths = paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .collect(Collectors.toList());
            for (Path path : signalPaths) {
                processSignal(path.toString());
            }
        } catch (IOException e) {
            System.err.println("Error reading data directory: " + DATA_DIR);
        }

        System.out.println("Processing complete.");
    }
}
