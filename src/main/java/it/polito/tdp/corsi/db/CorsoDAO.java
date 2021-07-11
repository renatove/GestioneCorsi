package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

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
}
