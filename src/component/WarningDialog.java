package component;

import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel warningText;
    private static WarningDialog warningDialog = new WarningDialog();

    public static WarningDialog getInstace() {
        return warningDialog;
    }

    private WarningDialog() {
        setTitle("");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(320,150));
        setLocationRelativeTo(null);
        setResizable(false);
        buttonOK.addActionListener(e -> onOK());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public WarningDialog setContent(String title, String msg) {
        this.setTitle(title);
        this.warningText.setText(msg);
        return this;
    }

    private void onOK() {
        dispose();
    }
}
