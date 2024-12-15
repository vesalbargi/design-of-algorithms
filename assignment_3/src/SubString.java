import java.text.DecimalFormat;

/**
 *
 * @author Hooman
 */

 public class SubString {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        String str1 = "absdeoopmuydehabsdeoohoomhoomhoomhoompmuydewewhskasaasasasllksasalalsasalsakwewewsakdsadjsadsadsadadwnndasdsnadsandxnsadnsansadsandqhoomanhabsdeoopmuydehabsdeoopmuydehhhooman1asashawewhesdshdsdhsdhsdhewdshdshdshdshdshdshdhsdhsdhsdhshdshdhewewehwewhedshcshdshdshdsdhsd";
        String str2 = "hooman9hooman9hooman9hooman9";

        // -----------------------------------------------------------------
        // Case I
        // case 1: 12
        // for (int i = 0; i < 12; i++) {
        // str1 = str1 + str1;
        // str2 = str2 + str2;
        // // str1 = str1 + str2; // to make worse case and duplicate the pattern
        // }
        // str2 = str2 + "kl"; // unique pattern
        // str1 = str1 + str2; // unique pattern at the end of the string

        // --------------------------------------------------------------------
        // Case II
        // Case 2: 8
        // for (int i = 0; i < 8; i++) {
        // str1 = str1 + str1;
        // str2 = str2 + str2;
        // str1 = str1 + str2; // to make worse case and duplicate the pattern
        // }
        // str2 = str2 + "kl"; // unique pattern
        // str1 = str1 + str2; // unique pattern at the end of the string

        // ---------------------------------------------------------------
        // Case III
        // Case 3: 8
        for (int i = 0; i < 8; i++) {
            str1 = str1 + str1;
            str2 = str2 + str2;
            str1 = str1 + str2; // to make worse case and duplicate the pattern
        }
        String str3 = "1234567890"; // new characters
        for (int i = 0; i < 10; i++) {
            str3 = str3 + str3;
        }
        str2 = str2 + str3;
        str1 = str1 + str2 + str1;

        System.out.println(" String Length: " + decimalFormat.format(str1.length()) + " characters ");
        System.out.println(" Pattern Length: " + decimalFormat.format(str2.length()) + " characters \n\n");
        int index = 0;
        long time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = str1.indexOf(str2);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Java Found index strings = " +
                decimalFormat.format(index));
        System.out.println(" Time java String search = " + decimalFormat.format(time) + " ns \n\n");

        // char[] str = "absdeoopmuydeh".toCharArray();
        // char[] pattern = "deh".toCharArray();
        char[] str = str1.toCharArray();
        char[] pattern = str2.toCharArray();

        // time = System.nanoTime();
        // for (int i = 0; i < 10000; i++) {
        // index = String.valueOf(str).indexOf(String.valueOf(pattern));
        // }
        // time = (System.nanoTime() - time) / 10000;
        // System.out.println(" Java Found index char arrays = " +
        // decimalFormat.format(index));
        // System.out.println(" Time java char array search = " +
        // decimalFormat.format(time) + " ns \n\n");

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = findPattern(str, pattern);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Found index with Naïve method = " +
                decimalFormat.format(index));
        System.out.println(" Time Naïve method = " + decimalFormat.format(time) + " ns");
        System.out.println(" Naïve method Number of search steps: " +
                decimalFormat.format(counter) + " times \n\n");

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = findPattern1(str, pattern);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Found index with KMP method = " +
                decimalFormat.format(index));
        System.out.println(" Time KMP method = " + decimalFormat.format(time) + " ns");
        System.out.println(" KMP method Number of search steps: " +
                decimalFormat.format(counter) + " times \n\n");

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = findPattern2(str, pattern);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Found index with Boyer-Moore method = " +
                decimalFormat.format(index));
        System.out.println(" Time Boyer-Moore method = " + decimalFormat.format(time) + " ns\n\n");

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = findPattern3(str, pattern);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Found index with Z Algorithm method = " +
                decimalFormat.format(index));
        System.out.println(" Time Z Algorithm method = " + decimalFormat.format(time) + " ns\n\n");

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            index = findPattern4(str, pattern);
        }
        time = (System.nanoTime() - time) / 10000;
        System.out.println(" Found index with Rabin-Karp method = " +
                decimalFormat.format(index));
        System.out.println(" Time Rabin-Karp method = " + decimalFormat.format(time) + " ns\n\n");
    }

    public static int counter = 0;

    // Naïve
    public static int findPattern(char[] str, char[] pattern) {
        counter = 0;
        for (int i = 0; i <= str.length - pattern.length; i++) {
            boolean found = true;
            for (int j = 0; j < pattern.length; j++) {
                counter++;
                if (str[i + j] != pattern[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        return -1;
    }

    // KMP
    public static int findPattern1(char[] str, char[] pattern) {
        counter = 0;
        int[] lps = computeLPSArray(pattern);
        int i = 0, j = 0;
        while (i < str.length) {
            counter++;
            if (str[i] == pattern[j]) {
                i++;
                j++;
            }
            if (j == pattern.length) {
                return i - j;
            } else if (i < str.length && str[i] != pattern[j]) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    // Boyer-Moore
    public static int findPattern2(char[] str, char[] pattern) {
        int[] badChar = computeBadCharTable(pattern);
        int n = str.length;
        int m = pattern.length;
        int shift = 0;
        while (shift <= n - m) {
            int j = m - 1;
            while (j >= 0 && pattern[j] == str[shift + j]) {
                j--;
            }
            if (j < 0) {
                return shift;
            } else {
                shift += Math.max(1, j - badChar[str[shift + j]]);
            }
        }
        return -1;
    }

    // Z Algorithm
    public static int findPattern3(char[] str, char[] pattern) {
        String concat = new String(pattern) + "$" + new String(str);
        int n = concat.length();
        int[] Z = new int[n];
        computeZArray(concat.toCharArray(), Z);
        for (int i = 0; i < n; i++) {
            if (Z[i] == pattern.length) {
                return i - pattern.length - 1;
            }
        }
        return -1;
    }

    // Rabin-Karp
    public static int findPattern4(char[] str, char[] pattern) {
        int n = str.length;
        int m = pattern.length;
        if (m > n)
            return -1;
        int prime = 101;
        long patternHash = 0, strHash = 0, h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * 256) % prime;
        }
        for (int i = 0; i < m; i++) {
            patternHash = (256 * patternHash + pattern[i]) % prime;
            strHash = (256 * strHash + str[i]) % prime;
        }
        for (int i = 0; i <= n - m; i++) {
            if (patternHash == strHash) {
                int j;
                for (j = 0; j < m; j++) {
                    if (str[i + j] != pattern[j]) {
                        break;
                    }
                }
                if (j == m) {
                    return i;
                }
            }
            if (i < n - m) {
                strHash = (256 * (strHash - str[i] * h) + str[i + m]) % prime;
                if (strHash < 0) {
                    strHash += prime;
                }
            }
        }
        return -1;
    }

    private static int[] computeLPSArray(char[] pattern) {
        int[] lps = new int[pattern.length];
        int len = 0;
        int i = 1;
        while (i < pattern.length) {
            if (pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    private static int[] computeBadCharTable(char[] pattern) {
        int[] table = new int[256];
        for (int i = 0; i < 256; i++) {
            table[i] = -1;
        }
        for (int i = 0; i < pattern.length; i++) {
            table[pattern[i]] = i;
        }
        return table;
    }

    private static void computeZArray(char[] str, int[] Z) {
        int n = str.length;
        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i > R) {
                L = R = i;
                while (R < n && str[R] == str[R - L]) {
                    R++;
                }
                Z[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if (Z[k] < R - i + 1) {
                    Z[i] = Z[k];
                } else {
                    L = i;
                    while (R < n && str[R] == str[R - L]) {
                        R++;
                    }
                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }
}
