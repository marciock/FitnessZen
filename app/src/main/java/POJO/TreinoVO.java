package POJO;

public class TreinoVO {

    private long id_treino;
    private long id_exercicio;
    private long id_grupo;
    private String ordem;
    private String serie;
    private String repeticao;
    private String carga;
    private String exercicio;

    public long getId_treino() {
        return id_treino;
    }
    public void setId_treino(long id_treino) {
        this.id_treino = id_treino;
    }

    public long getId_exercicio() {
        return id_exercicio;
    }
    public void setId_exercicio(long id_exercicio) {
        this.id_exercicio = id_exercicio;
    }
    public long getId_grupo() {
        return id_grupo;
    }
    public void setId_grupo(long id_grupo) {
        this.id_grupo = id_grupo;
    }
    public String getOrdem() {
        return ordem;
    }
    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public String getRepeticao() {
        return repeticao;
    }
    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }
    public String getCarga() {
        return carga;
    }
    public void setCarga(String carga) {
        this.carga = carga;
    }
    public String getExercicio() {
        return exercicio;
    }
    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }


}
