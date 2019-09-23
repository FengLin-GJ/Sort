import java.util.Arrays;
import java.util.Date;

public class Sort {
    public static void main(String[] args) throws InterruptedException {
        bubbleSort(args(150000));
        insertionSort(args(150000));
        shellSort(args(150000));
        quickSort(args(150000));
        mergeSort(args(150000));
        radixSort(args(150000));
        Selection(args(150000));
    }

    public static int[] args(int num){
        int [] args=new int[num];
        for (int i = 0; i < num; i++) {
            args[i]=(int)(Math.random()*num+1);
        }
        return args;
    }

    //基数排序(有问题)
    public static void radixSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        radixSort(args,10);
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("基数排序运行时间：" + (endTime - startTime)/1000 + "s");
    }
    //d表示每一位数字的范围（这里是10进制数，有0~9一共10种情况）
    public static void radixSort(int args[],int d) {
        //n用来表示当前排序的是第几位
        int n = 1;
        //hasNum用来表示数组中是否有至少一个数字存在第n位
        boolean hasNum = false;
        //二维数组temp用来保存当前排序的数字
        //第一维d表示一共有d个桶
        //第二维a.length表示每个桶最多可能存放a.length个数字
        int[][] temp = new int[d][args.length];
        int[] order = new int[d];
        while (true) {
            //判断是否所有元素均无比更高位，因为第一遍一定要先排序一次，所以有n!=1的判断
            if (n != 1 && !hasNum) {
                break;
            }
            hasNum = false;
            //遍历要排序的数组，将其存入temp数组中（按照第n位上的数字将数字放入桶中）
            for (int i = 0; i < args.length; i++) {
                int x = args[i] / (n * 10);
                if (x != 0) {
                    hasNum = true;
                }
                int lsd = (x % 10);
                temp[lsd][order[lsd]] = args[i];
                order[lsd]++;
            }
            //k用来将排序好的temp数组存入data数组（将桶中的数字倒出）
            int k = 0;
            for (int i = 0; i < d; i++) {
                if (order[i] != 0) {
                    for (int j = 0; j < order[i]; j++) {
                        args[k] = temp[i][j];
                        k++;
                    }
                }
                order[i] = 0;
            }
            n++;
        }
    }

    //归并排序
    public static void mergeSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        mergeSort(args,0,args.length-1);
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("归并排序运行时间：" + (endTime - startTime)/1000 + "s");
    }
    public static void mergeSort(int args[], int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSort(args, low, mid);
            // 右边
            mergeSort(args, mid + 1, high);
            // 左右归并
            merge(args, low, mid, high);
        }
    }
    public static void merge(int args[], int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (args[i] < args[j]) {
                temp[k++] = args[i++];
            } else {
                temp[k++] = args[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = args[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = args[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            args[k2 + low] = temp[k2];
        }
    }

    //快速排序
    private static void quickSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        quickSort(args,0,args.length-1);
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("快速排序运行时间：" + (endTime - startTime)/1000 + "s");
    }
    private static void quickSort(int args[], int begin, int end) {
        int tbegin = begin, tend = end;
        // 将第一个值作为快排序的分界值
        int pivot = args[begin];
        while (tbegin < tend) {
            // 如果两个游标没有交集，或者后面一直大于或等于分界值就一直向前移动
            while (tbegin < tend && args[tend] >= pivot) {
                --tend;
            }
            args[tbegin] = args[tend];
            // 如果两个游标没有交集，或者前面是小于或等于分界值，就一直向后头移动
            while (tbegin < tend && args[tbegin] <= pivot) {
                ++tbegin;
            }
            args[tend] = args[tbegin];

        }
        // 将临界值赋值给游标的交集的地方
        args[tbegin] = pivot;
        if (begin < tend) {
            // 递归排序游标的左边
            quickSort(args, begin, tend - 1);
        }
        if (tbegin < end) {
            // 递归排序游标的右边
            quickSort(args, tbegin + 1, end);
        }
    }

    //希尔排序
    public static void shellSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        // 计算出最大的h值
        int h = 1;
        while (h <= args.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int i = h; i < args.length; i += h) {
                if (args[i] < args[i - h]) {
                    int temp = args[i];
                    int j = i - h;
                    while (j >= 0 && args[j] > temp) {
                        args[j + h] = args[j];
                        j -= h;
                    }
                    args[j + h] = temp;
                }
            }
            // 计算出下一个h值
            h = (h - 1) / 3;
        }
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("希尔排序运行时间：" + (endTime - startTime)/1000 + "s");
    }

    //直接插入排序
    public static void insertionSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        for (int i = 1; i < args.length; i++) {
            for (int j = i; j > 0; j--) {
                if (args[j] < args[j - 1]) {
                    int tmp = args[j - 1];
                    args[j - 1] = args[j];
                    args[j] = tmp;
                }
            }
        }
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("插入排序运行时间：" + (endTime - startTime)/1000 + "s");
    }

    //冒泡排序
    public static void bubbleSort(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        for (int i=0;i<args.length-1;i++){
            for(int j=0;j<args.length-i-1;j++){
                if(args[j]>args[j+1]){
                    int temp=args[j];
                    args[j]=args[j+1];
                    args[j+1]=temp;
                }
            }
        }
        //Thread.sleep(1000);
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("冒泡排序运行时间：" + (endTime - startTime)/1000 + "s");
    }

    //直接选择排序
    public static void Selection(int args[]){
        double startTime = System.currentTimeMillis();  //获取开始时间
        for (int i = 0; i < args.length-1; i++) {
            int temp=i;
            for(int j=i+1;j<args.length;j++){
                if(args[temp]>args[j]){
                    temp=j;
                }
            }
            if(temp!=i){
                int k=args[i];
                args[i]=args[temp];
                args[temp]=k;
            }
        }
        double endTime = System.currentTimeMillis();  //获取结束时间
        /*for(int k=0;k<args.length;k++){
            System.out.println(args[k]);
        }*/
        System.out.println("直接选择排序运行时间：" + (endTime - startTime)/1000 + "s");
    }
}
