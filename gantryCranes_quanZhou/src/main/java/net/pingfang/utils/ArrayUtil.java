package net.pingfang.utils;

public class ArrayUtil {

	/**
	 * 数组插入排序
	 * @param arr
	 */
	public static void sortArray(int[] arr) {
		if(null !=arr && arr.length >0) {
			//插入排序
	        for (int i = 1; i < arr.length; i++) {
	            //外层循环，从第二个开始比较
	            for (int j = i; j > 0; j--) {
	                //内存循环，与前面排好序的数据比较，如果后面的数据小于前面的则交换
	                if (arr[j] < arr[j - 1]) {
	                    int temp = arr[j - 1];
	                    arr[j - 1] = arr[j];
	                    arr[j] = temp;
	                } else {
	                    //如果不小于，说明插入完毕，退出内层循环
	                    break;
	                }
	            }
	        }
		}		
	}
	
}
