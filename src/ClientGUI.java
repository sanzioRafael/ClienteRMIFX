import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.JOptionPane;

public class ClientGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass()
					.getResource("ClientGUI.fxml"));
			Scene scene = new Scene(pane);
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Cliente RMI");
			stage.getIcons().add(new Image("img/Filesystem-socket-icon-big.png"));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});
			stage.show();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}