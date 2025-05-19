/**
 * 
 */
package com.proquifa.net.modelo.comun.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * @author fmartinez
 *
 */
public class FlexiablePdfImageWriter {
	
	 private PDDocument document;
	 // default setting for image params
	 private int resolution = 96;
	 private int imageType = BufferedImage.TYPE_INT_RGB;
	 private String imageFormat = "jpg";
	 
	 public FlexiablePdfImageWriter(PDDocument document) {
	  this.document = document;
	 }
	 
	/**
	 * Generate image for given page number, begin at 1
	 * @param pageNo page number
	 * @return image
	 * @throws IOException
	 */
	 public BufferedImage getOriginalImageForSinglePage(int pageNo)
			 throws IOException {
		 
		 return null;
	 }
	 
	 
	 /**
	  * resize image with width, height
	  * @param orginalImage
	  * @param width
	  * @param height
	  * @return resiced image
	  */
	 public BufferedImage resizeImage(BufferedImage orginalImage, int width, int height)
	   {
	 
	  BufferedImage resizedImage = new BufferedImage(width, height, imageType);
	  resizedImage.getGraphics().drawImage(orginalImage, 0, 0, width, height, null);
	 
	  return resizedImage;
	 
	 }
	 /**
	  * resize image by percentage
	  * @param orginalImage
	  * @param percentage
	  * @return scaled image
	  */
	 public BufferedImage scaleImage(BufferedImage orginalImage, int percentage)
	{
	  int width= orginalImage.getWidth()*percentage/100;
	  int height= orginalImage.getHeight()*percentage/100;
	 
	  return this.resizeImage(orginalImage, width, height);
	 
	}
	 /**
	  * write image to file
	  * @param image
	  * @param path
	  * @return
	  * @throws IOException
	  */
	 public boolean writeImagte(BufferedImage image, String path)
	   throws IOException {
	  File outfile = new File(path);
	  ImageIO.write(image, imageFormat, outfile);
	  return true;
	 }
	  
	 
	 public int getResolution() {
	  return resolution;
	 }
	 
	 public void setResolution(int resolution) {
	  this.resolution = resolution;
	 }
	 
	 public int getImageType() {
	  return imageType;
	 }
	 
	 public void setImageType(int imageType) {
	  this.imageType = imageType;
	 }
	 
	 public String getImageFormat() {
	  return imageFormat;
	 }
	 
	 public void setImageFormat(String imageFormat) {
	  this.imageFormat = imageFormat;
	 }
}