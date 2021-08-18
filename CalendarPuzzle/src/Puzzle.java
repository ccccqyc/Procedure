import java.util.ArrayList;

public class Puzzle {
    public static void main(String[] args) {
        //初始化
        Puzzle t = new Puzzle();
        /*
        Month - M
        Date - D
        Week - W
         */
        int M = 7;
        int D = 15;
        int W = 4;
        int[][] plate = t.initPlateData(M, D, W);
        ArrayList[] sliceList = t.initSliceData();
        //初始化结束

    }

    /**
     * 底盘
     *
     * @param M Month
     * @param D Day
     * @param W Week
     * @return 初始化的盘子
     */
    public int[][] initPlateData(int M, int D, int W) {
        int[][] plate = new int[9][6];
        int row = 0;
        int column = 0;
        /**
         * 月
         */
        row = M / 6;
        column = M % 6 - 1;
        plate[row][column] = 10;
        /**
         * 日期
         */
        row = D / 6 + 1;
        column = D % 6 - 1;
        plate[row][column] = 10;
        /**
         * 星期
         * 星期 1 - 6 日
         * 1-6 7
         *
         */
        if (W <= 3) {
            row = 7;
            column = W + 2;
        } else {
            row = 8;
            column = W - 2;
        }
        plate[row][column] = 10;

        return plate;
    }

    /**
     * 一共9块小拼版
     *
     * @return
     */
    public ArrayList[] initSliceData() {


        /*
        height x width
        3 X 4

        # # # #
          #
          #
         */
        final int[][] A1 = {
                {1, 1, 1, 1},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
        /*
        3 X 3
              #
          # # #
          #   #
         */
        final int[][] A2 = {
                {0, 0, 2},
                {2, 2, 2},
                {2, 0, 2}
        };
        /*
        5 X 2
            #
            #
            #
         #  #
            #
         */
        final int[][] A3 = {
                {0, 3},
                {0, 3},
                {0, 3},
                {3, 3},
                {0, 3}

        };
        /*
        4 x 3
        #
        # # #
          #
          #
         */
        final int[][] A4 = {
                {4, 0, 0},
                {4, 4, 4},
                {0, 4, 0},
                {0, 4, 0}
        };
        /*
        4 x 2
        #
        #
        # #
          #
         */
        final int[][] A5 = {
                {5, 0},
                {5, 0},
                {5, 5},
                {0, 5}
        };
        /*
        5 x 2
        #
        #
        #
        #
        # #
         */
        final int[][] A6 = {
                {6, 0},
                {6, 0},
                {6, 0},
                {6, 0},
                {6, 6},
        };
        /*
        2 x 3
        # #
        # # #
         */
        final int[][] A7 = {
                {7, 7, 0},
                {7, 7, 7}
        };
        /*
        2 x 4
        #
        # # # #
         */
        final int[][] A8 = {
                {8, 0, 0, 0},
                {8, 8, 8, 8}
        };
        /*
        4 x 3
            #
            #
            #
        # # #
         */
        final int[][] A9 = {
                {0, 0, 9},
                {0, 0, 9},
                {0, 0, 9},
                {9, 9, 9}
        };
        //所有拼板 可能的所有形态
        ArrayList[] allList = new ArrayList[9];
        //临时.单块拼版可能的形态
        ArrayList<int[][]> temp = new ArrayList<>();
        ArrayList<int[][]> temp2 = new ArrayList<>();
        //临时 拼版
        int[][] tempS;
        temp2.add(A1);
        temp2.add(A2);
        temp2.add(A3);
        temp2.add(A4);
        temp2.add(A5);
        temp2.add(A6);
        temp2.add(A7);
        temp2.add(A8);
        temp2.add(A9);
        // 共9块 拼板
        for (int k = 0; k < 9; k++) {
            temp.add(temp2.get(k));
            //旋转 得四组
            for (int i = 0; i < 3; i++) {
                tempS = transpose(temp.get(i));
                if (!isEqual(temp, tempS)) {
                    temp.add(tempS);
                }
            }
            // 镜像 得四组
            for (int i = 0; i < 4; i++) {
                tempS = mirrorInversion(temp.get(i));
                if (!isEqual(temp, tempS)) {
                    temp.add(tempS);
                }
            }
            allList[k] = (ArrayList) temp.clone(); // 坑点 深拷贝
            temp.clear();
        }
        return allList;
    }

    /**
     * @param arr 旋转 数组
     *            1,②,③    ③,6    6,5,4    4,1
     *            4,5,6  →  ②,5 →  ③,②,1 → 5,②
     *            *****     1,4              6,③
     */
    public int[][] transpose(int[][] arr) {

        int width = arr[0].length;
        int height = arr.length;

        int[][] newArr = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newArr[Math.abs(width - j - 1)][i] = arr[i][j]; //旋转核心
            }
        }
        return newArr;
    }

    /**
     * @param arr 镜像 数组
     *            4,5,6        1,4     ③,②,1   4,1
     *            1,②,③       ②,5     6,5,4    5,②
     *            *****        ③,6              6,③
     */
    public int[][] mirrorInversion(int[][] arr) {
        int width = arr[0].length;
        int height = arr.length;
        int[][] newArr = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newArr[height - 1 - i][j] = arr[i][j];
            }
        }

        return newArr;

    }

    /**
     * 拼板有木有重复?
     *
     * @param arrlist
     * @param arr
     * @return 不重复 false
     */
    public boolean isEqual(ArrayList<int[][]> arrlist, int[][] arr) {
        boolean judge = true;
        c:
        for (int[][] al : arrlist) {
            if (al.length != arr.length) {
                judge = false;
                break c;
            }
            for (int i = 0; i < al.length; i++) {
                for (int j = 0; j < al[0].length; j++) {
                    if (al[i][j] != arr[i][j]) {
                        judge = false;
                        break c;
                    }
                }
            }
        }
        return judge;
    }
}