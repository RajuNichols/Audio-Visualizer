/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author nicho
 */
public class RknvknSuperVisualS21 implements Visualizer {
    private final String name = "Rknvkn VisualizerS21 ";
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private String vizPaneInitialStyle = "";
    
    private final Double bandHeightPercentage = 1.1;
    private final Double minRectangleHeight = 10.0;    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 240.0;
    
    private Rectangle[] rectangles;

    
    public RknvknSuperVisualS21(){
        
    }
    @Override
    public String getName(){
        return name;
    }
    
    @Override 
    public void start(Integer numBands,AnchorPane vizPane){
        end();
        
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        rectangles = new Rectangle[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(bandWidth / 2 + bandWidth * i);
            rectangle.setY(height / 2);
            rectangle.setWidth(bandWidth / 2);
            rectangle.setHeight(minRectangleHeight);
            rectangle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle);
            rectangles[i] = rectangle;
            
            
        }
    }
    @Override
    public void end(){
        if(rectangles != null){
            for(Rectangle rectangle : rectangles){
                vizPane.getChildren().remove(rectangle);
            }
            
            rectangles = null;
            vizPane.setClip(null);
        }
    }
    
    @Override
    public void draw(double timestamp, double length, float[] magnitudes, float[] phases){
        if (rectangles == null){
            return;
        }
        Integer num = min(rectangles.length,magnitudes.length);
        
        for(int i = 0; i < num; i++){
            
            rectangles[i].setLayoutY(((50.0 + magnitudes[i])/50.0) * halfBandHeight + minRectangleHeight);
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -5.0), 1.0, 1.0, 1.0));
            
            
            
            
        }   
    }
}
