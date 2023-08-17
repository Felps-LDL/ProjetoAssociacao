package felipeLagesDeLima.associacao;

import java.util.ArrayList;

public class MinhaAssociacao implements InterfaceAssociacao
{
    public ArrayList<Associacao> associacoes = new ArrayList<Associacao>();

    public double calcularFrequencia(int numAssociado, int numAssociacao, long inicio, long fim) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente
    {
        Associacao associacao_temp = pesquisar(numAssociacao);

        if (associacao_temp == null)throw new AssociacaoNaoExistente("Associação não existente");
        
        Associado associado_temp = null;

        for (Associado associado : associacao_temp.associados)
        {
            if (associado.num_positivo == numAssociado)
            {
                associado_temp = associado;
            }
        }

        if (associado_temp == null)throw new AssociadoNaoExistente("Associação não existente");

        double reunioes = 0, freqnc = 0;

        for (Reuniao reuniao : associacao_temp.reunioes)
        {
            if (reuniao.data >= inicio && reuniao.data <= fim)
            {
                reunioes++;
                for (Integer cod : reuniao.frequencia)
                {
                    if (cod == numAssociado)
                    {
                        freqnc++;
                        break;
                    }
                }
            }
        }

        if (reunioes == 0)throw new ReuniaoNaoExistente("Reunião não existente");

        return freqnc / reunioes;
    }
    
    public void registrarFrequencia(int codigoAssociado, int numAssociacao, long dataReuniao)throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente, FrequenciaJaRegistrada, FrequenciaIncompativel
    {
        Associacao associacao_temp = pesquisar(numAssociacao);

        if (associacao_temp == null)throw new AssociacaoNaoExistente("Associação não existente");

        long data_associacao = -1;

        for (Associado associado : associacao_temp.associados)
        {
            if (associado.num_positivo == codigoAssociado)
            {
                data_associacao = associado.dataAssociacao;
            }
        }

        if (data_associacao == -1)throw new AssociadoNaoExistente("Associação não existente");

        if (data_associacao < dataReuniao)throw new FrequenciaIncompativel("Associação não existente");

        boolean ok = false;

        for (Reuniao reuniao : associacao_temp.reunioes)
        {
            if (reuniao.data == dataReuniao)
            {
                ok = true;

                for (Integer cod : reuniao.frequencia)
                {
                    if (cod == codigoAssociado)
                    {
                        throw new FrequenciaJaRegistrada("Frequência já registrada");
                    }
                }
                
                reuniao.frequencia.add(codigoAssociado);
            }
        }
        if(!ok) throw new FrequenciaIncompativel("Frequemcia incompativel");

    }

    public void registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, long data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido
    {
        Associacao associacao_temp = pesquisar(numAssociacao);

        if (associacao_temp == null)throw new AssociacaoNaoExistente("Associação não existente");

        Associado associado_temp = null;
        long data_associacao = -1;

        for (Associado associado : associacao_temp.associados)
        {
            if (associado.num_positivo == numAssociado)
            {
                associado_temp = associado;
                data_associacao = associado.dataAssociacao;
            }
        }

        if (data_associacao == -1)throw new AssociadoNaoExistente("Associação não existente");

        Conta conta = null;

        for (Conta cont : associado_temp.contas)
        {
            if (cont.taxa.nome == taxa && cont.taxa.vigencia == vigencia)
            {
                conta = cont;
            }
        }

        if (taxa.isBlank() || taxa == null || vigencia < 0)throw new ValorInvalido("Valor Inválido");

        Taxa taxa_temp = null;

        for (Taxa tax : associacao_temp.taxas)
        {
            if (tax.nome.equals(taxa) && tax.vigencia == vigencia)
            {
                taxa_temp = tax;
            }
        }

        if (taxa_temp == null) throw new TaxaNaoExistente("taxa nao existente");
        if(associado_temp instanceof AssociadoRemido && taxa_temp.administrativo && ((AssociadoRemido)associado_temp).remissao<=(data))throw new AssociadoJaRemido("Associado já remido");
        if (conta == null) throw new ValorInvalido("Valor Inválido");

        double parcela = taxa_temp.valorAno / taxa_temp.parcelas;
        double restante = taxa_temp.valorAno - conta.valor;
    
        if((valor < parcela && restante > parcela)  || valor > taxa_temp.valorAno)throw new ValorInvalido("Valor inválido");
    
        conta.valor += valor;
    }

