/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.hbarquin.myphotoapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author xavier
 */
public class ImageProcessing {
    private static int MAX_LEVEL = 256;
    
    private File file;
    private BufferedImage image;
    
    private boolean isGrayScale;
    private int minGrayValue;
    private int maxGrayValue;
    
    private int[] redLevels;
    private int[] greenLevels;
    private int[] blueLevels;
    
    private int[] grayLevels;
    
    private int[][] pixels;
    
    private Histogram histogram;
    private Histogram accHistogram;
    
    private int bright;
    private int contrast;
    private double entropy;
    
    private boolean isRotated;
    private int outOfImageCount;
    
    public ImageProcessing (File file) {
        initializeImageData(file);
    }
    
    public ImageProcessing (ImageProcessing ip) {
        this.file = new File(ip.file.getAbsolutePath());
        this.image = deepCopy(ip.image);
        
        this.isRotated = ip.isRotated;
        this.outOfImageCount = ip.outOfImageCount;
        
        setDynamicData();
    }

    public ImageProcessing (BufferedImage bi, String path) {
        this.image = bi;
        this.file = new File(path);
        
        this.isRotated = false;
        this.outOfImageCount = 0;
        
        setDynamicData();
    }
    
    public void setImageFile (File file) {
        initializeImageData(file);
    }
    
    public void setFilename (String newFilename) {
        this.file = new File(newFilename);
    }
    
