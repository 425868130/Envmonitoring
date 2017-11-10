package component;

import collectionClient.Client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XJW on 2017/5/22.
 * 本类为自定义带背景图片的JPanel类
 */
public class ImagePanel extends JPanel {
    ImageIcon icon;
    Image img;

    public ImagePanel(String imgSrc) {
        icon = new ImageIcon(Client.class.getResource(imgSrc));
        img = icon.getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println(this.getWidth() + "," + this.getHeight());
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
