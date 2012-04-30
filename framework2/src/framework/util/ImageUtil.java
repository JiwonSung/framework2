/* 
 * @(#)ImageUtil.java
 */
package framework.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * �̹��� ���� ����, ũ�� ����� �̿��� �� �ִ� ��ƿ��Ƽ Ŭ�����̴�.
 */
public class ImageUtil {

	/**
	 * ������, �ܺο��� ��ü�� �ν��Ͻ�ȭ �� �� ������ ����
	 */
	private ImageUtil() {
	}

	/**
	 * �̹����� �������� �Ѵ�. 
	 * �ҽ� �̹��� ������ width, height �� ũ�Ⱑ ū ���� �������� �Ͽ� ������ ������ä �̹����� �����Ѵ�.
	 * 
	 * @param srcPath �ҽ� �̹��� ���
	 * @param destPath ��� �̹��� ���
	 * @param width ���������� ���� ������
	 * @param height ���������� ���� ������
	 * 
	 * @throws IOException
	 */
	public static void create(String srcPath, String destPath, int width, int height) throws IOException {
		resize(srcPath, destPath, width, height);
	}

	/**
	 * �̹����� �������� �Ѵ�. 
	 * �ҽ� �̹��� ������ width, height �� ũ�Ⱑ ū ���� �������� �Ͽ� ������ ������ä �̹����� �����Ѵ�.
	 * 
	 * @param srcFile �ҽ� �̹��� ����
	 * @param destFile ��� �̹��� ����
	 * @param width ���������� ���� ������
	 * @param height ���������� ���� ������
	 * 
	 * @throws IOException
	 */
	public static void create(File srcFile, File destFile, int width, int height) throws IOException {
		resize(srcFile, destFile, width, height);
	}

	/**
	 * �̹����� �������� �Ѵ�. 
	 * �ҽ� �̹��� ������ width, height �� ũ�Ⱑ ū ���� �������� �Ͽ� ������ ������ä �̹����� �����Ѵ�.
	 * 
	 * @param srcPath �ҽ� �̹��� ���
	 * @param destPath ��� �̹��� ���
	 * @param width ���������� ���� ������
	 * @param height ���������� ���� ������
	 * 
	 * @throws IOException
	 */
	public static void resize(String srcPath, String destPath, int width, int height) throws IOException {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		resize(srcFile, destFile, width, height);
	}

	/**
	 * �̹����� �������� �Ѵ�. 
	 * �ҽ� �̹��� ������ width, height �� ũ�Ⱑ ū ���� �������� �Ͽ� ������ ������ä �̹����� �����Ѵ�.
	 * 
	 * @param srcFile �ҽ� �̹��� ����
	 * @param destFile ��� �̹��� ����
	 * @param width ���������� ���� ������
	 * @param height ���������� ���� ������
	 * 
	 * @throws IOException
	 */
	public static void resize(File srcFile, File destFile, int width, int height) throws IOException {
		Image image = new ImageIcon(srcFile.getAbsolutePath()).getImage();
		double scale = getScale(width, height, image.getWidth(null), image.getHeight(null));
		int scaleWidth = (int) (scale * image.getWidth(null));
		int scaleHeight = (int) (scale * image.getHeight(null));
		BufferedImage bufImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bufImg.createGraphics();
		AffineTransform ax = new AffineTransform();
		ax.setToScale(1, 1);
		g2d.drawImage(image, ax, null);
		Image resizedImg = bufImg.getScaledInstance(scaleWidth, scaleHeight, BufferedImage.SCALE_SMOOTH);
		writePNG(resizedImg, destFile);
	}

	/**
	 * �̹����� PNG �������� �����Ѵ�.
	 * 
	 * @param image ������ �̹��� ��ü
	 * @param destPath ��� �̹��� ���
	 * 
	 * @throws IOException
	 */
	public static void writePNG(Image image, String destPath) throws IOException {
		File destFile = new File(destPath);
		writePNG(image, destFile);
	}

	/**
	 * �̹����� PNG �������� �����Ѵ�.
	 * 
	 * @param image ������ �̹��� ��ü
	 * @param destFile ��� �̹��� ����
	 * 
	 * @throws IOException
	 */
	public static void writePNG(Image image, File destFile) throws IOException {
		BufferedImage bufImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bufImg.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		ImageIO.write(bufImg, "png", destFile);
	}

	//////////////////////////////////////////////////////////////////////////////////////////Private �޼ҵ�

	/**
	 * ���� �̹��� ������� ���������� ������� �̹��� ������ ������ ���Ѵ�. 
	 * ũ�Ⱑ ū ���� �������� ���� ������ �Ѵ�.
	 * 
	 * @param resizeWidth ���������� ���� ������
	 * @param resizeHeight ���������� ���� ������
	 * @param imageWidth ���� �̹����� ���� ������
	 * @param imageHeight ���� �̹����� ���� ������
	 * 
	 * @return ������ ����
	 */
	private static double getScale(int resizeWidth, int resizeHeight, int imageWidth, int imageHeight) {
		double widthScale = (double) resizeWidth / imageWidth;
		double heightScale = (double) resizeHeight / (double) imageHeight;

		if (widthScale > heightScale) {
			return heightScale;
		} else {
			return widthScale;
		}
	}
}
