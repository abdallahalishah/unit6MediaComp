import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////
    ////////////////////// methods ///////////////////////////////////////
    ////////////////////// methods ///////////////////////////////////////
    ////////////////////// methods ///////////////////////////////////////
    ////////////////////// methods ///////////////////////////////////////
    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void highBlue(int r, int c, int w, int h)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < r + h; row++)
        {
            for (int col = c; col < c + w; col++)
            {
                pixels[row][col].setBlue(pixels[row][col].getBlue()+100);
            }
        }
    }
    
    public void highGreen(int r, int c, int w, int h)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < r + h; row++)
        {
            for (int col = c; col < c + w; col++)
            {
                pixels[row][col].setGreen(pixels[row][col].getGreen()+100);
            }
        }
    }
    
    public void highRed(int r, int c, int w, int h)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < r + h; row++)
        {
            for (int col = c; col < c + w; col++)
            {
                pixels[row][col].setRed(pixels[row][col].getRed()+100);
            }
        }
    }

    public void negate(int r, int c, int endr, int endc)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < endr; row++)
        {
            for (int col = c; col < endc ; col++)
            {
                pixels[row][col].setRed(255-pixels[row][col].getRed());
                pixels[row][col].setBlue(255-pixels[row][col].getBlue());
                pixels[row][col].setGreen(255-pixels[row][col].getGreen());
            }
        }
        //         for (int row = r; row < endr/2; row++)
        //         {
        //             for (int col = c; col < endc ; col++)
        //             {
        //                 pixels[row][col].setRed(255-pixels[row][col].getRed());
        //                 pixels[row][col].setBlue(255-pixels[row][col].getBlue());
        //                 pixels[row][col].setGreen(255-pixels[row][col].getGreen());
        //             }
        //         }
    }

    public void dark(int r, int c, int w, int h)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < r + h; row++)
        {
            for (int col = c; col < c + w; col++)
            {
                int red = pixels[row][col].getRed()-50;
                int blue = pixels[row][col].getBlue()-50;
                int green = pixels[row][col].getGreen()-50;
                int ave = (red + blue + green)/3;
                pixels[row][col].setRed(red);
                pixels[row][col].setBlue(blue);
                pixels[row][col].setGreen(green);
            }
        }
    }
    
    public void gray(int r, int c, int w, int h)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = r; row < r + h; row++)
        {
            for (int col = c; col < c + w; col++)
            {
                int red = pixels[row][col].getRed()-100;
                int blue = pixels[row][col].getBlue()-100;
                int green = pixels[row][col].getGreen()-100;
                int ave = (red + blue + green)/3;
                pixels[row][col].setRed(ave);
                pixels[row][col].setBlue(ave);
                pixels[row][col].setGreen(ave);
            }
        }
    }

    public void collage(int scale)
    {
        Pixel[][] pixels = this.getPixels2D();
        int height = pixels[0].length;
        int width = pixels.length;
        Color[] newPixels = new Color[(height*width)/scale];
        int[] newR = new int[(height*width)/scale];
        int[] newG = new int[(height*width)/scale];
        int[] newB = new int[(height*width)/scale];
        int counter = 0;
        for (int row = 0; row < pixels.length; row+=scale)
        {
            for (int col = 0; col < pixels[0].length; col+=scale)
            {
                Pixel pix = pixels[row][col];
                newPixels[counter] = pix.getColor();
                newR[counter] = pix.getRed();
                newG[counter] = pix.getGreen();
                newB[counter] = pix.getBlue();
                counter++;
            }
        }
        mirrorDiagonalTRBL();
        mirrorVertical();
        mirrorHorizontal();
        counter = 0;
        for (int row = 0; row < pixels.length/scale; row++)
        {
            for (int col = 0; col < pixels[0].length/scale; col++)
            {
                Color color = newPixels[counter];
                pixels[row][col].setColor(color);
                counter++;
            }
        }
        negate(0,0,220,388);
        counter = 0;
        for (int row = 0; row < pixels.length/scale; row++)
        {
            for (int col = 0; col < pixels[0].length/scale; col++)
            {
                Color color = newPixels[counter];
                pixels[row][col+388*3].setColor(color);
                counter++;
            }
        }
        //dark(0,388*3,388,220);
        highBlue(0,388*3,388,220);
        counter = 0;
        for (int row = 0; row < pixels.length/scale; row++)
        {
            for (int col = 0; col < pixels[0].length/scale; col++)
            {
                Color color = newPixels[counter];
                pixels[row+220*3][col].setColor(color);
                counter++;
            }
        }
        highGreen(220*3-1,0,388,220);
        counter = 0;
        for (int row = 0; row < pixels.length/scale; row++)
        {
            for (int col = 0; col < pixels[0].length/scale; col++)
            {
                Color color = newPixels[counter];
                pixels[row+220*3][col+388*3].setColor(color);
                counter++;
            }
        }
        highRed(220*3-1,388*3,388,220);
        
        negate(220,0,220*3,388*4);
        negate(0,388,220*4-1,388*3);
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        } 
    }

    public void flipped()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel pixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        Color[] flipped = new Color[pixels.length * width];
        Color[] current = new Color[pixels.length * width];
        int counter = 0;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = width - 1; col >= 0; col--)
            {
                pixel = pixels[row][col];
                flipped[counter] = pixel.getColor();
                counter += 1;
            }
        }
        counter = 0;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                pixel = pixels[row][col];
                current[counter] = pixel.getColor();
                counter += 1;
            }
        }
        counter = 0;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Color newPix = flipped[counter];
                pixels[row][col].setColor(newPix);
                counter += 1;
            }
        }

        for (int i = 0; i < flipped.length; i += 2)
        {
            flipped[i] = null;
        }

        for (int i = 1; i < flipped.length; i += 2)
        {
            current[i] = null;
        }
        counter = 0;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                if (flipped[counter] != null)
                {
                    pixels[row][col].setColor(flipped[counter]);
                    //pixels[row][col].setRed(255);
                }
                else 
                {
                    pixels[row][col].setColor(current[counter]);
                    //pixels[row][col].setBlue(255);
                }
                counter += 1;
            }
        }

    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length/2; row++)
        {
            for (int col = 0; col < width ; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[pixels.length - 1 - row][col];
                topPixel.setColor(bottomPixel.getColor());
            }
        } 
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorDiagonalTRBL() // top right to bottom left
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int width = pixels[0].length;
        double totalCol = 1.0;
        int height = pixels.length;
        //int diag = (int)(Math.sqrt(height*height + width*width));
        double increment = width/(height*1.0);
        System.out.println(height + "    " + width + "   " + increment);
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width ; col++)
            {
                if (col < totalCol)
                {
                    topPixel = pixels[row][col];
                    bottomPixel = pixels[pixels.length - 1 - row][width - 1 - col];
                    topPixel.setColor(bottomPixel.getColor());
                }
            }
            totalCol += increment;
        } 
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorDiagonalTLBR() // top left to bottom right
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topLeftPixel = null;
        Pixel bottomRightPixel = null;
        int width = pixels[0].length;
        int height = pixels.length;
        //int diag = (int)(Math.sqrt(height*height + width*width));
        double increment = width/(height*1.0);
        double totalCol = width;
        System.out.println(height + "    " + width + "   " + increment);
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width ; col++)
            {
                if (col < totalCol)
                {
                    topLeftPixel = pixels[row][col];
                    bottomRightPixel = pixels[pixels.length - 1 - row][width - 1 - col];
                    topLeftPixel.setColor(bottomRightPixel.getColor());
                }
            }
            totalCol -= increment;
        } 
    }

    /**
     * Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        //flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                {
                    Random gen = new Random();
                    int num = gen.nextInt(256);
                    int num1= gen.nextInt(256);
                    int num2 = gen.nextInt(256);

                    Color color = new Color(num, num1,num2);
                    leftPixel.setColor(color);
                }
                else
                {
                    leftPixel.setColor(Color.black);
                }
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture car = new Picture("koenigsegg.jpg");
        //car.zeroBlue();
        //car.mirrorVertical();
        //car.mirrorHorizontal();
        //car.mirrorDiagonalTRBL();
        //car.mirrorHorizontal(); 
        //car.mirrorDiagonalTLBR();
        //car.mirrorVertical();
        //car.mirrorHorizontal();
        //car.negate();
        //car.gray();
        //car.intensify();
        //car.flipped();
        //car.createCollage();
        car.collage(4);
        //car.edgeDetection(18);
        car.explore();
    }

} // this } is the end of class Picture, put all new methods before this
