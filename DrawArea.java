/*

Class that draws the image on screen.

In the background of the image exists a xy-plane, so every pixel of the image has unique (x, y) coordinate.
The specific function F(x, y) calculates result values for every pixel´s coordinates, 
and pixels are coloured according to those values.

Calculation is executed recursively in method "laskeOsaArvo()".

*/


import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.swing.*;

public class DrawArea extends JPanel {
	
	private String funktio = new String("x2+y2");
	
	private int pixelkoko = 1;
	private int leveys = 1250;
	private int korkeus = 750;
	private int rangeMin = -10;
	private int rangeMax = 10;
	private float hue = 0.0f, sat = 0.75f, bright = 1.0f;
	private int drawMode = 1;
	
	private double minX = -60;
	private double maxX = 60;
	private double minY = -40;
	private double maxY = 40;
	
	private double mouseX = 0;
	private double mouseY = 0;
	private double mouseF = 0;
	private int koordinatesOnly = 0;
	private boolean tekstit = true;
	
	private int funktionPituus;
	private int maxPituus = 500;
	private double [] kertoimet = new double[maxPituus];
	private int kerroin = 0;
	
	private int [] funktionOsat = new int[maxPituus];
	
	private BufferedImage img;
	private Graphics2D g2;
	
	
	public DrawArea(int leveys, int korkeus) {
		this.leveys = leveys;
		this.korkeus = korkeus;
		int luku = setFunktio(funktio);
	}
	
	public String getFunktio() {
		return funktio;
	}
	
	public boolean getTekstit() {
		return tekstit;
	}
	
	public int getRangeMin() {
		return rangeMin;
	}
	public int getRangeMax() {
		return rangeMax;
	}
	public float getHue() {
		return hue;
	}
	public float getSat() {
		return sat;
	}
	public float getBright() {
		return bright;
	}
	public int getDrawMode() {
		return drawMode;
	}
	public double getMinX() {
		return minX;
	}
	public double getMaxX() {
		return maxX;
	}
	public double getMinY() {
		return minY;
	}
	public double getMaxY() {
		return maxY;
	}
	public BufferedImage getImage() {
		return img;
	}
	
