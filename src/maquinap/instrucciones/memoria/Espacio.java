package maquinap.instrucciones.memoria;

public class Espacio {

	private int dir_com, tam;
	private boolean libre;
	
	public Espacio(int dir, int tam, boolean libre) {
		this.dir_com = dir;
		this.libre = libre;
		this.tam = tam;
	}

	public boolean isLibre() {
		return libre;
	}

	public void setLibre(boolean libre) {
		this.libre = libre;
	}

	public int getDir_com() {
		return dir_com;
	}

	public void setDir_com(int dir_com) {
		this.dir_com = dir_com;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}

	public int compareTo(Espacio e2) {
		if (this.dir_com < e2.dir_com){
			return 1;
		} else if (this.dir_com > e2.dir_com){
			return -1;
		}
		return 0;
	}

	public boolean estaEnEsteEspacioDeDirecciones(Integer dir) {
		return dir_com <= dir && dir_com+tam >= dir;
	}

	public boolean superaElTamanio(Integer dir, Integer cantidad) {
		return dir+tam<dir+cantidad;
	}
	
	public boolean loLiberaExacto(Integer dir, Integer cantidad) {
		return dir_com+tam==dir+cantidad;
	}
	
}
