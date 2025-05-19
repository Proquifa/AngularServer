package com.proquifa.net.negocio.comun.impl;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.comun.GenerateCodeService;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service("GenerateCodeServiceImpl")
public class GenerateCodeServiceImpl implements GenerateCodeService {

    @Override
    public String getQRCode(String code) throws ProquifaNetException {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(code, com.google.zxing.BarcodeFormat.QR_CODE, 360, 360);

            BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, bitMatrix.getWidth(), bitMatrix.getHeight());
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < bitMatrix.getWidth(); i++) {
                for (int j = 0; j < bitMatrix.getHeight(); j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getBarcode(String code) throws ProquifaNetException {
        try {
            BitMatrix bitMatrix = new Code128Writer().encode(code, BarcodeFormat.CODE_128, 0, 35, null);

            BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, bitMatrix.getWidth(), bitMatrix.getHeight());
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < bitMatrix.getWidth(); i++) {
                for (int j = 0; j < bitMatrix.getHeight(); j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
