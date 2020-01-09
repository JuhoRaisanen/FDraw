/*
Main Frame Window which implements all GUI settings. 
It also creates:
- DrawArea object that draws the image on screen
- TiedostonLukija object that handles saving and loading

*/


import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.*;

public class Ikkuna extends JFrame implements MouseMotionListener {
	
	private JPanel nappulat;
	private DrawArea kuva;
	private JButton funktioButton;
	private JButton asetusButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton buildButton;
	private JButton exitButton;
	private JButton helpButton;
	public static TiedostonLukija lukija;
	
	private int leveys;
	private int korkeus;
	
	private static FunktioIkkuna fi;
	private static AsetusIkkuna ai;
	
	public Ikkuna(int leveys, int korkeus) {
		
		this.leveys = leveys;
		this.korkeus = korkeus;
		
		setTitle("Funktiokuva");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setBackground(Color.BLACK);
		rakennaRuutu();

		Container contentPane = getContentPane();
		contentPane.add(nappulat, BorderLayout.LINE_END);
		contentPane.add(kuva, BorderLayout.CENTER);
		
		setVisible(true);
		ai = null;
		fi = null;
		lukija = new TiedostonLukija("saves.txt");
		
		addMouseMotionListener(this);
	}
	
	private void rakennaRuutu() {
		
		helpButton = new JButton("Help");
		funktioButton = new JButton("Function");
		asetusButton = new JButton("Settings");
		loadButton = new JButton("Load");
		saveButton = new JButton("Save");
		buildButton = new JButton("Create PNG");
		exitButton = new JButton("Exit");
		
		helpButton.addActionListener(new NappulanKuuntelija());
		asetusButton.addActionListener( new NappulanKuuntelija());
		funktioButton.addActionListener( new NappulanKuuntelija());
		loadButton.addActionListener(new NappulanKuuntelija());
		saveButton.addActionListener(new NappulanKuuntelija());
		exitButton.addActionListener( new NappulanKuuntelija());
		buildButton.addActionListener(new NappulanKuuntelija());
		kuva = new DrawArea(leveys, korkeus);
		nappulat = new JPanel();
		
		nappulat.setLayout(new BoxLayout(nappulat, BoxLayout.PAGE_AXIS));
		nappulat.add(helpButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(asetusButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(funktioButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(loadButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(saveButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(buildButton);
		nappulat.add(Box.createRigidArea(new Dimension(0, 10)));
		nappulat.add(exitButton);
		
		nappulat.setBackground(Color.BLACK);
	}
	
	public static FunktioIkkuna getFunktioIkkuna() {
		return fi;
	}
	public static AsetusIkkuna getAsetusIkkuna() {
		return ai;
	}
	public static void setFunktioIkkuna(FunktioIkkuna f) {
		fi = f;
	}
	public static void setAsetusIkkuna(AsetusIkkuna a) {
		ai = a;
	}
	
	private class NappulanKuuntelija implements ActionListener {
		
		private JTextField [] input;
		private int taulukonKoko = 0;
		
		public NappulanKuuntelija(JTextField[] taulukko, int taulukonKoko) {
			this.taulukonKoko = taulukonKoko;
			input = new JTextField[taulukonKoko];
			int i;
			for(i = 0; i < taulukonKoko; i++) {
				input[i] = taulukko[i];
			}
		}
		
		public NappulanKuuntelija() {
			input = null;
		}
		
		public void actionPerformed (ActionEvent e) {
			
			String tapahtuma = e.getActionCommand();
			
			if(tapahtuma.equals("Help")) {
				HelpIkkuna hi = new HelpIkkuna();
			}
			
			else if(tapahtuma.equals("Function")) {
				
				if(fi != null) {
					fi.tuhoa();
				}				
				fi = new FunktioIkkuna();				
			}
			
			else if(tapahtuma.equals("Calculate")) {
				
				int a = 0;
				String syote = new String();
				try {
					syote = input[0].getText();
					a = kuva.setFunktio(syote);
					if(a == 1) {
						return;						
					} else {
						JOptionPane.showMessageDialog(null, "Syntax error with reading function");
						return;
					}
					
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Error, try again");
					return;
				}
			}
			
			else if(tapahtuma.equals("Settings")) {
				if(ai != null) {
					ai.tuhoa();
				}	
				ai = new AsetusIkkuna();				
			}
			
			else if(tapahtuma.equals("Apply")) {
				
				try {
					float hue = Float.parseFloat(input[0].getText());
					float sat = Float.parseFloat(input[1].getText());
					float bright = Float.parseFloat(input[2].getText());
					int drawMode = Integer.parseInt(input[3].getText());
					int rangeMin = Integer.parseInt(input[4].getText());
					int rangeMax = Integer.parseInt(input[5].getText());
					double minX = Double.parseDouble(input[6].getText());
					double maxX = Double.parseDouble(input[7].getText());
					double minY = Double.parseDouble(input[8].getText());
					double maxY = Double.parseDouble(input[9].getText());
					
					kuva.setHue(hue);
					kuva.setSat(sat);
					kuva.setBright(bright);
					kuva.setDrawMode(drawMode);
					kuva.setRangeMin(rangeMin);
					kuva.setRangeMax(rangeMax);
					kuva.setMinX(minX);
					kuva.setMaxX(maxX);
					kuva.setMinY(minY);
					kuva.setMaxY(maxY);
					
					kuva.piirraUudelleen();
					
				} catch (NumberFormatException nfe) {
					
					JOptionPane.showMessageDialog(null, "Error, try again");
				}
			}
			
			else if(tapahtuma.equals("Save")) {
				SaveIkkuna si = new SaveIkkuna();
			}
			else if(tapahtuma.equals("Load")) {
				LoadIkkuna li = new LoadIkkuna();
			}
			
			else if(tapahtuma.equals("Save image")) {
				String nimi = input[0].getText();
				String[] arvot = new String[11];
				arvot[0] = Float.toString(kuva.getHue());
				arvot[1] = Float.toString(kuva.getSat());
				arvot[2] = Float.toString(kuva.getBright());
				arvot[3] = Integer.toString(kuva.getDrawMode());
				arvot[4] = Integer.toString(kuva.getRangeMin());
				arvot[5] = Integer.toString(kuva.getRangeMax());
				arvot[6] = Double.toString(kuva.getMinX());
				arvot[7] = Double.toString(kuva.getMaxX());
				arvot[8] = Double.toString(kuva.getMinY());
				arvot[9] = Double.toString(kuva.getMaxY());
				arvot[10] = kuva.getFunktio();
				
				int tulos = lukija.tallennaKuva(nimi, arvot);
				if(tulos == 1) {
					
					JOptionPane.showMessageDialog(null, "Image saved succesfully!");
				} else if (tulos == -1) {
					
					JOptionPane.showMessageDialog(null, "Error, try again");
				} else if (tulos == 0) {
					
					int kysymys = JOptionPane.showConfirmDialog(null, "The name is already used. Do you want to overwrite?");
					if(kysymys == 0) {
						tulos = lukija.yliKirjoitaKuva(nimi, arvot);
						if(tulos == 1) {
							JOptionPane.showMessageDialog(null, "Image saved succesfully!");
						} else if (tulos == -1) {
							JOptionPane.showMessageDialog(null, "Error, try again");
						}
					}
				}
			}
			
			else if(tapahtuma.equals("Load image")) {
				String nimi = input[0].getText();
				String[] arvot = new String[11];

				boolean haeKuvaa = lukija.haeKuva(nimi, arvot);
				if(haeKuvaa == true) {
					try {
						float hue = Float.parseFloat(arvot[0]);
						float sat = Float.parseFloat(arvot[1]);
						float bright = Float.parseFloat(arvot[2]);
						int drawMode = Integer.parseInt(arvot[3]);
						int rangeMin = Integer.parseInt(arvot[4]);
						int rangeMax = Integer.parseInt(arvot[5]);
						double minX = Double.parseDouble(arvot[6]);
						double maxX = Double.parseDouble(arvot[7]);
						double minY = Double.parseDouble(arvot[8]);
						double maxY = Double.parseDouble(arvot[9]);
						
						kuva.setHue(hue);
						kuva.setSat(sat);
						kuva.setBright(bright);
						kuva.setDrawMode(drawMode);
						kuva.setRangeMin(rangeMin);
						kuva.setRangeMax(rangeMax);
						kuva.setMinX(minX);
						kuva.setMaxX(maxX);
						kuva.setMinY(minY);
						kuva.setMaxY(maxY);
						
						int a = kuva.setFunktio(arvot[10]);
						if(a != 1) {
							JOptionPane.showMessageDialog(null, "Error with reading file");
						}
						
						kuva.piirraUudelleen();
						JOptionPane.showMessageDialog(null, "Load Success");
						
					} catch (NumberFormatException nfe) {
						
						JOptionPane.showMessageDialog(null, "Error with reading file");
					}					
				} else {
					JOptionPane.showMessageDialog(null, "Error with reading file");
				}
			}
			
			else if(tapahtuma.equals("Create PNG")) {
				
				BuildIkkuna bi = new BuildIkkuna();
			}
			
			else if(tapahtuma.equals("Save PNG")) {
				
				String nimi = new String();
				
				try {
					nimi = input[0].getText();
					
					File f = new File(nimi + ".png");
					ImageIO.write(kuva.getImage(), "png", f);
					JOptionPane.showMessageDialog(null, "Image saved succesfully!");
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(null, "Error with saving file");
				}
			}
			
			else if(tapahtuma.equals("Exit")) {
				System.exit(0);
			}
		}
	}
	
	private class ItemKuuntelija implements ItemListener {
		
		public ItemKuuntelija() {}
		
		public void itemStateChanged(ItemEvent e) {
		
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				kuva.setTekstit(false);
			} else {
				kuva.setTekstit(true);
			}
		}
	}

	
	private class FunktioIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JLabel viesti;
		private JTextField funktioKentta;
		private JButton syotaArvo;
		
		public FunktioIkkuna() {
			
			setTitle("Funktio");
			setSize(400, 200);
			setLocation(300, 300);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			viesti = new JLabel("Write function: ");
			funktioKentta = new JTextField(30);
			funktioKentta.setText(kuva.getFunktio());
			
			syotaArvo = new JButton("Calculate");
			JTextField [] taulukko = {funktioKentta};
			syotaArvo.addActionListener( new NappulanKuuntelija(taulukko, 1));
			
			ruutu = new JPanel();
			
			ruutu.add(viesti);
			ruutu.add(funktioKentta);
			ruutu.add(syotaArvo);
			
			add(ruutu);
			
			setVisible(true);
		}
		
		public void tuhoa() {
			setVisible(false);
			dispose();
		}
	}
	
	private class SaveIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JLabel viesti;
		private JTextField tekstiKentta;
		private JButton syotaArvo;
		
		public SaveIkkuna() {
			
			setTitle("Save the image");
			setSize(400, 200);
			setLocation(300, 300);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			viesti = new JLabel("Write the name of the image: ");
			tekstiKentta = new JTextField(30);
			
			syotaArvo = new JButton("Save image");
			JTextField [] taulukko = {tekstiKentta};
			syotaArvo.addActionListener( new NappulanKuuntelija(taulukko, 1));
			
			ruutu = new JPanel();
			
			ruutu.add(viesti);
			ruutu.add(tekstiKentta);
			ruutu.add(syotaArvo);
			
			add(ruutu);
			
			setVisible(true);
		}
	}
	
	private class HelpIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JTextArea tekstiKentta;
		private JScrollPane scrollPane;
		
		public HelpIkkuna() {
			
			setTitle("Help");
			setSize(1000, 600);
			setLocation(250, 250);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tekstiKentta = new JTextArea();
			
			try {
				FileReader reader = new FileReader("help.txt");
				BufferedReader br = new BufferedReader(reader);
				tekstiKentta.read(br, null);
				br.close();
				tekstiKentta.requestFocus();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error with reading file 'help.txt' ");
			}
			
			tekstiKentta.setEditable(false);
			
			
			scrollPane = new JScrollPane(tekstiKentta);
			
			ruutu = new JPanel(new BorderLayout(10, 10));
			
			ruutu.add(scrollPane, BorderLayout.CENTER);
			
			add(ruutu);
			
			setVisible(true);
		}
	}
	
	private class BuildIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JLabel viesti;
		private JTextField tekstiKentta;
		private JButton syotaArvo;
		
		public BuildIkkuna() {
			
			setTitle("Create PNG Image");
			setSize(400, 200);
			setLocation(300, 300);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			viesti = new JLabel("Write the name of the image: ");
			tekstiKentta = new JTextField(30);
			
			syotaArvo = new JButton("Save PNG");
			JTextField [] taulukko = {tekstiKentta};
			syotaArvo.addActionListener( new NappulanKuuntelija(taulukko, 1));
			
			ruutu = new JPanel();
			
			ruutu.add(viesti);
			ruutu.add(tekstiKentta);
			ruutu.add(syotaArvo);
			
			add(ruutu);
			
			setVisible(true);
		}
	}
	
