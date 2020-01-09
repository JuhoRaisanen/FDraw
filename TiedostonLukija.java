/*

All saving and loading are implemented here
*/


import java.io.*;
import java.util.ArrayList;

public class TiedostonLukija {
	
	private String tiedosto;
	
	public TiedostonLukija(String tiedosto) {
		
		this.tiedosto = tiedosto;
	}
	
	
	public int tallennaKuva(String nimi, String[] kuva) {
		
		String[] arvot = new String[11];
		boolean onkoNimiTiedostossa = haeKuva(nimi, arvot);
		if(onkoNimiTiedostossa == false) {
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(tiedosto, true))) {
				
				bw.write("NAME=" + nimi);
				bw.newLine();
				for(String arvo : kuva) {
					bw.write(arvo);
					bw.newLine();
				}
				bw.close();
				return 1;
				
			} catch (IOException ioe) {
				return -1;
			}
			
		}
		
		return 0;
	}
	
	public int yliKirjoitaKuva(String nimi, String[] kuva) {
		
		String line = new String();
		ArrayList<String> lines = new ArrayList<>();
		
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tiedosto)))) {

            while ((line = br.readLine()) != null) {
				
                if (line.equals("NAME=" + nimi)) {
					lines.add(line);
					int i;
					for(i=0; i < 11; i++) {
						line = br.readLine();
						line = line.replace(line, kuva[i]);
						lines.add(line);
					}
				} else {
					lines.add(line);
				}
            }
			br.close();
			
        } catch (IOException ioe) {
            return -1;
        }
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(tiedosto))) {
				
			for(String s : lines) {
				bw.write(s);
				bw.newLine();
			}
			
			bw.close();
			return 1;
				
		} catch (IOException ioe) {
			return -1;
		}
	}
	
	public String[] haeKuvienNimet() {
		
		ArrayList<String> sanat = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tiedosto)))) {
			
			String rivi = new String();
			rivi = br.readLine();
			
			while(rivi != null) {
				
				if(rivi.length() > 5) {					
					if(rivi.substring(0, 5).equals("NAME=")) {
						
						rivi = rivi.substring(5);
						sanat.add(rivi);
					}
				}
				rivi = br.readLine();
			}
			
			br.close();
		} catch (IOException ioe) {
			String[] tulos = {"Unexpected Error with loading files"};
			return tulos;
		}
		
		int i = 0;
		int koko = sanat.size();
		String[] tulos = new String[koko];
		for(String s : sanat) {
			tulos[i] = s;
			i++;
		}
		return tulos;
	}
	
	public boolean haeKuva(String nimi, String[] arvot) {
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tiedosto)))) {
			
			String rivi = new String();
			rivi = br.readLine();
			
			while(rivi != null) {
				
				if(rivi.equals("NAME=" + nimi)) {
					int i;
					for(i=0; i < 11; i++) {
						rivi = br.readLine();
						arvot[i] = rivi;
					}
					br.close();
					return true;
				}
				rivi = br.readLine();
			}
			
			br.close();
		} catch (IOException ioe) {
			return false;
		}
		return false;
	}
	
}