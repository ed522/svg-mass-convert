package com.ed522.svgmassconvert;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.LoaderContext;
import com.github.weisj.jsvg.parser.SVGLoader;
import com.github.weisj.jsvg.view.ViewBox;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;

    @FXML
    private Spinner<Integer> dimensionX;
    @FXML
    private Spinner<Integer> dimensionY;

    private File source;
    private File destination;

    @FXML
    protected void browseSource() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select source directory");
        File f = dc.showDialog(null);

        if (f != null) {
            this.source = f;
            this.sourceField.setText(f.getAbsolutePath());
        }
    }

    @FXML
    protected void browseDestination() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select destination directory");
        File f = dc.showDialog(null);

        if (f != null) {
            this.destination = f;
            this.destinationField.setText(f.getAbsolutePath());
        }
    }

    @FXML
    protected void convertAll() throws IOException {

        this.source = new File(this.sourceField.getText());
        this.destination = new File(this.destinationField.getText());

        if (!source.exists() || !destination.exists() || !source.isDirectory() || !destination.isDirectory()) {
            return;
        }

        File[] sourceFiles = this.source.listFiles();
        if (sourceFiles == null) return;
        File[] destinationFiles = Arrays.stream(sourceFiles)
                .map(f -> new File(this.destination, getWithoutExtension(f) + ".png"))
                .toArray(File[]::new);

        Map<File, File> sourceToDestination = new HashMap<>();
        for (int i = 0; i < sourceFiles.length; i++) {
            sourceToDestination.put(sourceFiles[i], destinationFiles[i]);
        }

        LoaderContext context = LoaderContext.builder().build();

        for (Map.Entry<File, File> entry : sourceToDestination.entrySet()) {
            try (FileInputStream read = new FileInputStream(entry.getKey())) {
                SVGLoader loader = new SVGLoader();
                SVGDocument doc = loader.load(read, null, context);

                if (doc == null) {
                    System.err.println("Error loading SVG file: " + entry.getKey());
                    continue;
                }

                doc.sizeForViewport(new ViewBox(dimensionX.getValue(), dimensionY.getValue()));

                BufferedImage image = new BufferedImage(dimensionX.getValue(), dimensionY.getValue(), BufferedImage.TYPE_INT_ARGB);
                doc.render(null, image.createGraphics(), new ViewBox(dimensionX.getValue(), dimensionY.getValue()));

                if (!entry.getValue().createNewFile()) {
                    System.err.println("Already exists: " + entry.getValue());
                    continue;
                }

                // write out
                try (FileOutputStream write = new FileOutputStream(entry.getValue())) {
                    ImageIO.write(image, "png", write);
                }
            }
        }
    }

    public static String getWithoutExtension(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf(".");

        // -1: no extension | 0: oops all extension (hidden file)
        if (dotIndex == -1 || dotIndex == 0) {
            return name;
        }

        return name.substring(0, dotIndex);
    }


    public static class ImageCanvas extends Canvas {
        private final java.awt.Image image;

        public ImageCanvas(java.awt.Image image) {
            // Load the image file asynchronously via Toolkit
            this.image = image;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (image != null) {
                // Draws the image at coordinate position (0, 0)
                g.drawImage(image, 0, 0, this);
            }
        }
    }
}