	private class LoadIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JPanel nappulat;
		private JLabel viesti;
		private JList kuvat;
		private JScrollPane kuvaKentta;
		private JTextField tekstiKentta;
		private JButton lataaKuva;
		private String[] kuvaNimet;
		
		public LoadIkkuna() {
			
			setTitle("Load image");
			setSize(400, 400);
			setLocation(300, 300);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			kuvaNimet = lukija.haeKuvienNimet();
			kuvat = new JList(kuvaNimet);
			kuvat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			kuvat.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					tekstiKentta.setText(kuvaNimet[kuvat.getSelectedIndex()]);
				}			
			});
			
			kuvaKentta = new JScrollPane(kuvat);
			
			viesti = new JLabel("Select the name of the image: ");
			tekstiKentta = new JTextField(30);
			
			lataaKuva = new JButton("Load image");
			JTextField [] taulukko = {tekstiKentta};
			lataaKuva.addActionListener(new NappulanKuuntelija(taulukko, 1));
			
			nappulat = new JPanel();
			nappulat.setLayout(new BoxLayout(nappulat, BoxLayout.LINE_AXIS));
			nappulat.add(tekstiKentta);
			nappulat.add(Box.createRigidArea(new Dimension(10, 0)));
			nappulat.add(lataaKuva);
			
			ruutu = new JPanel(new BorderLayout(0, 10));
			ruutu.add(viesti, BorderLayout.PAGE_START);
			ruutu.add(kuvaKentta, BorderLayout.CENTER);
			ruutu.add(nappulat, BorderLayout.PAGE_END);
			
			add(ruutu);
			
			setVisible(true);
		}
	}

	
	
	private class AsetusIkkuna extends JFrame {
		
		private JPanel ruutu;
		private JButton laskeTulos;
		private JCheckBox tekstiButton;

		
		public AsetusIkkuna() {
			
			setTitle("Settings");
			setSize(550, 500);
			setLocation(200, 200);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			String[] tekstit = {"Set hue (0-1): ", "Set saturation (0-1): ", "Set brightness (0-1): ", "Set drawing mode (1-7): ", 
			"Set minimum drawing limit: ", "Set maximum drawing limit: ", "Set minimum x: ", "Set maximum x:", 
			"Set minimum y:", "Set maximum y:"};
			String [] arvot = new String[10];
			arvot[0] = Float.toString(kuva.getHue());
			arvot[1] = Float.toString(kuva.getSat());
			arvot[2] = Float.toString(kuva.getBright());
			arvot[3] = Integer.toString(kuva.getDrawMode());
			arvot[4] = Integer.toString(kuva.getRangeMin());
			arvot[5] = Integer.toString(kuva.getRangeMax());
			arvot[6] = Double.toString(kuva.getMinX());
			arvot[7] = Double.toString(kuva.getMaxX());
			arvot[8] = Double.toString(kuva.getMinY());
			arvot[9] = Double.toString(kuva.getMaxY());
			JTextField [] taulukko = new JTextField[10];
			
			boolean check = kuva.getTekstit();
			tekstiButton = new JCheckBox("Show coordinates");
			tekstiButton.setSelected(check);
			tekstiButton.addItemListener(new ItemKuuntelija());		
			
			ruutu = new JPanel(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 5;
			c.anchor = GridBagConstraints.LINE_END;
			
			ruutu.add(tekstiButton, c);
			c.gridy++;
			
			int i;
			for(i = 0; i < 10; i++) {
				JLabel l = new JLabel(tekstit[i]);
				JTextField t = new JTextField(10);
				
				t.setText(arvot[i]);
				
				ruutu.add(l, c);
				c.gridx++;
				
				c.anchor = GridBagConstraints.LINE_START;
				ruutu.add(t, c);
				c.gridx--;
				c.gridy++;
				c.anchor = GridBagConstraints.LINE_END;

				taulukko[i] = t;
			}
			
			laskeTulos = new JButton("Apply");
			laskeTulos.addActionListener(new NappulanKuuntelija(taulukko, 10));
			
			ruutu.add(laskeTulos, c);
			
			add(ruutu);
			
			setVisible(true);
		}
		
		public void tuhoa() {
			setVisible(false);
			dispose();
		}
	}
	
	public void mouseMoved(MouseEvent e)
	{
	int x = e.getX();
	int y = e.getY();
	
	if(x >= 0 && x <= leveys) {
		if(y >= 0 && y <= korkeus) {
			kuva.laske(x, y);
		}
	}
	}
	public void mouseDragged(MouseEvent e)
	{}
	
}