/**
 *
 * @author Hooman
 */

public class SubString {
    public static void main(String[] args) {
        String str1 = "absdeoopmuydehabsdeoohoomhoomhoomhoompmuydewewhskasaasasasllksasalalsasalsakwewewsakdsadjsadsadsadadwnndasdsnadsandxnsadnsansadsandqhoomanhabsdeoopmuydehabsdeoopmuydehhhooman1asashawewhesdshdsdhsdhsdhewdshdshdshdshdshdshdhsdhsdhsdhshdshdhewewehwewhedshcshdshdshdsdhsd";
        String str2 = "hooman9";
        for (int i = 0; i < 5; i++) {
            str1 = str1 + str1;
        }
        str1 = str1 + "hhhhooman9";
        int index = 0;
        long time = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            index = str1.indexOf(str2);
        }
        time = (System.nanoTime() - time) / 100000;
        System.out.println(" Found index strings = " + index);
        System.out.println(" Time java String search = " + time);

        // char [] str="absdeoopmuydeh".toCharArray();
        // char [] pattern="deh".toCharArray();
        char[] str = str1.toCharArray();
        char[] pattern = str2.toCharArray();
        time = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            index = String.valueOf(str).indexOf(String.valueOf(pattern));
        }
        time = (System.nanoTime() - time) / 100000;
        System.out.println(" Found index char arrays = " + index);
        System.out.println(" Time java char array search = " + time);

        time = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            index = findPattern(str, pattern);
        }
        time = (System.nanoTime() - time) / 100000;
        System.out.println(" Found index char arrays with my method = " + index);
        System.out.println(" Time my method char array search = " + time);
    }

    public static int findPattern(char[] str, char[] pattern) {
        for (int i = 0; i <= str.length - pattern.length; i++) {
            int j;
            for (j = 0; j < pattern.length; j++) {
                if (str[i + j] != pattern[j]) {
                    break;
                }
            }
            if (j == pattern.length) {
                return i;
            }
        }
        return -1;
    }
}
