package felipeLagesDeLima.associacao;

public class ReuniaoJaExistente extends Exception 
{ 
    public ReuniaoJaExistente(String errorMessage) 
    {
        super(errorMessage);
    }
}