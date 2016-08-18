package be.vdab.voertuigen.div;

public enum DIV {
    INSTANCE;
    int teller=1;
    
    protected Nummerplaat getNummerplaat() {
        StringBuilder sb = new StringBuilder();
        sb.append("AAA");
        if (teller < 100)
            sb.append(0);
        if (teller < 10)
            sb.append(0);
        sb.append(teller);
        
        teller++;
        if (teller == 1000)
            teller = 1;
                    
        return new Nummerplaat(sb.toString());
    }
}
