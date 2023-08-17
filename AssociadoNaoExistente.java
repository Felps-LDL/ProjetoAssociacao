package felipeLagesDeLima.associacao;

public class AssociadoNaoExistente extends Exception 
{ 
    public AssociadoNaoExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}