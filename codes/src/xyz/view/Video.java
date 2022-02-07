package xyz.view;

//import com.sun.javaws.util.JfxHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Video {

    public  JPanel getVideo(String videoPath){
        JFXPanel VFXPanel = new JFXPanel();
        JPanel videoPanel =new JPanel();
        File video_source = new File(videoPath);
        Media m = new Media(video_source.toURI().toString());
        System.out.println(m);
        MediaPlayer player = new MediaPlayer(m);
        MediaView viewer = new MediaView(player);
        player.play();
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        // center video position
        javafx.geometry.Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        viewer.setX(400);
        viewer.setY(400);

        // resize video based on screen size
        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true);

        // add video to stackpane
        root.getChildren().add(viewer);

        VFXPanel.setScene(scene);
        //player.play();
        videoPanel.setLayout(new BorderLayout());
        videoPanel.add(VFXPanel, BorderLayout.CENTER);
        return videoPanel;
    }
}