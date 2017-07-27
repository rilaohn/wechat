package com.wechat.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CodeUtil {

	/**
	 * 生成二维码图片
	 * @param content	内容
	 * @param format	格式
	 * @param filePath	路径
	 * @param width		宽度
	 * @param height	高度
	 * @param margin	边距
	 * @return	是否成功
	 */
	public static boolean getQRCode(String content, String format, String filePath, int width, int height, Integer margin) {
		Boolean flag = false;
		flag = encode(content, format, filePath, width, height, margin, BarcodeFormat.QR_CODE, ErrorCorrectionLevel.H, "UTF-8");
		return flag;
	}

	/**
	 * 生成二维码图片（正方形）
	 * @param content		内容
	 * @param format		格式
	 * @param filePath		路径
	 * @param widthHeight	宽高
	 * @param margin		边距
	 * @return	是否成功
	 */
	public static boolean getQRCode(String content, String format, String filePath, int widthHeight, Integer margin) {
		Boolean flag = false;
		flag = encode(content, format, filePath, widthHeight, widthHeight, margin, BarcodeFormat.QR_CODE, ErrorCorrectionLevel.H, "UTF-8");
		return flag;
	}

	/**
	 * 生成二维码图片（正方形，边距0）
	 * @param content		二维码图片内容
	 * @param format		格式
	 * @param filePath		路径
	 * @param widthHeight	宽高
	 * @return	是否成功
	 */
	public static boolean getQRCode(String content, String format, String filePath, int widthHeight) {
		Boolean flag = false;
		flag = encode(content, format, filePath, widthHeight, widthHeight, 0, BarcodeFormat.QR_CODE, ErrorCorrectionLevel.H, "UTF-8");
		return flag;
	}

	/**
	 * 生成二维码图片（正方形，边距0，通过filePath自动配置格式）
	 * @param content		内容
	 * @param filePath		路径
	 * @param widthHeight	宽高
	 * @return	是否成功
	 */
	public static boolean getQRCode(String content, String filePath, int widthHeight) {
		Boolean flag = false;
		String format = filePath.split("\\.")[1];
		flag = encode(content, format, filePath, widthHeight, widthHeight, 0, BarcodeFormat.QR_CODE, ErrorCorrectionLevel.H, "UTF-8");
		return flag;
	}

	/**
	 * 生成二维码图片（正方形，边距0，通过路劲配置格式，默认宽高256）
	 * @param content	内容
	 * @param filePath	路径
	 * @return	是否成功
	 */
	public static boolean getQRCode(String content, String filePath) {
		Boolean flag = false;
		String format = filePath.split("\\.")[1];
		flag = encode(content, format, filePath, 256, 256, 0, BarcodeFormat.QR_CODE, ErrorCorrectionLevel.H, "UTF-8");
		return flag;
	}

	/**
	 * 编码
	 * @param content 内容
	 * @param format 格式
	 * @param filePath	路径
	 * @param width	 宽
	 * @param height 高
	 * @param margin 边距
	 * @param barcodeFormat	 编码格式
	 * @param errorLevel 纠错等级
	 * @param charset 字符集
	 * @return 是否成功
	 */
	public static boolean encode(String content, String format, String filePath, int width, int height, Integer margin, BarcodeFormat barcodeFormat,
									ErrorCorrectionLevel errorLevel, String charset) {
		Boolean flag = false;
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
		hints.put(EncodeHintType.MARGIN, margin);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, barcodeFormat, width, height, hints);
			MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
			flag = writeToFile(bufferedImage, format, filePath);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 解码
	 * @param filePath 路径
	 * @return 结果
	 */
	public static Result decode(String filePath){
		return decode(filePath, "UTF-8");
	}

	/**
	 * 解码
	 * @param filePath 路径
	 * @param charset 字符集
	 * @return 结果
	 */
	public static Result decode(String filePath, String charset){
		Result result = null;
		BufferedImage image;
		try {
			File file = new File(filePath);
			image = ImageIO.read(file);
			if (null != image) {
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
				hints.put(DecodeHintType.CHARACTER_SET, charset);
				result = new MultiFormatReader().decode(bitmap, hints);
			} else {
				System.err.println("Could not decode image.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	public static Boolean writeToFile(BufferedImage bufImg, String format, String saveImgFilePath) {
		Boolean bool = false;
		try {
			bool = ImageIO.write(bufImg, format, new File(saveImgFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return bool;
		}
	}
}
