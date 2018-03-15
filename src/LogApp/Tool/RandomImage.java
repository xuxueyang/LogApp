package LogApp.Tool;


import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class RandomImage {
    private String data = "";
    private int count;
    private int width;
    private int height;
    private static final int SIZE = 22;
    private static final String AZ09 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    BufferedImage image;
    public RandomImage(int width, int height, int count){
        this.width = width;
        this.height = height;
        this.count = count;
        setData();
    }
    private Color getRandColor(int fc, int bc){
        Random random = new Random();
        if(fc > 255) fc = 255;
        if(bc > 255) bc = 255;
        int red = fc + random.nextInt(bc - fc);
        int green = fc + random.nextInt(bc - fc);
        int blue = fc + random.nextInt(bc - fc);
        return new Color(red, green, blue);
    }
    private Color getColor(){
        Random random = new Random();
//        int red = random.nextInt(random.nextInt(10)*15);
//        int green = random.nextInt(random.nextInt(10)*15);
//        int blue = random.nextInt(random.nextInt(10)*15);
//        return new Color(red, green, blue);
        return  new Color(80,80,80);
    }
    private void setData(){
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            data += AZ09.charAt((random.nextInt(AZ09.length())));
        }
    }
    public String getData() {
        return data;
    }
    private int getY() {
        Random random = new Random();
        return random.nextInt(height - SIZE) + SIZE;
    }
    private int getX(int index) {
        return SIZE * index + SIZE / 2;
    }
    private void  drawImageLine(Graphics g) {
        int x1,y1,x2,y2;
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            g.setColor(getColor());
            x1 = random.nextInt(width);
            y1 = random.nextInt(height);
            x2 = random.nextInt(width);
            y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);   }
    }
    private void drawPoint(Graphics g) {
        int x,y;
        Random random = new Random();
        for (int i = 0; i < 120; i++) {
            g.setColor(getColor());
            x = random.nextInt(width);
            y = random.nextInt(height);
            g.drawOval(x, y, 1, 1);
        }
    }
    public BufferedImage getImage(){
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200,250));
        g.fillRect(0,0,width,height);
        drawImageLine(g);
        drawPoint(g);
        Font font = new Font("Times New Roman",Font.BOLD,SIZE);
        g.setFont(font);
        for (int i = 0; i < data.length(); i++) {
            g.setColor(getColor());
            g.drawString(String.valueOf(data.charAt(i)),getX(i),getY());
        }
        g.dispose();
        return image;
    }
}