    public boolean isGrayScale () {
        return this.isGrayScale;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getName() {
        return this.file.getName();
    }
    
    public String getNameWithoutExtension() {
        return this.file.getName().replaceFirst("[.][^.]+$", "");
    }
    
    public String getPathWithoutExtension() {
        return this.file.getAbsolutePath().replaceFirst("[.][^.]+$", "");
    }
    
    public int getWidth () {
        return this.image.getWidth();
    }
    
    public int getHeight () {
        return this.image.getHeight();
    }
    
    public int getBright () {
        return this.bright;
    }
    
    public int getContrast () {
        return this.contrast;
    }
    
    public double getEntropy () {
        return this.entropy;
    }
    
    public int getMinGrayValue () {
        if (!isGrayScale()) {
            return -1;
        }        
        return this.minGrayValue;
    }
    
    public int getMaxGrayValue () {
        if (!isGrayScale()) {
            return -1;
        }        
        return this.maxGrayValue;
    }
    
    public int getPixel (int posX, int posY) {
        return pixels[posX][posY];
    }
    
    public Map<String, Integer> getRGB (int posX, int posY) {
        return processRGBData(posX, posY);
    }
    
    public int getAlpha (int posX, int posY) {
        return processRGBData(posX, posY).get("alpha");
    }
    
    public int getRed (int posX, int posY) {
        return processRGBData(posX, posY).get("red");
    }
    
    public int getGreen (int posX, int posY) {
        return processRGBData(posX, posY).get("green");
    }
    
    public int getBlue (int posX, int posY) {
        return processRGBData(posX, posY).get("blue");
    }
    
    public int[] getRedLevels () {
        return redLevels;
    }
    
    public int[] getGreenLevels () {
        return greenLevels;
    }
    
    public int[] getBlueLevels () {
        return blueLevels;
    }
    
    public int[] getGrayLevels () {
        return grayLevels;
    }
    
    public Histogram getHistogram () {
        if (this.histogram == null) {
            System.out.println("Histograma nulo, recalculando!!!");
            setHistogram();
        }
        return this.histogram;
    }
    
    public Histogram getAccumulateHistogram () {
        if (this.accHistogram == null) {
            System.out.println("Histograma acumulado nulo, recalculando!!!");
            setAccumulateHistogram();
        }
        return this.accHistogram;
    }
    
    public double[][] getNormalizedAccumulateHistogram () {
        double[][] bands;
        int size = getWidth() * getHeight();
        
        if (isGrayScale()) {
            bands = new double[MAX_LEVEL][1];
            for (int i = 0; i < MAX_LEVEL; i++) {
                int value = this.accHistogram.getYValue("Gray", i);
                double newval = ((double)value / (double)size * 100) / 100;
                bands[i][0] = newval;
            }
        } else {
            bands = new double[MAX_LEVEL][3];
            for (int i = 0; i < MAX_LEVEL; i++) {
                int value = this.accHistogram.getYValue("Red", i);
                double newval = ((double)value / (double)size * 100) / 100;
                bands[i][0] = newval;
            }
            for (int i = 0; i < MAX_LEVEL; i++) {
                int value = this.accHistogram.getYValue("Green", i);
                double newval = ((double)value / (double)size * 100) / 100;
                bands[i][1] = newval;
            }
            for (int i = 0; i < MAX_LEVEL; i++) {
                int value = this.accHistogram.getYValue("Blue", i);
                double newval = ((double)value / (double)size * 100) / 100;
                bands[i][2] = newval;
            }
        }
        return bands;
    }
    
    public void convertToGrayScale () {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                final int grayScale = (int) (0.299 * getRed(i, j) + 0.587 * getGreen(i, j) + 0.114 * getBlue(i, j));
                this.image.setRGB(i, j, new Color(grayScale, grayScale, grayScale).getRGB());
            }
        }
        setDynamicData();
    }
    
    public void applyGammaCorrection (float gamma) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                int newval = (int) Math.round(Math.pow((float)val / (float)255, gamma) * (float)255);
                this.image.setRGB(i, j, new Color(newval, newval, newval).getRGB());
            }
        }
        setDynamicData();
    }
    
    public void equalizeHistogram () {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int actualGray = getRed(i, j);
                int rightBranch = Math.round(this.accHistogram.getYValue("Gray", actualGray) * 256 / (getWidth() * getHeight())) - 1;
                int newGray = Math.max(0, rightBranch);
                this.image.setRGB(i, j, new Color(newGray, newGray, newGray).getRGB());
            }
        }
        setDynamicData();
    }
    
    public void specifyHistogramFromImage (ImageProcessing image) {
        
        // Normalizar ambos histogramas acumulados
        double[][] actualAccNormalizedHistogram = getNormalizedAccumulateHistogram();
        double[][] refAccNormalizedHistogram = image.getNormalizedAccumulateHistogram();
        
        if (isGrayScale()) {
            alterAccHistogramBand("Gray", actualAccNormalizedHistogram, refAccNormalizedHistogram);
        } else {
            alterAccHistogramBand("Red", actualAccNormalizedHistogram, refAccNormalizedHistogram);
            alterAccHistogramBand("Green", actualAccNormalizedHistogram, refAccNormalizedHistogram);
            alterAccHistogramBand("Blue", actualAccNormalizedHistogram, refAccNormalizedHistogram);
        }
        
        // Recalculamos valores dinamicos
        setDynamicData();
    }
    
    public ImageProcessing applyPiecewiseLinearFit (Point[] coordinates) {
        int[] lut = new int[MAX_LEVEL];
        
        for (int i = 1; i < coordinates.length; i += 2) {
            Point p1 = coordinates[i - 1];
            Point p2 = coordinates[i];
            for (int x = p1.x; x <= p2.x; x++) {
                float div = (float) (p2.y - p1.y) / (float) (p2.x - p1.x);
                int y = (int) Math.round((float) p1.y + div * (float) (x - p1.x));
                lut[x] = y;
            }
        }
        
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = lut[getRed(i, j)];
                image.image.setRGB(i, j, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing getHorizontalFlippedImage () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(getWidth() - 1 - i, j, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing getVerticalFlippedImage () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(i, getHeight() - 1 - j, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing getTrasposeImage () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(j, i, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing rotate90 () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(getHeight() - 1 - j, i, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing rotate180 () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(getHeight() - 1 - i, getWidth() - 1 - j, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing rotate270 () {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                image.image.setRGB(j, getWidth() - 1 - i, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        
        return image;
    }
    
    public ImageProcessing substractImage (ImageProcessing image) {
        ImageProcessing newimage = new ImageProcessing(this);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                int newval = Math.abs(val - image.getRed(i, j));
                newimage.image.setRGB(i, j, new Color(newval, newval, newval).getRGB());
            }
        }
        newimage.setDynamicData();
        return newimage;
    }
    
    public ImageProcessing getMapUsingUmbral (int umbral) {
        ImageProcessing image = new ImageProcessing(this);
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val = getRed(i, j);
                if (val > umbral) {
                    image.image.setRGB(i, j, new Color(255, 0, 0).getRGB());
                }
            }
        }
        image.setDynamicData();
        return image;
    }
    
    public ImageProcessing scaleImage (float widthScale, float heightScale, boolean bilineal) {
        ImageProcessing image = new ImageProcessing(this);
        
        image.image = new BufferedImage((int) Math.floor((double) getWidth() * (double) widthScale), (int) Math.floor((double) getHeight() * (double) heightScale), BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                float x = (float) i / widthScale;
                float y = (float) j / heightScale;
                int val = bilineal ? applyBilinealTo(x, y) : applyVMPTo(x, y);
                image.image.setRGB(i, j, new Color(val, val, val).getRGB());
            }
        }
        image.setDynamicData();
        return image;
    }
    
    public ImageProcessing rotateImage (float angle, boolean bilineal) {
        ImageProcessing image = new ImageProcessing(this);
        
        // Get corner coordinates
        Point A = getRotatedPoint(0, 0, angle);
        Point B = getRotatedPoint(0, getHeight() - 1, angle);
        Point C = getRotatedPoint(getWidth() - 1, 0, angle);
        Point D = getRotatedPoint(getWidth() - 1, getHeight() - 1, angle);
        
        // Obtener el ancho y el alto de la imagen nueva
        int minx, maxx;
        
        minx = A.x;
        minx = Math.min(minx, B.x);
        minx = Math.min(minx, C.x);
        minx = Math.min(minx, D.x);
        
        maxx = A.x;
        maxx = Math.max(maxx, B.x);
        maxx = Math.max(maxx, C.x);
        maxx = Math.max(maxx, D.x);
        
        int miny, maxy;
        
        miny = A.y;
        miny = Math.min(miny, B.y);
        miny = Math.min(miny, C.y);
        miny = Math.min(miny, D.y);
        
        maxy = A.y;
        maxy = Math.max(maxy, B.y);
        maxy = Math.max(maxy, C.y);
        maxy = Math.max(maxy, D.y);
        
        int width = Math.abs(maxx - minx);
        int height = Math.abs(maxy - miny);
        
        image.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // Aplicamos color a la imagen
        image.outOfImageCount = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Point coordinates = new Point (i + minx, j + miny);
                float x = (float) coordinates.x * (float) Math.cos(angle) + (float) coordinates.y * (float) Math.sin(angle);
                float y = (float) (-1) * (float) coordinates.x * (float) Math.sin(angle) + (float) coordinates.y * (float) Math.cos(angle);
                int val = 0;
                if (exists(Math.round(x), Math.round(y))) {
                    val = bilineal ? applyBilinealTo(x, y) : applyVMPTo(x, y);
                } else {
                    image.outOfImageCount++;
                }
                image.image.setRGB(i, j, new Color(val, val, val).getRGB());
            }
        }
        image.isRotated = true;
        image.setDynamicData();
        return image;
    }
    
    public String getAbsolutePath () {
        return this.file.getAbsolutePath();
    }
    
    public String getExtension () {
        return getFileExtension(this.file);
    }
    
    public void drawImage (Graphics2D g2d, int originX, int originY, int width, int height) {
        g2d.drawImage(this.image, originX, originY, width, height, null);
    }
    
    public void saveIntoFile () {
        this.saveIntoFileAs(this.file);
    }
    
    public void changeBrightTo (int newBright) {
        float A = 1;
        float B = newBright - getBright();
        applyLinearTransformation(A, B);
    }
    
    public void changeContrastTo (int newContrast) {
        float A = (float) newContrast / (float) getContrast();
        float B = 0;
        applyLinearTransformation(A, B);
    }
    
    public void applyLinearTransformation (float A, float B) {
        System.out.println("Valor de A y B utilizados: " + A + ", " + B);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int grayColor = getRed(i, j);
                int newColor = Math.round(A * (float) grayColor + B);
                if (newColor < 0) {
                    newColor = 0;
                } else if (newColor > 255) {
                    newColor = 255;
                }
                this.image.setRGB(i, j, new Color(newColor, newColor, newColor).getRGB());
            }
        }
        setDynamicData();
    }
    
    public void saveIntoFileAs (File file) {
        try {
            boolean b = ImageIO.write(this.image, this.getExtension(), file);
            System.out.println("Valor del booleano: " + Boolean.toString(b));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getImageFromRange (Point p1, Point p2) {
        int width = p2.x - p1.x;
        int height = p2.y - p1.y;
        
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i  = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bi.setRGB(i, j, pixels[p1.x + i][p1.y + j]);
            }
        }
        return bi;
    }
    
    private Map<String, Integer> processRGBData(int posX, int posY) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        final int pixel = pixels[posX][posY];
        map.put("alpha", ((pixel >> 24) & 0xFF));
        map.put("red", ((pixel >> 16) & 0xFF));
        map.put("green", ((pixel >> 8) & 0xFF));
        map.put("blue", (pixel & 0xFF));
        return map;
    }

    private void initializeImageData(File file) {
        try {
            BufferedImage aux = ImageIO.read(file);
            this.image = new BufferedImage(aux.getWidth(), aux.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < aux.getWidth(); i++) {
                for (int j = 0; j < aux.getHeight(); j++) {
                    this.image.setRGB(i, j, aux.getRGB(i, j));
                }
            }
            setDynamicData();
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.file = file;
    }
    
    private void setDynamicData () {
        
        this.pixels = new int[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                pixels[i][j] = this.image.getRGB(i, j);
            }
        }
        
        this.isGrayScale = true;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int valr = getRed(i, j);
                int valg = getGreen(i, j);
                int valb = getBlue(i, j);
                if (!(valr == valg && valr == valb)) {
                    this.isGrayScale = false;
                    break;
                }
            }
        }
        
        this.redLevels = new int[MAX_LEVEL];
        this.greenLevels = new int[MAX_LEVEL];
        this.blueLevels = new int[MAX_LEVEL];
        
        this.grayLevels = new int[MAX_LEVEL];
        
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {                
                if (!isGrayScale()) {
                    redLevels[getRed(i, j)]++;
                    greenLevels[getGreen(i, j)]++;
                    blueLevels[getBlue(i, j)]++;
                } else {
                    grayLevels[getRed(i, j)]++;
                }
            }
        }
        
        this.minGrayValue = calcMinGrayValue();
        this.maxGrayValue = calcMaxGrayValue();
        this.entropy = calcEntropy();
        
        System.out.println("Se calculan los histogramas!");
        setHistogram();
        setAccumulateHistogram();
        
        this.contrast = 0;
        this.bright = 0;
        if (isGrayScale()) {
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    this.bright += getRed(i, j);
                }
            }
            this.bright /= (getWidth() * getHeight());
        
            double contrast = 0;
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    double aux = (getRed(i,j) - this.bright);
                    contrast += aux * aux;
                }
            }
            contrast /= (double)(getWidth() * getHeight());
            this.contrast = (int) Math.round(Math.sqrt(contrast));
        }
    }
    
    private void setHistogram () {
        this.histogram = new Histogram();
        if (isGrayScale()) {
            this.histogram.addSerie("Gray");
            if (this.isRotated) {
                this.histogram.addToSerie("Gray", 0, grayLevels[0] - outOfImageCount);
            } else {
                this.histogram.addToSerie("Gray", 0, grayLevels[0]);
            }
            for (int i = 1; i < grayLevels.length; i++) {
                this.histogram.addToSerie("Gray", i, grayLevels[i]);
            }
            this.histogram.generateHistogram("Gray histogram", "Levels", "Pixels");
        } else {
            this.histogram.addSerie("Red");
            this.histogram.addSerie("Blue");
            this.histogram.addSerie("Green");
            for (int i = 0; i < redLevels.length; i++) {
                this.histogram.addToSerie("Red", i, redLevels[i]);
                this.histogram.addToSerie("Green", i, greenLevels[i]);
                this.histogram.addToSerie("Blue", i, blueLevels[i]);
            }
            this.histogram.generateHistogram("Color histogram", "Levels", "Pixels");
        }
    }
    
    private void setAccumulateHistogram () {
        this.accHistogram = new Histogram();
        if (isGrayScale()) {
            this.accHistogram.addSerie("Gray");
            this.histogram.addToSerie("Gray", 0, isRotated ? grayLevels[0] - outOfImageCount : grayLevels[0]);
            int acc = isRotated ? grayLevels[0] - outOfImageCount : grayLevels[0];
            for (int i = 1; i < grayLevels.length; i++) {
                this.accHistogram.addToSerie("Gray", i, acc + grayLevels[i]);
                acc += grayLevels[i];
            }
            this.accHistogram.generateHistogram("Gray histogram", "Levels", "Pixels");
        } else {
            this.accHistogram.addSerie("Red");
            this.accHistogram.addSerie("Blue");
            this.accHistogram.addSerie("Green");
            int accRed = 0;
            int accGreen = 0;
            int accBlue = 0;
            for (int i = 0; i < redLevels.length; i++) {
                this.accHistogram.addToSerie("Red", i, accRed + redLevels[i]);
                this.accHistogram.addToSerie("Green", i, accGreen + greenLevels[i]);
                this.accHistogram.addToSerie("Blue", i, accBlue + blueLevels[i]);
                accRed += redLevels[i];
                accGreen += greenLevels[i];
                accBlue += blueLevels[i];
            }
            this.accHistogram.generateHistogram("Color histogram", "Levels", "Pixels");
        }
    }
    
    private void alterAccHistogramBand (String band, double[][] actualAccNormalizedHistogram, double[][] refAccNormalizedHistogram) {
        // Crear tabla LUT
        int[] lut = new int[MAX_LEVEL];
        
        int bandNumber;
        if ("Red".equals(band) || "Gray".equals(band)) {
            bandNumber = 0;
        } else if ("Green".equals(band)) {
            bandNumber = 1;
        } else {
            bandNumber = 2;
        }
        
        for (int i = 0; i < MAX_LEVEL; i++) {
            for (int j = 0; j < MAX_LEVEL; j++) {
                if ((actualAccNormalizedHistogram[i][bandNumber] - refAccNormalizedHistogram[j][bandNumber]) < 1E-3) {
                    lut[i] = j;
                    break;
                }
            }
        }
        
        // Aplicarla a la imagen para obtener la imagen resultado
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int val;
                if ("Red".equals(band) || "Gray".equals(band)) {
                    val = getRed(i, j);
                } else if ("Green".equals(band)) {
                    val = getGreen(i, j);
                } else {
                    val = getBlue(i, j);
                }
                int newval = lut[val];
                this.image.setRGB(i, j, new Color(newval, newval, newval).getRGB());
                if ("Red".equals(band)) {
                    this.image.setRGB(i, j, new Color(newval, getGreen(i, j), getBlue(i,j)).getRGB());
                } else if ("Green".equals(band)) {
                    this.image.setRGB(i, j, new Color(getRed(i, j), newval, getBlue(i,j)).getRGB());
                } else if ("Gray".equals(band)) {
                    this.image.setRGB(i, j, new Color(newval, newval, newval).getRGB());
                } else {
                    this.image.setRGB(i, j, new Color(getRed(i, j), getGreen(i, j), newval).getRGB());
                }
            }
        }
    }
    
    private boolean exists (int x, int y) {
        return (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());
    }
    
    private Point getRotatedPoint (int x, int y, float angle) {
        int x2 = Math.round((float) x * (float) Math.cos(angle) - (float) y * (float) Math.sin(angle));
        int y2 = Math.round((float) x * (float) Math.sin(angle) + (float) y * (float) Math.cos(angle));
        return new Point(x2, y2);
    }
    
    private int applyVMPTo (float i, float j) {
        Point p = new Point(Math.round(i), Math.round(j));
        
        if (p.x >= this.getWidth()) {
            p.x--;
        }
        
        if (p.y >= this.getHeight()) {
            p.y--;
        }
        
        return this.getRed(p.x, p.y);
    }
    
    private int applyBilinealTo (float i, float j) {
        int X = (int) Math.floor((double) i);
        int Y = (int) Math.floor((double) j);
        
        int A = exists(X, Y + 1) ? getRed(X, Y + 1) : applyVMPTo(i, j);
        int B = exists(X + 1, Y + 1) ? getRed(X + 1, Y + 1) : applyVMPTo(i, j);
        int C = exists(X, Y) ? getRed(X, Y) : applyVMPTo(i, j);
        int D = exists(X + 1, Y) ? getRed(X + 1, Y) : applyVMPTo(i, j);
        
        float p = i - (float) X;
        float q = j - (float) Y;
        
        return (int) (((float) C) + ((float) (D - C)) * p + ((float) (A - C)) * q + ((float) (B + C - A - D)) * p * q);
    }
    
    private int calcMinGrayValue () {
        int min = getRed(0, 0);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 1; j < getHeight(); j++) {
                int val = getRed(i, j);
                if (min > val) {
                    min = val;
                }
            }
        }
        return min;
    }
    
    private int calcMaxGrayValue () {
        int max = getRed(0, 0);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 1; j < getHeight(); j++) {
                int val = getRed(i, j);
                if (max < val) {
                    max = val;
                }
            }
        }
        return max;
    }
    
    private double calcEntropy () {
        double acc = 0;
        int size = getWidth() * getHeight();
        for (int i = 0; i < this.grayLevels.length; i++) {
            if (this.grayLevels[i] == 0) continue;            
            double p = (double)this.grayLevels[i] / (double)size;            
            acc += p * (Math.log(p) / Math.log(2));
        }
        return (-1) * acc;
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
       }
}
