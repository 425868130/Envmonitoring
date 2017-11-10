package queryClient;

import component.TranslucentPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends TranslucentPanel {
    private static final long serialVersionUID = 1L;
    private static MyPanel myPanel = new MyPanel();
    int startPointMargin = 40, endPointMargin = 60;
    List<Point> pointList;
    /* 坐标起始点 */
    int startX = startPointMargin;
    int startY;
    int Xlength, Ylength, Xgap, Ygap;

    public static MyPanel getInstance() {
        return myPanel;
    }

    public void SetTemperatureList(List<Point> temperatureList) {
        /*清空之前的点*/
        if (pointList != null) {
            pointList.clear();
        }
        pointList = temperatureList;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();

		/* 计算x和y轴的长度 */
        Xlength = width - startPointMargin - endPointMargin;
        Ylength = height - startPointMargin - endPointMargin;

        g.drawString("时间( t )", width - endPointMargin, height - startPointMargin);
        g.drawString("温度( C )", startPointMargin, startPointMargin);
        g.drawString("温度曲线图", Xlength / 2, startPointMargin);

		/* y轴起始点 */
        startY = height - startPointMargin;

        Xgap = Xlength / 24;
        Ygap = Ylength / 15;

		/* 画出横坐标 */
        for (int i = 0, tempStartX = startX; i < 24; i++) {
            if (i < 24) {
                g.drawLine(tempStartX, startY, tempStartX + Xgap, startY);
                g.drawLine(tempStartX + Xgap, startY, tempStartX + Xgap, startY - 5);
            }
            g.drawString(String.valueOf(i), tempStartX + Xgap, startY + 20);
            tempStartX += Xgap;
        }

		/* 画出纵坐标 */
        for (int i = 0, tempStartY = startY, j = -30; i <= 15; i++, j += 5) {
            if (i < 15) {
                g.drawLine(startX, tempStartY, startX, tempStartY - Ygap);
                g.drawLine(startX, tempStartY - Ygap, startX + 5, tempStartY - Ygap);
            }
            g.drawString(String.valueOf(j), startX - 30, tempStartY);
            tempStartY -= Ygap;
        }
        /*如果温度列表为空则不画线*/
        if (pointList == null) {
            return;
        }
        /* 画折线图 */
        Point temp1, temp2;
        for (int i = 0; i < pointList.size() - 1; i++) {
            temp1 = coordinateMapper(pointList.get(i));
            temp2 = coordinateMapper(pointList.get(i + 1));
             /*同时画出两点的温度值*/
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(pointList.get(i).y), temp1.x, temp1.y - 10);
            g.drawString(String.valueOf(pointList.get(i + 1).y), temp2.x, temp2.y - 10);

            g.setColor(Color.RED);
             /*如果到了x轴最后则不连线*/
            if (pointList.get(i).x != 24) {
                g.drawLine(temp1.x, temp1.y, temp2.x, temp2.y);
            }
        }
    }

    /* 将列表中的坐标转换为绘图时的界面坐标 */
    public Point coordinateMapper(Point point) {
        // System.out.println(point.x + "," + point.y);
        int pointX = startX + point.x * Xgap;
        int pointY;
        int GapNum = Math.abs(point.y / 5);
        int addonLength = (int) (Math.abs(point.y % 5 / 5.0) * Ygap);
        /* 计算绘图时的Y坐标 */
        if (point.y > 0) {
            pointY = startY - ((6 + GapNum) * Ygap + addonLength);
        } else {
            pointY = startY - ((6 - GapNum) * Ygap) + addonLength;
        }
        return new Point(pointX, pointY);
    }
}/* MyPanel */