/*

Resolution window, which opens the main program
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Resolution extends JFrame {
	
	private JPanel ruutu;
	private JLabel teksti;
	private JRadioButton b1;
	private JRadioButton b2;
	private JRadioButton b3;
	private JRadioButton b4;
	
	private int valinta;
	private JButton button;
	
	public Resolution() {
		
		setTitle("FunctionDrawer 1.0");
		valinta = 2;
		
		setSize(400, 200);
		setLocation(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		ruutu = new JPanel(new GridLayout(0, 1));
		teksti = new JLabel("Choose your screen resolution: ");
		
		b1 = new JRadioButton("1280 x 800");
		b1.setActionCommand("1280 x 800");
		b2 = new JRadioButton("1366 x 768");
		b2.setActionCommand("1366 x 768");
		b2.setSelected(true);
		b3 = new JRadioButton("1600 x 900");
		b3.setActionCommand("1600 x 900");
		b4 = new JRadioButton("1920 x 1080");
		b4.setActionCommand("1920 x 1080");
		
		ButtonGroup group = new ButtonGroup();
		group.add(b1);
		group.add(b2);
		group.add(b3);
		group.add(b4);
		
		b1.addActionListener(new ActionKuuntelija());
		b2.addActionListener(new ActionKuuntelija());
		b3.addActionListener(new ActionKuuntelija());
		b4.addActionListener(new ActionKuuntelija());
		
		button = new JButton("Continue");
		button.addActionListener(new ActionKuuntelija());
		
		ruutu.add(teksti);
		ruutu.add(b1);
		ruutu.add(b2);
		ruutu.add(b3);
		ruutu.add(b4);
		ruutu.add(button);
		
		add(ruutu);
		
		setVisible(true);
		
	}
	
	public void tuhoa() {
		dispose();
	}
	
	private class ActionKuuntelija implements ActionListener {
		
		private String tapahtuma;
		private Ikkuna ikkuna;
		
		public ActionKuuntelija() {}
		
		public void actionPerformed(ActionEvent e) {
		
			tapahtuma = e.getActionCommand();
			
			if(tapahtuma.equals("1280 x 800")) {
				valinta = 1;
			}
			else if(tapahtuma.equals("1366 x 768")) {
				valinta = 2;
			}
			else if(tapahtuma.equals("1600 x 900")) {
				valinta = 3;
			}
			else if(tapahtuma.equals("1920 x 1080")) {
				valinta = 4;
			}
			else if(tapahtuma.equals("Continue")) {
				
				//Creating the main frame window
				switch (valinta) {
					case 1:
					ikkuna = new Ikkuna(1180, 800);
					tuhoa();
					break;
					case 2:
					ikkuna = new Ikkuna(1266, 768);
					tuhoa();
					break;
					case 3:
					ikkuna = new Ikkuna(1500, 900);
					tuhoa();
					break;
					case 4:
					ikkuna = new Ikkuna(1820, 1080);
					tuhoa();
					break;
				}
			}			
		}
	}
}