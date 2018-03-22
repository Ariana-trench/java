public class KMP{
    public static int[] getNext(String needle){
        int len = needle.length();
        int next[] = new int[len+1];
        next[0] = -1;
        int i=0, k=-1;
        while(i<len){
            if(k==-1 || needle.charAt(i)==needle.charAt(k)){
                next[++i] = ++k;
            }
            else{
                k = next[k];
            }
        }
        return next;
    }
    
    public static int kmp(String hayString, String needle){
        int next[] = getNext(needle);
        int i=0, j=0;
        while(i<hayString.length()){
            if(j == -1 || hayString.charAt(i)==needle.charAt(j)){
                i++;j++;
            }
            else{
                j = next[j];
            }
            if(j == needle.length()){
                return i-j;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        System.out.println(kmp(args[0], args[1]));    
    }
}