package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public List<Corso> getCorsiPerPeriodo(Integer periodo) {
	
		String sql = "select * from corso where pd = ?";
		
		List<Corso> result = new ArrayList<>();
		
		try {
			Connection conn = Database.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				result.add(c);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	public Map<Corso, Integer> getIscrittiPerPeriodo(Integer pd) {
		String sql = "select c.codins, c.nome, c.crediti, c.pd, count(*) as tot from corso c, iscrizione i where c.codins = i.codins AND c.pd = ? group by c.codins, c.nome, c.crediti, c.pd;";
		
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = Database.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public List<Studente> getStudentiByCorso( Corso corso) {
		
		String sql = "select s.matricola, s.cognome, s.nome, s.cds studente "
		            +"from studente s, iscrizione i "
		            +"where s.matricola = i.matricola AND i.codins = ?";
		List<Studente> result = new LinkedList<Studente>();
		try {
			Connection conn = Database.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),rs.getString("cds"));
				result.add(s);
			}
			rs.close();
			st.close();
			conn.close();			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
		
	}

	public Map<String, Integer> getDivisioneStudenti( Corso corso) {
		String sql = "select s.CDS, count(*) as tot "
	          +"from studente s, iscrizione i "				
	          +"where s.matricola = i.matricola AND i.codins = ? AND s.cds <> '' "
	          +"group by s.CDS";	
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			Connection conn = Database.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {			
				result.put(rs.getString("CDS"), rs.getInt("tot"));
			}
			rs.close();
			st.close();
			conn.close();			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;		
	}

	
	public boolean esisteCorso(Corso corso) {
		String sql ="select * from corso where codins = ?";
		try {
			Connection conn = Database.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			if(rs.next() ) {
				rs.close();
				st.close();
				conn.close();				
				return true;
			} else {
				rs.close();
				st.close();
				conn.close();				
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}
}
