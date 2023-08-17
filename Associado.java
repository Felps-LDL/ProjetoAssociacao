package felipeLagesDeLima.associacao;

import java.util.ArrayList;

public class Associado 
{
    public int num_positivo;
    public String nome, tel;
    public long nascimento, dataAssociacao;
    public ArrayList<Conta> contas = new ArrayList<Conta>();

    public Associado(int numero, String nome, String telefone, long dataAssociacao, long nascimento) throws ValorInvalido
    {
        if (numero < 0 || nome.isBlank() || nome == null || telefone.isBlank() || telefone == null|| dataAssociacao < 0 || nascimento < 0)
        {
            throw new ValorInvalido("Valor inválido");
        }
        
        num_positivo = numero;
        this.nome = nome;
        tel = telefone;
        this.dataAssociacao = dataAssociacao;
        this.nascimento = nascimento;
    }
}