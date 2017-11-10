package component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XJW on 2017/5/23.
 * 类为自定义的半透明Panel
 */
public class TranslucentPanel extends JPanel {
    private float transparency;

    public TranslucentPanel() {
    }

    public TranslucentPanel(float transparency) {
        this.transparency = transparency;
    }

    /**
     * 这个方法用来设置JPanel的透明度
     *
     * @param transparency:透明度
     * @return void
     */
    public void setTransparent(float transparency) {
        this.transparency = transparency;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D) g.create();
        graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));
        graphics2d.setColor(getBackground());
        graphics2d.fillRect(0, 0, getWidth(), getHeight());
//          graphics2d.drawImage(background, 0, 0, getWidth(), getHeight(), 46, 114, 315, 521, this);
        graphics2d.dispose();
    }


}
