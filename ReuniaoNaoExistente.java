package felipeLagesDeLima.associacao;

public class ReuniaoNaoExistente extends Exception 
{ 
    public ReuniaoNaoExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}