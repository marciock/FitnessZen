package POJO;

public class RepertorioVO {


    private long id_repertorio;
    private long id_grupo;
    private String musica;
    private String caminho;

    public long getId_repertorio() {
        return id_repertorio;
    }
    public void setId_repertorio(long id_repertorio) {
        this.id_repertorio = id_repertorio;
    }
    public long getId_grupo() {
        return id_grupo;
    }
    public void setId_grupo(long id_grupo) {
        this.id_grupo = id_grupo;
    }
    public String getMusica() {
        return musica;
    }
    public void setMusica(String musica) {
        this.musica = musica;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
