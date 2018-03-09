public class TestCase{
    private static void arrayTest(){
        int a[][][] = {{{1,2,3},{1},{1,2,3,4}},{{1,2}},{{1,2,3,4},{1,2}}};
        // System.out.println(a.length);
        // System.out.println(a[0].length);
        // System.out.println(a[0][0].length);
        for(int i=0; i<a.length; i++){
            for(int j=0; j<a[i].length; j++){
                for(int k=0; k<a[i][j].length;k++){
                    System.out.printf("%d ", a[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
        }

        int b[] = new int[8];
        b[0] = 1;

        int c[][] = new int[3][];
        c[0] = new int[8];
        c[1] = new int[5];
        c[2] = new int[2];

        int d[][] = new int[3][3];  // default set 0
        // for(int i=0;i<d.length; i++){
        //     for(int j=0; j<d[i].length; j++){
        //         System.out.print(d[i][j]+" ");
        //     }
        //     System.out.println();
        // }
    }
    public static void main(String[] args) {
        arrayTest();
    }
}