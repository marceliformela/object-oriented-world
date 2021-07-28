package classes;

public enum KierunkiRuchu
{
    LEWO(0), PRAWO(1), GORA(2), DOL(3);
    
    private int wartosc;
    
    KierunkiRuchu(int wartosc)
    {
        this.wartosc = wartosc;
    }
    
}