package felipeLagesDeLima.associacao;

public class Taxa 
{
    public String nome;
    public int vigencia, parcelas;
    public double valorAno;
    public boolean administrativo;

    public Taxa(String nome, int vigencia, double valorAno, int parcelas, boolean administrativo)
    {
        this.nome = nome;
        this.vigencia = vigencia;
        this.parcelas = parcelas;
        this.valorAno = valorAno;
        this.administrativo = administrativo;
    }
}
