
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JColorChooser;

/**
 *
 * @author vento
 */
public class Edge_ {

    Vertex vertexA;
    Vertex vertexB;
    String name; //edit info of variable

    boolean isSelect;

    int x_center;
    int y_center;
    int r_center;
    
    int offSetXInfo;
    int level = -1;
    
    long cost;
    
    int line_mode = 0;//add
    static int idEdge = 0;//add
    int current_idEdge = 0;//add
    
    static Color color = Color.BLUE;
    
    String edgeInfo;//add
    Font f = new Font("SanSerif", Font.PLAIN, 20);//add
    Edge_(Vertex a, Vertex b) {
        idEdge++;
        setCurrentIdEdge();
        this.vertexA = a;
        this.vertexB = b;

        this.r_center = 50;
        
        this.name = vertexA.info+""+vertexB.info; //edit  
        
        this.isSelect = false;
        
        this.edgeInfo = "0";//add
        
        if (a == null) {
            this.name = null;
        }
        
        setXYCenter();
    }
    
    Edge_(Vertex a, Vertex b, String edgeInfo) {
        this(a, b);
        this.edgeInfo = edgeInfo;
    }
    
    Edge_(Vertex a, Vertex b, String edgeInfo, int offsetXInfo) {
        this(a, b, edgeInfo);
        this.offSetXInfo = offsetXInfo;
    }
    
    Edge_(Vertex a, Vertex b, String edgeInfo, int offsetXInfo, int level) {
        this(a, b, edgeInfo, offsetXInfo);
        this.level = level;
    }
    
    Edge_(Vertex a, Vertex b, String edgeInfo, int offsetXInfo, int level, long cost){
        this(a, b, edgeInfo, offsetXInfo, level);
        this.cost = cost;
    }

    boolean inLine(int x0, int y0) {
        return ((x0 - x_center) * (x0 - x_center) + (y0 - y_center) * (y0 - y_center)) <= r_center * r_center ;
    }

    void draw(Graphics2D g) {
        g.setFont(f);//add
        g.setColor(isSelect ? color : Color.BLACK);
        g.setStroke(new BasicStroke(2));
        if(vertexA != vertexB){
            this.name = vertexA.info+""+vertexB.info;
            g.draw(new QuadCurve2D.Float(vertexA.x, vertexA.y, x_center, y_center, vertexB.x, vertexB.y));
            
        }else{
            this.name = vertexA.info+""+vertexB.info;
            double angle = Math.atan2(y_center - vertexA.y, x_center - vertexA.x);
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
           
            int rc = (int)(vertexA.r*Math.sqrt(2));
            int xloop = vertexA.x - vertexA.r + (int)(dx*rc);
            int yloop = vertexA.y - vertexA.r + (int)(dy*rc);
            
            g.drawArc(xloop, yloop , vertexA.r*2, vertexA.r*2, 0, 360);            
        }
        //g.drawString(info, x_center, y_center);
        g.drawString(edgeInfo, x_center + offSetXInfo , y_center);
    }

    void initializeIdEdge(){//add 
        idEdge = 0;
    }
    
    void setCurrentIdEdge(){
        this.current_idEdge = idEdge;
    }
    
    void setIdEdge(){
        idEdge = current_idEdge;
    }
    
    void setLineMode(int linemode){
        this.line_mode=  linemode;
    }
    int getLIneMode(){
        return line_mode;
    }
    
    void setXYCenter(){
        x_center = (vertexA.x + vertexB.x)/2;
        y_center = (vertexA.y + vertexB.y)/2;
    }
    
    void setSelectEdgeAndVertexAB(boolean isSelect){
        this.isSelect = isSelect;
        vertexA.isSelect = isSelect;
        vertexB.isSelect = isSelect;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    static void color(){
        JColorChooser colorChooser = new JColorChooser();
        Color c = colorChooser.showDialog(null, "Choose Color", colorChooser.getColor());
        if(c != null){
            color = c;
        }
    }
    
    static void setDefaultColor(){
        color = Color.BLUE;
    }
}


