package class20;

import java.util.TreeSet;

/*
 * 给定一个整型数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和（较大集合的累加和一定是所有数累加和减去较小集合的累加和）
 * 为了方便起见，假设arr中没有负数，其实也可以有
 * 但是处理起来会比较麻烦，而且有没有负数都不影响算法流程的理解
 * */
public class Code03_SplitSumClosedSizeHalf {

	public static int right(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		TreeSet<Integer> ans = new TreeSet<>();
		process(arr, 0, 0, 0, ans, sum >> 1);
		return ans.last();
	}

	public static void process(int[] arr, int i, int sum, int picks, TreeSet<Integer> ans, int limit) {
		if (i == arr.length) {
			if ((arr.length & 1) == 0) {
				if (picks == (arr.length >> 1) && sum <= limit) {
					ans.add(sum);
				}
			} else {
				if ((picks == (arr.length >> 1) || picks == (arr.length >> 1) + 1) && sum <= limit) {
					ans.add(sum);
				}
			}
		} else {
			process(arr, i + 1, sum, picks, ans, limit);
			process(arr, i + 1, sum + arr[i], picks + 1, ans, limit);
		}
	}

	public static int dp(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		sum >>= 1;
		int N = arr.length;
		int M = (arr.length + 1) >> 1;
		int[][][] dp = new int[N][M + 1][sum + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= M; j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = Integer.MIN_VALUE;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int k = 0; k <= sum; k++) {
				dp[i][0][k] = 0;
			}
		}
		for (int k = 0; k <= sum; k++) {
			dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= Math.min(i + 1, M); j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = dp[i - 1][j][k];
					if (k - arr[i] >= 0) {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
					}
				}
			}
		}
		return Math.max(dp[N - 1][M][sum], dp[N - 1][N - M][sum]);
	}

	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * value);
		}
		return arr;
	}

	public static void printArray(int[] arr) {
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 50;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = randomArray(len, maxValue);
			int ans1 = right(arr);
			int ans2 = dp(arr);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}