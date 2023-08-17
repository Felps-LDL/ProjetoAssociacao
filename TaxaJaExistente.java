package felipeLagesDeLima.associacao;

public class TaxaJaExistente extends Exception 
{ 
    public TaxaJaExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}