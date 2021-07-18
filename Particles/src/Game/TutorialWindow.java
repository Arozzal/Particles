package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class TutorialWindow {
	
	private JDialog window; 
	
	public TutorialWindow() {
		window = new JDialog();
		window.setTitle("Tutorial");
		window.setSize(1153, 557);
		window.setModal(true);
		window.setResizable(false);
		
		
		try {
			BufferedImage tutorialImage = ImageIO.read(getClass().getResourceAsStream("tutorial.png"));
			JLabel imageLabel = new JLabel(new ImageIcon(tutorialImage));
			window.add(imageLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void show() {
		window.setVisible(true);
	}
	
}
