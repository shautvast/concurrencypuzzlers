package chapter1.cretans;

/**
 * Epimenides the Cretan says, 'All the Cretans are liars'
 *
 * https://en.wikipedia.org/wiki/Epimenides_paradox
 *
 * or This sentence is false.
 */
public class Cretan {

    public double value;

    public Cretan(double value) {
        this.value = value;
    }

    public void test() {
        if (value != value) { // paradoxical?
            /* can we reach this line ?*/
            System.out.println("WTF?");
        }
    }
}


