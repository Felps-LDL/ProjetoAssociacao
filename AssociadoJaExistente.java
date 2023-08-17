package felipeLagesDeLima.associacao;

public class AssociadoJaExistente extends Exception 
{ 
    public AssociadoJaExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}