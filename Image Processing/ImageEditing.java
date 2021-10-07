/* 
Assignment number         :         6
File Name                 :         ImageEditing.java 
Name (First Last)         :         Maor Shriki
Student ID                :         208274118
Email                     :         maor.shriki@post.idc.ac.il
*/
public class ImageEditing {

	/**
	 * Reads a .PPM file and converts it into an image matrix.
	 *
	 * @param filename - Name of a PPM file.
	 * @return an array containing the image data.
	 */
	public static int[][][] read(String filename) {
		StdIn.setInput(filename);
		StdIn.readString();
		int colm = StdIn.readInt();
		int rows = StdIn.readInt();
		StdIn.readInt();
		int[][][] picArr = new int[rows][colm][3];
		for (int i = 0; i < picArr.length; i++) {
			for (int j = 0; j < picArr[i].length; j++) {
				picArr[i][j][0] = StdIn.readInt();
				picArr[i][j][1] = StdIn.readInt();
				picArr[i][j][2] = StdIn.readInt();
			}
		}
		return picArr;
	}

	/**
	 * Prints the RGB values for every pixel in the image.
	 *
	 * @param source - An image matrix.
	 */
	private static void print(int[][][] source) {
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[i].length; j++) {
				for (int t = 0; t < source[i][j].length; t++) {
					System.out.printf("%4s", source[i][j][t], " ");
				}
				System.out.print("  ");
			}
			System.out.println();
		}
	}

	/**
	 * flips a picture, horizontally.
	 *
	 * @param source - An image matrix.
	 * @return A flipped image matrix, horizontally.
	 */
	public static int[][][] flipHorizontally(int[][][] source) {
		int rows = source.length;
		int colm = source[0].length;
		int[][][] flip = new int[rows][colm][3];
		for (int i = 0; i < flip.length; i++) {
			for (int j = 0; j < flip[i].length; j++) {
				for (int t = 0; t < flip[i][j].length; t++) {
					flip[i][j][t] = source[i][flip[i].length - 1 - j][t];
				}
			}
		}
		return flip;
	}

	/**
	 * flips a picture ,Vertically.
	 *
	 * @param source - An image matrix.
	 * @return A flipped image matrix, Vertically.
	 */
	public static int[][][] flipVertically(int[][][] source) {
		int rows = source.length;
		int colm = source[0].length;
		int[][][] vertical = new int[rows][colm][3];
		for (int i = 0; i < vertical.length; i++) {
			for (int j = 0; j < vertical[i].length; j++) {
				for (int t = 0; t < vertical[i][j].length; t++) {
					vertical[i][j][t] = source[vertical.length - 1 - i][j][t];
				}
			}
		}
		return vertical;
	}

	/**
	 * Computes the average of the pixels colors at the image.
	 *
	 * @param pixel - An image matrix.
	 * @return average of the matrix colors.
	 */
	public static double average(int[][][] pixel) {
		double sum = 0;
		double length = (pixel.length * pixel[0].length * 3);
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				for (int t = 0; t < pixel[i][j].length; t++) {
					sum += pixel[i][j][t];
				}
			}
		}
		double avg = sum / length;
		return avg;
	}

	/**
	 * Binary segements an image.
	 *
	 * @param source - An image matrix.
	 * @return A segmented version of the image.
	 */
	static int[][][] segement(int[][][] source) {
		double t = average(source);
		int sum = 0;
		int rows = source.length;
		int colm = source[0].length;
		int[][][] seg = new int[rows][colm][3];

		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[i].length; j++) {
				sum += source[i][j][0] + source[i][j][1] + source[i][j][2];
				if (sum / 3 > t) {
					seg[i][j][0] = 255;
					seg[i][j][1] = 255;
					seg[i][j][2] = 255;
				} else {
					seg[i][j][0] = 0;
					seg[i][j][1] = 0;
					seg[i][j][2] = 0;
				}
				sum = 0;
			}
		}
		return seg;
	}

	/**
	 * Rescales an image.
	 *
	 * @param source - An image matrix.
	 * @param width  - The width of the target image.
	 * @param height - The height of the target image.
	 * @return A scaled version of the digital image.
	 */
	public static int[][][] scale(int[][][] source, int width, int height) {
		int[][][] scaling = new int[height][width][3];
		for (int i = 0; i < scaling.length; i++) {
			for (int j = 0; j < scaling[i].length; j++) {
				for (int t = 0; t < scaling[i][j].length; t++) {
					scaling[i][j][t] = source[i * source.length / height][j * source[0].length / width][t];
				}
			}
		}
		return scaling;
	}

	/**
	 * Blends the pixels of two images.
	 *
	 * @param pixel1 - pixel from the first image.
	 * @param pixel2 - pixel from the second image.
	 * @param alpha  - number that determines the blending the pixels.
	 * @return A blended pixel.
	 */
	static int[] blend(int[] pixel1, int[] pixel2, double alpha) {
		int[] blend = new int[pixel1.length];
		for (int i = 0; i < blend.length; i++) {
			blend[i] = (int) ((alpha * pixel1[i]) + ((1 - alpha) * pixel2[i]));
		}
		return blend;
	}

	/**
	 * Combines two images.
	 *
	 * @param source1 - The first image matrix.
	 * @param source2 - The second image matrix.
	 * @param alpha   - number that determines the blending the pixels.
	 * @return A combined image.
	 */
	public static int[][][] combine(int[][][] source1, int[][][] source2, double alpha) {
		int[][][] combinit = new int[source1.length][source1[0].length][3];
		for (int i = 0; i < source1.length; i++) {
			for (int j = 0; j < source1[i].length; j++) {
				combinit[i][j] = blend(source1[i][j], source2[i][j], alpha);
			}
		}
		return combinit;
	}

	/**
	 * given a source image and a target image and transform the former into the
	 * latter in number of steps.
	 *
	 * @param source - The first image matrix.
	 * @param target - The second image matrix.
	 * @param n      - integer that determines how many steps of morphing.
	 * @return A combined image.
	 */

	public static void morph(int[][][] source, int[][][] target, int n) {
		int sourceWidth = source.length;
		int sourceHeight = source[0].length;
		int targetWidth = target.length;
		int targetHeight = target[0].length;
		if ((sourceWidth != targetWidth) || (sourceHeight != targetHeight)) {
			source = scale(source, targetWidth, targetHeight);
		}
		for (int i = n; i >= 0; i--) {
			double alpha = (1.0 * i) / (1.0 * n);
			show(combine(source, target, alpha));
		}
	}

	/**
	 * Renders an image using StdDraw. The input array is assumed to contain
	 * integers in the range [0,255]. With the third dimension being of size 3.
	 *
	 * @param pic - the image to show.
	 */
	public static void show(int[][][] pic) {
		StdDraw.setCanvasSize(pic[0].length, pic.length);
		int height = pic.length;
		int width = pic[0].length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(30);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				StdDraw.setPenColor(pic[i][j][0], pic[i][j][1], pic[i][j][2]);
				StdDraw.filledRectangle(j + 0.5, height - i - 0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}