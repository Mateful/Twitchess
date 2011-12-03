package de.fhb.projects.chesstwitterbot.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GenerateImage extends JFrame {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenerateImage() {
		try {
			interpretFan("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        new GenerateImage();
    }
    
    public void interpretFan(String fan) throws IOException{
    	int row=0,column=0;
    	fan="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    	BufferedImage backgroundImage = resetImage();
    	for(int i=0; ' '!=fan.charAt(i);i++){
    		if('r'==fan.charAt(i)){
    			generateImage("rook","-b",row, column,backgroundImage);
    		}else{
    			if('n'==fan.charAt(i)){
    				generateImage("knight","-b",row, column,backgroundImage);
        		}else{
        			if('b'==fan.charAt(i)){
        				generateImage("bishop","-b",row, column,backgroundImage);
            		}else{
            			if('q'==fan.charAt(i)){
            				generateImage("queen","-b",row, column,backgroundImage);
                		}else{
                			if('k'==fan.charAt(i)){
                				generateImage("king","-b",row, column,backgroundImage);
                    		}else{
                    			if('p'==fan.charAt(i)){
                    				generateImage("pawn","-b",row, column,backgroundImage);
                        		}else{
                            		if('/'==fan.charAt(i)){
                            			row++;
                            			column--;
                            		}                        			
                        		}
                    		}                    		
                		}            			
            		}        			
        		}    			
    		}    	
    		System.out.println("row="+row+"\n column="+column+"\nfanchar="+fan.charAt(i));
    		column = (column+1 + charToInt(fan.charAt(i))) % 8;
    	}   
    	createFinalImage(backgroundImage);
    	
    }
    
    public void createFinalImage (BufferedImage backgroundImage){
    	JLabel label = new JLabel(new ImageIcon(backgroundImage));
    	add(label);
    	pack();
    	setVisible(true);
    	
    }
    
    
    public BufferedImage resetImage() throws IOException{
    	
    	BufferedImage backgroundImage =    	
			backgroundImage = ImageIO.read(new File(
			        "field.png"));
	
    	
    	return backgroundImage;
    }
    
    public int charToInt(char ch){
 
    	if(ch=='1')return 0;
    	if(ch=='2')return 1;
    	if(ch=='3')return 2;
    	if(ch=='4')return 3;
    	if(ch=='5')return 4;
    	if(ch=='6')return 5;
    	if(ch=='7')return 6;
    	if(ch=='8')return 7;
    	return 0;
    }
    
    
    public void generateImage(String figure, String colour, int row, int column, BufferedImage backgroundImage){
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	try {
            
            
            BufferedImage foregroundImage = ImageIO
                    .read(new File(figure+colour+".png"));            
       
            System.out.println("rowC="+rowToCoordinate(row)+"\n columnC="+ columnToCoordinate(column));
            backgroundImage.getGraphics()
                    .drawImage(foregroundImage,columnToCoordinate(column), rowToCoordinate(row), this);
           
            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
     
    }
    
    public void drawImage(BufferedImage foregroundImage, BufferedImage backgroundImage){
    	JLabel label = new JLabel(new ImageIcon(backgroundImage));       	 
        add(label);
        pack();
        setVisible(true);
    }
 
    public int rowToCoordinate(int row){
    	switch(row){
    	case 0: return -2;
    	case 1: return 52;
    	case 2: return 108;
    	case 3: return 162;
    	case 4: return 218;
    	case 5: return 274;
    	case 6: return 330;
    	case 7: return 386;
    	default: return 0;
    	}
    }
    
    
    public int columnToCoordinate(int column){
    	switch(column){
    	case 0: return 52;
    	case 1: return 106;
    	case 2: return 162;
    	case 3: return 218;
    	case 4: return 274;
    	case 5: return 330;
    	case 6: return 386;
    	case 7: return 442;
    	default: return 0;
    	}
    } 
}















