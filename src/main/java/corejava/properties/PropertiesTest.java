package corejava.properties;

import sun.security.provider.PolicySpiFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            PropertiesFrame frame = new PropertiesFrame();
            frame.setVisible(true);
        });
    }
}

class PropertiesFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;

    private File propertiesFile;
    private Properties settings;

    public PropertiesFrame () {
        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, ".corejava");
        if (!propertiesDir.exists()) {
            propertiesDir.mkdir();
        }
        propertiesFile = new File(propertiesDir, "program.properties");

        Properties defaultSetting = new Properties();
        defaultSetting.setProperty("left", "0");
        defaultSetting.setProperty("top", "0");
        defaultSetting.setProperty("width", "" + DEFAULT_WIDTH);
        defaultSetting.setProperty("height", "" + DEFAULT_HEIGHT);
        defaultSetting.setProperty("title", "");

        settings = new Properties(defaultSetting);

        if (propertiesFile.exists()) {
            try (InputStream in = new FileInputStream(propertiesFile)) {
                settings.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int left = Integer.parseInt(settings.getProperty("left"));
        int top = Integer.parseInt(settings.getProperty("top"));
        int width = Integer.parseInt(settings.getProperty("width"));
        int height = Integer.parseInt(settings.getProperty("height"));
        setBounds(left, top, width, height);

        String title = settings.getProperty("title");
        if (title.equals("")) {
            title = JOptionPane.showInputDialog("Please supply a frame title:");
        }
        if (title == null) {
            title = "";
        }
        setTitle(title);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                settings.setProperty("left", "" + getX());
                settings.setProperty("top", "" + getY());
                settings.setProperty("width", "" + getWidth());
                settings.setProperty("height", "" + getHeight());
                settings.setProperty("title", getTitle());

                try(OutputStream out = new FileOutputStream(propertiesFile)) {
                    settings.store(out, "Program Properties");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.exit(0);
            }
        });
    }
}
