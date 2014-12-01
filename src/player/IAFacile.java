package player;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.Case;
import structure.Position;

public class IAFacile extends PlayerIA {

    public IAFacile(Case c) {
        super(c);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if(!isPlayerTurn(obj)) {
            return;
        }
        casesAvailable(null);
        Random rand = new Random();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(IAFacile.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged();
        notifyObservers(new Position(rand.nextInt(7 - 0 + 1) + 0, rand.nextInt(7 - 0 + 1) + 0));
    }
    
}
