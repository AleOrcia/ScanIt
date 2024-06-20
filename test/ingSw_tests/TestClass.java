package ingSw_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ingSw_beans.*;

public class TestClass {

	private Dipendente dipendente;
	private Prodotto prodotto;
	
	
	public TestClass() {
		this.dipendente = new Dipendente("Mario", "Rossi", "01/01/1990", "3333333333", "Via Dante Alighieri, 1", "123456789", "d0000");
		this.prodotto = new Prodotto("p0001","Biscotti","Biscotti integrali ricchi di fibre",3.28,10,"f4000");
	}
	
	@Test
    public void testDipendente() {
		assertEquals("Mario", this.dipendente.getNome());
		assertEquals("Rossi", this.dipendente.getCognome());
		assertEquals("01/01/1990", this.dipendente.getDataNascita());
		assertEquals("3333333333", this.dipendente.getNumeroTelefono());
		assertEquals("Via Dante Alighieri, 1", this.dipendente.getIndirizzo());
		assertEquals("123456789", this.dipendente.getIdDocumento());
		assertEquals("d0000", this.dipendente.getUsername());
		
		this.dipendente.setIdDocumento("2222222222");
		assertEquals("2222222222", this.dipendente.getIdDocumento());

    }

	@Test
    public void testProdotto() {
    	assertEquals("p0001", this.prodotto.getId());
		assertEquals("Biscotti", this.prodotto.getNome());
		assertEquals("Biscotti integrali ricchi di fibre", this.prodotto.getDescrizione());
		assertEquals(10, this.prodotto.getQuantita());
		assertEquals("f4000", this.prodotto.getIdFornitore());
		
		this.prodotto.setQuantita(this.prodotto.getQuantita() - 2);
		assertEquals(8, this.prodotto.getQuantita());
    }

}
