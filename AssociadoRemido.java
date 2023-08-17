package felipeLagesDeLima.associacao;

public class AssociadoRemido extends Associado
{
    public long remissao;

    public AssociadoRemido(int numero, String nome, String telefone, long dataAssociacao, long nascimento, long remissao) throws ValorInvalido
    {
        super(numero, nome, telefone, dataAssociacao, nascimento);
        this.remissao = remissao;
    }
}