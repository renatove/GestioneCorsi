package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	private CorsoDAO corsoDAO;
	
	public Model() {
		this.corsoDAO = new CorsoDAO();
	}
	
	public List<Corso> getCorsiPerPeriodo(Integer pd) {
		
		return corsoDAO.getCorsiPerPeriodo(pd);
		
	}
	
	public Map<Corso, Integer> getIscrittiPerPeriodo(Integer pd) {
		return corsoDAO.getIscrittiPerPeriodo(pd);
	}
	
	public List<Studente> getStudentiByCorso(String codiceCorso) {
		return corsoDAO.getStudentiByCorso(new Corso(codiceCorso,null,null,null));
	}

	public boolean esisteCorso(String codiceCorso) {
		return corsoDAO.esisteCorso(new Corso(codiceCorso,null,null,null));
	}
	
	public Map<String, Integer> getDivisioneCDS1(String codice) {
		Map<String,Integer> divisione = new HashMap<String, Integer>();
		List<Studente> studenti = this.getStudentiByCorso(codice);
		for(Studente s : studenti) {
			if(s.getCDS() != null && !s.getCDS().equals("")) {
				if(divisione.get(s.getCDS()) == null) {
					divisione.put(s.getCDS(), 1);
				} else {
					divisione.put(s.getCDS(), divisione.get(s.getCDS()) + 1);
				}
			}
		}
		return divisione;
	}
	
	public Map<String, Integer> getDivisioneCDS(String codice) {
		return corsoDAO.getDivisioneStudenti(new Corso(codice,null,null,null));
	}
}
