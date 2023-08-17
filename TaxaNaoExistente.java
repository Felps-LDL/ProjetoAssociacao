package felipeLagesDeLima.associacao;

public class TaxaNaoExistente extends Exception 
{ 
    public TaxaNaoExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}