    public double somarPagamentoDeAssociado (int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, long inicio, long fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente
    {
        Associacao associacao_temp = pesquisar(numAssociacao);

        if (associacao_temp == null) throw new AssociacaoNaoExistente("Associação não existente");

        Associado associado_temp = null;

        for (Associado associado : associacao_temp.associados)
        {
            if (associado.num_positivo == numAssociado)
            {
                associado_temp = associado;
            }
        }

        if (associado_temp == null) throw new AssociadoNaoExistente("Associação não existente");

        Taxa taxa_temp = null;

        for (Taxa taxa : associacao_temp.taxas)
        {
            if (taxa.nome.equals(nomeTaxa) && taxa.vigencia == vigencia)
            {
                taxa_temp = taxa;
            }
        }

        double valor = 0;
        
        if (taxa_temp == null) throw new TaxaNaoExistente("taxa nao existente");

        for (Conta conta : associado_temp.contas)
        {
            if (conta.taxa.equals(taxa_temp))
            {
                valor += conta.valor;
            }
        }

        return valor;
    }
    
    public double calcularTotalDeTaxas (int numAssociacao, int vigencia) throws AssociacaoNaoExistente, TaxaNaoExistente
    {
        Associacao associacao_temp = pesquisar(numAssociacao);

        if (associacao_temp == null)
        {
            throw new AssociacaoNaoExistente("Associação não existente");
        }

        double total = 0;

        for (Taxa taxa : associacao_temp.taxas)
        {
            if (taxa.vigencia == vigencia)
            {
                total += taxa.valorAno;
            }
        }

        if (total == 0)
        {
            throw new TaxaNaoExistente("Taxa não existente");
        }

        return total;
    }

    public void adicionar(Associacao a) throws AssociacaoJaExistente, ValorInvalido
    {
        if (a == null) throw new ValorInvalido("Valor inválido");
        if (a.nome == null)throw new ValorInvalido("Valor inválido");
        if (a.num < 0 || a.nome.isBlank())throw new ValorInvalido("Valor inválido");

        Associacao associacao = pesquisar(a.num);

        if (associacao == null)
        {
            associacoes.add(a);
        }
        else
        {
            throw new AssociacaoJaExistente("Associação já existente");
        }
    }

    public void adicionar(int associacao, Associado a) throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido
    {
        if (associacao < 0 || a.nome.isBlank() || a.nome == null || a == null)
        {
            throw new ValorInvalido("Valor inválido");
        }

        Associacao associacao_temp = pesquisar(associacao);

        if (associacao_temp == null)throw new AssociacaoNaoExistente("Associação não existente");
        
        for (Associado associado : associacao_temp.associados)
        {
            if (associado.num_positivo == a.num_positivo)
            {
                throw new AssociadoJaExistente("Associado já existente");
            }
        }

        for (Taxa taxa : associacao_temp.taxas)
        {
            Conta conta = new Conta(taxa);
            a.contas.add(conta);
        }
        
        associacao_temp.associados.add(a);
    }

    public Associacao pesquisar(int cod)
    {
        for (Associacao associacao : associacoes)
        {
            if (associacao.num == cod)
            {
                return associacao;
            }
        }

        return null;
    }

    public void adicionar(int associacao, Reuniao r) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido
    {
        if (r == null)throw new ValorInvalido("Valor inválido");
        if (r.ata == null)throw new ValorInvalido("Valor inválido");
        if (associacao < 0 || r.ata.isBlank() || r.data < 0)throw new ValorInvalido("Valor inválido");

        Associacao associacao_temp = pesquisar(associacao);

        if (associacao_temp == null)
        {
            throw new AssociacaoNaoExistente("Associação não existente");
        }

        for (Reuniao reuniao : associacao_temp.reunioes)
        {
            if (reuniao.equals(r))
            {
                throw new ReuniaoJaExistente("Reunião já existente");
            }
        }

        associacao_temp.reunioes.add(r);
    }

    public void adicionar(int associacao, Taxa t) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido
    {
        if (associacao < 0 || t == null || t.nome.isBlank() || t.nome == null || t.parcelas < 0 || t.valorAno < 0 || t.vigencia < 0)
        {
            throw new ValorInvalido("Valor inválido");
        }

        Associacao associacao_temp = pesquisar(associacao);

        if (associacao_temp == null)
        {
            throw new AssociacaoNaoExistente("Associação não existente");
        }
        
        for (Taxa taxa : associacao_temp.taxas)
        {
            if (taxa.equals(t))
            {
                throw new TaxaJaExistente("Taxa já existente");
            }
        }

        associacao_temp.taxas.add(t);
        
        for (Associado associado : associacao_temp.associados)
        {
            boolean ok = false;

            for (Conta conta : associado.contas)
            {
                if (conta.taxa.equals(t))
                {
                    ok = true;
                }
            }

            if (!ok) associado.contas.add(new Conta(t));
        }
    }
}