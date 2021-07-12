/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	if(txtPeriodo.getText() == null) {
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    	}
    	
    	String periodo = txtPeriodo.getText();
    	Integer pd;
    
    	
    	try {
    		pd = Integer.parseInt(periodo);
    		
    	} catch (NumberFormatException e){
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    		
    	}
    	
    	if(pd < 1 || pd > 2) {
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    	}
    	
    	List<Corso> corsi = model.getCorsiPerPeriodo(pd);
    	/*
    	for (Corso corso: corsi ) {
    		txtRisultato.appendText(corso.getNome() + "\n");
    	}
    	*/
    	txtRisultato.setStyle("-fx-font-family: monospace");
    	StringBuilder sb = new StringBuilder();
    	for (Corso c: corsi ) {
    		sb.append(String.format("%-8s  ", c.getCodins()));
    		sb.append(String.format("%-4d  ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		sb.append(String.format("%-4d\n", c.getPd()));
    	}
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	
    	if(txtPeriodo.getText() == null) {
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    	}
    	
    	String periodo = txtPeriodo.getText();
    	Integer pd;
    
    	
    	try {
    		pd = Integer.parseInt(periodo);
    		
    	} catch (NumberFormatException e){
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    		
    	}
    	
    	if(pd < 1 || pd > 2) {
    		txtRisultato.setText("devi inserire un numero 1 o 2 per il periodo");
    		return;
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	txtRisultato.setStyle("-fx-font-family: monospace");
    	
    	Map<Corso, Integer> corsoIscrizioni = model.getIscrittiPerPeriodo(pd);
    	for(Corso c: corsoIscrizioni.keySet()) {
    		sb.append(String.format("%-8s  ", c.getCodins()));
    		sb.append(String.format("%-4d  ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		sb.append(String.format("%-4d ", c.getPd()));
    		Integer n = corsoIscrizioni.get(c);
    		sb.append(String.format("%-4d\n", n));
    		
    	}
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String codiceCorso = txtCorso.getText();
    	
    	if(!model.esisteCorso(codiceCorso)) {
    		txtRisultato.appendText("Il corso inserito non esiste!");
    		return;
    	}
    	
    	Map<String,Integer> divisione = model.getDivisioneCDS(codiceCorso);
    	
    	for(String cds : divisione.keySet()) {
    		txtRisultato.appendText(cds + " " + divisione.get(cds) + "\n");
    	}
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String codiceCorso = txtCorso.getText();
    	
    	if(!model.esisteCorso(codiceCorso)) {
    		txtRisultato.appendText("Il corso inserito non esiste!");
    		return;
    	}
    	
    	List<Studente> studenti = model.getStudentiByCorso(codiceCorso);
    	
    	if(studenti.size() == 0 ) {
    		txtRisultato.appendText("il corso inserito non ha iscritti!");
    		return;
    	}
    	
    	for(Studente s : studenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
    
}