	public void setRangeMin(int rangeMin) {
		this.rangeMin = rangeMin;
	}
	public void setRangeMax(int rangeMax) {
		this.rangeMax = rangeMax;
	}
	public void setHue(float hue) {
		this.hue = hue;
	}
	public void setTekstit(boolean tekstit) {
		this.tekstit = tekstit;
	}
	public void setSat(float sat) {
		this.sat = sat;
	}
	public void setBright(float bright) {
		this.bright = bright;
	}
	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
	}
	public void setMinX(double x) {
		minX = x;
	}
	public void setMaxX(double x) {
		maxX = x;
	}
	public void setMinY(double y) {
		minY = y;
	}
	public void setMaxY(double y) {
		maxY = y;
	}
	
	
	public void piirraUudelleen() {
		img = new BufferedImage(leveys, korkeus, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) img.getGraphics();
		koordinatesOnly = 0;
		repaint();
	}
	
	
	/*
	Drawing new function.
	First function is converted from string format to numeral form, so that it is easier to handle.
	Then function will be checked so that it has correct syntax.
	Then new image will be calculated.
	*/
	public int setFunktio(String funktio) {
		
		tyhjennaTaulukot();
		int tulos = kasitteleSyote(funktio);
		
		if(tulos == 1) {
			tulos = tarkistaFunktio();
		}
		
		if(tulos == 1) {
			this.funktio = funktio;
			piirraUudelleen();
		}
		
		return tulos;
	}
	
	private void tyhjennaTaulukot() {
		int i;
		for(i = 0; i < maxPituus; i++) {
			kertoimet[i] = 0;
			funktionOsat[i] = 0;
		}
		funktionPituus = 0;
		kerroin = 0;
	}
	
	//Checking that function written by user is in correct syntax
	private int tarkistaFunktio() {
		int i, k, input, sulkuja;
		int tulos = 1;
		int luku;
		
		for(i = 0; i < funktionPituus; i++) {
			
			input = funktionOsat[i];
			
			switch(input) {
				case 0:
				i = funktionPituus;
				tulos = 0;
				break;
				
				case 1:
				sulkuja = 1;
				for(k = i+1; k < funktionPituus; k++) {
					
					if(funktionOsat[k] == 1) {
						sulkuja++;
					}
					if(funktionOsat[k] == 2) {
						sulkuja--;
						if(sulkuja == 0) {
							k = funktionPituus;
						}
					}
				}
				if(sulkuja != 0) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
				
				case 2:
				break;
				
				case 3:
				luku = funktionOsat[i+1];
				if(luku < 10) {
					if(luku != 1 && luku != 7) {
						tulos = 0;
						i = funktionPituus;	
					}
				}
				break;
				
				case 4:
				luku = funktionOsat[i+1];
				if(luku < 10) {
					if(luku != 1 && luku != 7) {
						tulos = 0;
						i = funktionPituus;	
					}
				}
				break;
				
				case 5:
				luku = funktionOsat[i+1];
				if(luku < 10) {
					if(luku != 1 && luku != 7) {
						tulos = 0;
						i = funktionPituus;	
					}
				}
				break;
				
				case 6:
				luku = funktionOsat[i+1];
				if(luku < 10) {
					if(luku != 1 && luku != 7) {
						tulos = 0;
						i = funktionPituus;	
					}
				}
				break;
				
				case 12:
				if(funktionOsat[i+1] != 1) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
				
				case 13:
				if(funktionOsat[i+1] != 1) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
				
				case 14:
				if(funktionOsat[i+1] != 1) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
				
				case 15:
				if(funktionOsat[i+1] != 1) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
				
				case 16:
				if(funktionOsat[i+1] != 1) {
					tulos = 0;
					i = funktionPituus;
				}
				break;
			}
		}
		
		if(funktionPituus == 0) {
			tulos = 0;
		}
		return tulos;
	}
	
	//Converting function from string format to integer array
	private int kasitteleSyote(String funktio) {
		
		int i, k, c=0;
		int max = funktio.length();
		int [] kirjaimet = new int[max];
		
		for(i = 0; i < max; i++) {
			c = (int) funktio.charAt(i);
			kirjaimet[i] = c;
		}
		
		
		
		for(i = 0; i < max; i++) {
			
			c = kirjaimet[i];
			
			//c == numero
			if(c >= 48 && c <= 57) {
				for(k = i; k < max; k++) {
					c = kirjaimet[k];
					
					if((c < 48 || c > 57) && (c != 46)) {
						try {
						kertoimet[kerroin] = Double.parseDouble(funktio.substring(i, k));
						} catch (Exception e) {
							return 0;
						}
						kerroin++;
						
						int a=0, b=0;
						if(i > 0) {
							a = kirjaimet[i-1];
						}
						b = kirjaimet[k];
						onkoLukuVakio(a, b);
						
						i = k-1;
						k = max;
					}
					if(k == max-1) {
						try {
						kertoimet[kerroin] = Double.parseDouble(funktio.substring(i, k+1));
						} catch (Exception e) {
							return 0;
						}
						kerroin++;
						
						int a=0, b=0;
						if(i > 0) {
							a = kirjaimet[i-1];
						}
						onkoLukuVakio(a, b);
						
						i = k;
						k = max;						
					}
				}
				
			//c == x
			} else if (c == 88 || c == 120) {
				if(i-1 >= 0) {
					c = kirjaimet[i-1];
					if(c < 48 || c > 57) {
						kertoimet[kerroin] = 1;
						kerroin++;
					}
				} else { 
					kertoimet[kerroin] = 1;
					kerroin++;
				}
				
				if(i+1 < max) {
					c = kirjaimet[i+1];
					if(c < 48 || c > 57) {
						kertoimet[kerroin] = 1;
						kerroin++;
					}
				} else { 
					kertoimet[kerroin] = 1;
					kerroin++;
				}
				
				funktionOsat[funktionPituus] = 10;
				funktionPituus++;
				//c == y
			} else if (c == 89 || c == 121) {
				
				if(i-1 >= 0) {
					c = kirjaimet[i-1];
					if(c < 48 || c > 57) {
						kertoimet[kerroin] = 1;
						kerroin++;
					}
				} else { 
					kertoimet[kerroin] = 1;
					kerroin++;
				}
				
				if(i+1 < max) {
					c = kirjaimet[i+1];
					if(c < 48 || c > 57) {
						kertoimet[kerroin] = 1;
						kerroin++;
					}
				} else { 
					kertoimet[kerroin] = 1;
					kerroin++;
				}	
				
				funktionOsat[funktionPituus] = 11;
				funktionPituus++;
				
				
				//c == sin
			} else if (c == 83 || c == 115) {	
			
				if(i < max-2) {
					
					if(kirjaimet[i+1] == 73 || kirjaimet[i+1] == 105) {
						if(kirjaimet[i+2] == 78 || kirjaimet[i+2] == 110) {
							
							funktionOsat[funktionPituus] = 12;
							funktionPituus++;
							i += 2;
						}
					}
				}
			
			
			
				//c == cos
			} else if (c == 67 || c == 99) {	
			
				if(i < max-2) {
					
					if(kirjaimet[i+1] == 79 || kirjaimet[i+1] == 111) {
						if(kirjaimet[i+2] == 83 || kirjaimet[i+2] == 115) {
							
							funktionOsat[funktionPituus] = 13;
							funktionPituus++;
							i += 2;
						}
					}
				}
				
				//c == tan
			} else if (c == 84 || c == 116) {	
			
				if(i < max-2) {
					
					if(kirjaimet[i+1] == 65 || kirjaimet[i+1] == 97) {
						if(kirjaimet[i+2] == 78 || kirjaimet[i+2] == 110) {
							
							funktionOsat[funktionPituus] = 14;
							funktionPituus++;
							i += 2;
						}
					}
				}
				
				//c == e
			} else if (c == 69 || c == 101) {
				
				funktionOsat[funktionPituus] = 15;
				funktionPituus++;
				
				//c = log
			} else if (c == 76 || c == 108) {	
			
				if(i < max-2) {
					
					if(kirjaimet[i+1] == 79 || kirjaimet[i+1] == 111) {
						if(kirjaimet[i+2] == 71 || kirjaimet[i+2] == 103) {
							
							funktionOsat[funktionPituus] = 16;
							funktionPituus++;
							i += 2;
						}
					}
				}
				
				//c == (
			} else if (c == 40) {
				
				funktionOsat[funktionPituus] = 1;
				funktionPituus++;
				// c== )
			} else if (c == 41) {
				
				funktionOsat[funktionPituus] = 2;
				funktionPituus++;
				// c == *
			} else if (c == 42) {
				
				funktionOsat[funktionPituus] = 5;
				funktionPituus++;
				//c == +
			} else if (c == 43) {
				
				funktionOsat[funktionPituus] = 3;
				funktionPituus++;
				//c == -
			} else if (c == 45) {
		
				funktionOsat[funktionPituus] = 4;
				funktionPituus++;
				//c == /
			} else if (c == 47) {
				
				funktionOsat[funktionPituus] = 6;
				funktionPituus++;
			}
			
			
		}
		
		return 1;
	}
	
	private void onkoLukuVakio(int a, int b) {
			
		//Luvun kummallakaan puolella ei ole x tai y
		if(a != 88 && a != 120 && a != 89 && a != 121) {
			if(b != 88 && b != 120 && b != 89 && b != 121) {
				//Luku on vakio
				funktionOsat[funktionPituus] = 7;
				funktionPituus++;
			}
		}
	}
	
	
	/*
	When encountering a bracket mark "(" in function, method will calculate the point where closing bracket ")" is
	*/
	private int annaSulkujenLoppu(int alku) {
		int sulkuja = 1;
		int tulos = 0;
		int i;
		
		for(i = alku+1; i < funktionPituus; i++) {
			
			if(funktionOsat[i] == 1) {
				sulkuja++;
			}
			if(funktionOsat[i] == 2) {
				sulkuja--;
				if(sulkuja == 0) {
					
					tulos = i;
					i = funktionPituus;
				}
			}
		}
		
		return tulos;
	}

	/*
	Recursive method for calculating the  pixel values. 
	Attributes are: pixel x-coordinate, pixel y-coordinate, first point of the function part in process, last point of the function part in process
	
	For every pixel, the function will be read recursively from start to finish.
	After the iterations, function will return a double value F(x, y) that is function value for pixel (x, y).
	*/
	private double laskeOsaArvo(double x, double y, int alku, int loppu) {
		
		int i, k, input, sulku;
		double tulos = 0;
		
		for(i = alku; i < loppu; i++) {
			
			input = funktionOsat[i];
			
			switch(input) {
				
				// (
				case 1:
				sulku = annaSulkujenLoppu(i);
				tulos = laskeOsaArvo(x, y, i+1, sulku);
				i = sulku;
				break;
				
				// )
				case 2:
				break;
				
				// +
				case 3:
				tulos += laskeOsaArvo(x, y, i+1, loppu);
				i = loppu;
				break;
				
				// -
				case 4:
				tulos -= laskeOsaArvo(x, y, i+1, loppu);
				i = loppu;
				break;
				
				// *
				case 5:
				if(funktionOsat[i+1] == 1) {
					sulku = annaSulkujenLoppu(i+1);
					tulos *= laskeOsaArvo(x, y, i+2, sulku);
					i = sulku;
				} else {
					tulos *= laskeOsaArvo(x, y, i+1, loppu);
					i = loppu;
				}
				break;
				
				// /
				case 6:
				if(funktionOsat[i+1] == 1) {
					sulku = annaSulkujenLoppu(i+1);
					
					double arvo = laskeOsaArvo(x, y, i+2, sulku);
					if(arvo != 0) { tulos = tulos/arvo; }
					else {tulos = -1000000; }
					i = sulku;
					
				} else {
					double arvo = laskeOsaArvo(x, y, i+1, loppu);
					if(arvo != 0) { tulos = tulos/arvo; }
					else {tulos = -1000000; }
					i = loppu;
				}
				break;
				
				case 7:
				tulos = kertoimet[kerroin];
				kerroin++;
				break;
				
				case 10:
				tulos = kertoimet[kerroin]*Math.pow(x, kertoimet[kerroin+1]);
				kerroin += 2;
				break;
				
				case 11:
				tulos = kertoimet[kerroin]*Math.pow(y, kertoimet[kerroin+1]);
				kerroin += 2;
				break;
				
				case 12:
				sulku = annaSulkujenLoppu(i+1);
				tulos = Math.sin(laskeOsaArvo(x, y, i+2, sulku));
				i = sulku;
				break;
				
				case 13:
				sulku = annaSulkujenLoppu(i+1);
				tulos = Math.cos(laskeOsaArvo(x, y, i+2, sulku));
				i = sulku;
				break;
				
				case 14:
				sulku = annaSulkujenLoppu(i+1);
				tulos = Math.tan(laskeOsaArvo(x, y, i+2, sulku));
				i = sulku;
				break;
				
				case 15:
				sulku = annaSulkujenLoppu(i+1);
				tulos = Math.pow(tulos, laskeOsaArvo(x, y, i+2, sulku));
				i = sulku;
				break;
				
				case 16:
				sulku = annaSulkujenLoppu(i+1);
				tulos = Math.log(laskeOsaArvo(x, y, i+2, sulku));
				i = sulku;
				break;
			}
		}
		
		return tulos;
	}
	
	//DrawMode setting
	private void updateColour(double arvo) {
		
		switch(drawMode) {
			
			case 1:
			bright = (float) arvo;
			break;
			
			case 2:
			sat = (float) arvo;
			break;
			
			case 3:
			hue = (float) arvo;
			break;
			
			case 4:
			bright = (float) arvo;
			sat = (float) arvo;
			break;
			
			case 5:
			bright = (float) arvo;
			hue = (float) arvo;
			break;
			
			case 6:
			hue = (float) arvo;
			sat = (float) arvo;
			break;
			
			case 7:
			hue = (float) arvo;
			sat = (float) arvo;
			bright = (float) arvo;
			break;
		}
	}
	
	
	/*
	Method analyzes every pixel of the image and calculates according pixel values.
	Then pixel values are converted to HSB colors according to setting values. 
	*/
	@Override
	protected void paintComponent(Graphics g) {
		
		if(koordinatesOnly == 0) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 3000, 3000);
		Color co = new Color(0);
		
		int xx, yy, yyy;
		double arvo, osaArvo, a, b;
		for(xx = 0; xx < leveys; xx += pixelkoko) {
			
			for (yy = 0; yy < korkeus; yy += pixelkoko) {
				
				a = (double)xx/((double)leveys/(maxX-minX)) + minX;
				b = (double)yy/((double)korkeus/(maxY-minY)) + minY;
				
				kerroin = 0;
				osaArvo = laskeOsaArvo(a, b, 0, funktionPituus);
				arvo = (double) (osaArvo - rangeMin)/(rangeMax-rangeMin);
				
				if(arvo > 1) {arvo = 1;}
				if(arvo < 0) {arvo = 0;}
				
				updateColour(arvo);
				co = new Color(Color.HSBtoRGB(hue, sat, bright));				
				g2.setColor(co);
				
				yyy = yy*(-1) + korkeus;
				g2.fillRect(xx, yyy, pixelkoko, pixelkoko);
				
			}
		}
		}
		
		g.drawImage(img, 0, 0, null);
		
		if(tekstit) {
			g.setColor(Color.WHITE);
			
			String teksti = String.format("%.2f", mouseX);		
			g.drawString("X:   " + teksti, leveys-150, 20);
			
			teksti = String.format("%.2f", mouseY);
			g.drawString("Y:   " + teksti, leveys-150, 35);
			
			teksti = String.format("%.2f", mouseF);
			g.drawString("F(): " + teksti, leveys-150, 50);
		}

	}
	
	//Calculating mouse x & y coordinates and mouse point function value in top right corner
	public void laske(int x, int y) {
		
		y = y*(-1) + korkeus;
		
		mouseX = (double)x/((double)leveys/(maxX-minX)) + minX;
		mouseY = (double)y/((double)korkeus/(maxY-minY)) + minY;
		
		kerroin = 0;
		mouseF = laskeOsaArvo(mouseX, mouseY, 0, funktionPituus);
		
		koordinatesOnly = 1;
		repaint();
	}
	
}