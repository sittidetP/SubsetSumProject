
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import javax.swing.JColorChooser;
/**
 *
 * @author vento
 */
public class Vertex {

    int x;
    int y;
    String info;
    static int r;
    static Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);
    int shift ;
    boolean isSelect;
    int infoWidth;
    int infoHeight;
    static int paddingWidth = 20;
    static int paddingWidthInside = 10;
    static int paddingHeigth = 10;
    static int paddingHeigthInside = 0;
    
    int id;
    static int idGen = 0;
    
    static Color color = Color.BLUE;
    static Color colorB = Color.WHITE;
    static Color textColor = Color.BLACK;
    
    Vertex(int x, int y) {
        this.id = idGen;
        idGen++;
        this.r = 36;
        this.shift = 30;
        this.x = x;
        this.y = y;
        this.info = "";
        this.isSelect = false;
        //System.out.println("id : " + id + " (" + x + ", " + y + " )");
    }
    
    Vertex(int x, int y, String info) {
        this(x, y);
        this.info = info;
        
    }

    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    void draw(Graphics2D g) {//Graphics2D g
        g.setFont(sanSerifFont);
        infoWidth = g.getFontMetrics(sanSerifFont).stringWidth(info);
        infoHeight = g.getFontMetrics(sanSerifFont).getHeight();
        //System.out.println("Vid : " + id + ", x : " + x + ", y : " + y + ", infoWidth : " + infoWidth + ", infoHeight : " + infoHeight);
        g.setColor(isSelect ? color : Color.BLACK);
        g.setStroke(new BasicStroke(2));

        g.fillRect(x - (infoWidth + paddingWidth)/2, y - (infoHeight + paddingHeigth)/2, infoWidth + paddingWidth, infoHeight + paddingHeigth);
        //g.fillOval(x - r, y - r, r * 2, r * 2);
        //g.fillOval(x, y, (infoWidth - 10) * 2, (infoWidth - 10) * 2);

        g.setColor(colorB);
        g.fillRect(x - (infoWidth + paddingWidthInside)/2, y - (infoHeight + paddingHeigthInside)/2, infoWidth + paddingWidthInside, infoHeight + paddingHeigthInside);
        //g.fillOval(x - infoWidth, y - infoHeight, infoWidth * 2 - (infoWidth - shift), r * 2 - (infoWidth - shift));
        //g.fillOval(x - r + (r - shift) / 2, y - r + (r - shift) / 2, r * 2 - (r - shift), r * 2 - (r - shift));

        g.setColor(textColor);
        g.drawString(info, x - infoWidth/2 , y + infoHeight/4);
        
        
        
    }
    
    static void colorVertex(){
        JColorChooser colorChooser = new JColorChooser();
        Color c = colorChooser.showDialog(null, "Choose Color", colorChooser.getColor());
        if(c != null){
            color = c;
        }
    }
    
    static void colorBackgroundVertex(){
        JColorChooser colorChooser = new JColorChooser();
        Color c = colorChooser.showDialog(null, "Choose Color", colorChooser.getColor());
        if(c != null){
            colorB = c;
        }
    }
    
    static void colorSelectAnswerTextVertex(){
        JColorChooser colorChooser = new JColorChooser();
        Color c = colorChooser.showDialog(null, "Choose Color", colorChooser.getColor());
        if(c != null){
            textColor = c;
        }
    }
    
    static void setColorsDefault(){
        color = Color.BLUE;
        colorB = Color.WHITE;
        textColor = Color.BLACK;
    }
}

