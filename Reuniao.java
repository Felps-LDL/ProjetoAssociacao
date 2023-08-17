package felipeLagesDeLima.associacao;

import java.util.ArrayList;

public class Reuniao 
{
    public long data;
    public String ata;
    public ArrayList<Integer> frequencia;
    
    public Reuniao(long data, String ata) throws ValorInvalido
    {
        if (data < 0 || ata == null || ata.isBlank())
        {
            throw new ValorInvalido("Valor inválido");
        }
        this.data = data;
        this.ata = ata;
        frequencia = new ArrayList<Integer>();
    }
}
