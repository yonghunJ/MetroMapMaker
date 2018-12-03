
package mmm.data;


import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This is a DraggableText class for MetroMapMaker application.
 * 
 * @author Yonghun Jeong
 * @version 1.0
 */
public class DraggableText extends Text implements Draggable{
    double startX;
    double startY;
    String text;
    boolean is_Bold = false;
    boolean is_Italics = false;
    String font_family = "Arial";
    double font_size = 11;
    boolean Is_Horizontal = true;
    private String whose;
    private boolean is_belong;
    /**
     * Constructor for initializing the DraggableText object, note that this constructor
     * will fully setup the DraggableText for use.
     */
    public DraggableText(){
        setX(100.0);
        setY(100.0);
        setOpacity(1.0);
        startX = 0.0;
        startY = 0.0;
        this.setFont(Font.font(font_family, FontWeight.NORMAL, FontPosture.REGULAR,font_size));
        is_belong = false;
    }
    /**
     * This function return the current state of the data to draw or process
     * a job.
     * @return STARTING_TEXT
     */
    @Override
    public MMMState getStartingState() {
        return MMMState.STARTING_TEXT;
    }
    /**
     * This function designate the initial coordinate of the data to make.
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void start(int x, int y) {
        startX = x;
        startY = y;

    }
    public void starts(double x, double y) {
        setX(x);
        setY(y);
        startX = x;
	startY = y;
    }
    /**
     * This function change the current coordinate of the text when user dragged text. 
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void drag(int x, int y) {
        double diffX = x - startX;
        double diffY = y - startY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;
        xProperty().set(newX);
        yProperty().set(newY);
        startX = x;
        startY = y;
    }
    /**
     * This function print the current coordinate. 
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    public String cT(double x, double y){
        return "(x,y): (" + x + "," + y + ")";
    }
    /**
     * This function change the size of the text.
     * @param x : x coordinate
     * @param y : y coordinate  
     */
    @Override
    public void size(int x, int y) {
        setX(x);
        setY(y);
        double width = x - startX;
        double height = y- startY;
        double newX = x + width;
        double newY = x + height;
        
        //maxHeight(width);
        //maxWidth(height);

    }
    public boolean getIs_belong(){
        return is_belong;
    }
    public void setIs_belong(boolean is_belong){
         this.is_belong = is_belong;
    }
    public void zoominlocation(double zoomin) {
        setX(getX()*zoomin);
        setY(getY()*zoomin);
    }
    /**
     * This function return to width of the text.
     * @return 1 not use
     */   
    @Override
    public double getWidth() {
        return 1;
    }
    /**
     * This function return to height of the text. 
     * @return 1 not use
     */   
    @Override
    public double getHeight() {
        return 1;
    }
    /**
     * This function show the curren coordinate and size of the text when load the file in to the workspace. 
     * @param initX  : initial x coorinate
     * @param initY :  initial y coorinate
     * @param initWidth :  initial width
     * @param initHeight :  intial height 
     */
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        xProperty().set(initX);
	yProperty().set(initY);
    }
    /**
     * This function return the shape type as text to do a job.
     * @return TEXT
     */
    @Override
    public String getShapeType() {
        return TEXT;
    }
    /**
     * This function set the information whether text is italics or not.
     * @param is_italics set it as italics or not
     */   
    public void setIsItalic(boolean is_italics){
        this.is_Italics = is_italics;
    }
    /**
     * This function get the information whether text is italics or not.
     * return is_italics whether it is italics or not
     */ 
    public boolean getIsItalic(){
        return this.is_Italics;
    }
    /**
     * This function set the information whether text is bold or not.
     * @param is_Bold set it as bold or not
     */   
    public void setIsBold(boolean is_Bold){
        this.is_Bold = is_Bold;
    }
    /**
     * This function get the information whether bold is italics or not.
     * return is_Bold whether it is bold or not
     */ 
    public boolean getIsBold(){
        return this.is_Bold;
    }
    /**
     * This function set the font family of text.
     * @param string set the font family
     */ 
    public void setFamily(String string){
        this.font_family = string;
    }
    /**
     * This function return the font family of text.
     * return font_family
     */ 
    public String getFamily(){
        return this.font_family;
    }
    /**
     * This function set  the font size.
     * @param fontsize : size of the font
     */ 
    public void setFontSize(double fontSize){
        this.font_size = fontSize;
    }
    /**
     * This function return the font size.
     * @return font_size return the font size
     */ 
    public double getFontSize(){
        return this.font_size;
    }
    public void setFontsetting( String font_family, boolean is_Bold,boolean is_Italics,int font_size){
        
    }
    public void setFontFamilyChange( String font_family){
        this.setFamily(font_family);
       if(this.is_Bold){
            if(this.is_Italics){
                this.setFont(Font.font(font_family, FontWeight.BOLD, FontPosture.ITALIC, this.font_size)); 
            }else{
                this.setFont(Font.font(font_family, FontWeight.BOLD, FontPosture.REGULAR, this.font_size)); 
            }
        }else{
            if(this.is_Italics){
               this.setFont(Font.font(font_family, FontWeight.NORMAL, FontPosture.ITALIC, this.font_size));
           }else{
               this.setFont(Font.font(font_family, FontWeight.NORMAL, FontPosture.REGULAR, this.font_size)); 
           } 
       }
    }
    public void setFontBoldChange(boolean is_Bold){
        this.setIsBold(is_Bold);
       if(this.is_Bold){
            if(this.is_Italics){
                this.setFont(Font.font(this.font_family, FontWeight.BOLD, FontPosture.ITALIC, this.font_size)); 
            }else{
                this.setFont(Font.font(this.font_family, FontWeight.BOLD, FontPosture.REGULAR, this.font_size)); 
            }
        }else{
            if(this.is_Italics){
               this.setFont(Font.font(this.font_family, FontWeight.NORMAL, FontPosture.ITALIC, this.font_size));
           }else{
               this.setFont(Font.font(this.font_family, FontWeight.NORMAL, FontPosture.REGULAR, this.font_size)); 
           } 
       }
    }
    public void setFontItalicsChange(boolean is_Italics){
        this.setIsItalic(is_Italics);
       if(this.is_Bold){
            if(this.is_Italics){
                this.setFont(Font.font(this.font_family, FontWeight.BOLD, FontPosture.ITALIC, this.font_size)); 
            }else{
                this.setFont(Font.font(this.font_family, FontWeight.BOLD, FontPosture.REGULAR, this.font_size)); 
            }
        }else{
            if(this.is_Italics){
               this.setFont(Font.font(this.font_family, FontWeight.NORMAL, FontPosture.ITALIC, this.font_size));
           }else{
               this.setFont(Font.font(this.font_family, FontWeight.NORMAL, FontPosture.REGULAR, this.font_size)); 
           } 
       }
    }
    public void setFontFontsizeChange(double font_size){
        this.setFontSize(font_size);
       if(this.is_Bold){
            if(this.is_Italics){
                this.setFont(Font.font(font_family, FontWeight.BOLD, FontPosture.ITALIC, font_size)); 
            }else{
                this.setFont(Font.font(font_family, FontWeight.BOLD, FontPosture.REGULAR, font_size)); 
            }
        }else{
            if(this.is_Italics){
               this.setFont(Font.font(font_family, FontWeight.NORMAL, FontPosture.ITALIC, font_size));
           }else{
               this.setFont(Font.font(font_family, FontWeight.NORMAL, FontPosture.REGULAR, font_size)); 
           } 
       }
    }
    public void Wpressed(double movement){
        setY(getY()+movement);
    }
    public void Apressed(double movement){
        setX(getX()+movement);
    }
    public void Spressed(double movement){

        setY(getY()-movement);
    }
    public void Dpressed(double movement){
        setX(getX()-movement);
    }
    public String getWhoseText(){
        return this.whose;
    } 
    public void setWhoseText(String whose){
        this. whose = whose;
    }
}
