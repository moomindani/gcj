import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

/**
 * Google Code Jam 解答テンプレート
 * @author moomindani
 *
 */
public class Solution {
	/**
	 * 順列のすべての組み合わせを算出するクラス。
	 * C++のnext_permutationに相当。
	 * @see http://topcoder.g.hatena.ne.jp/eller/20090831/1251723649
	 */
	public static class Permutation implements Iterable<int[]> {
		private final int size;

		/**
		 * <p>順列を表すクラスのコンストラクタ。反復子が返す配列の要素数を指定する。
		 * @param size 順列の要素数（10程度が妥当な最大値）
		 * @throws IllegalArgumentException 引数（順列の要素数）が0以下の場合
		 */
		public Permutation(int size) {
			if (size <= 0) throw new IllegalArgumentException();
			this.size = size;
		}

		public Iterator<int[]> iterator() {
			return new Iter(size);
		}

		private class Iter implements Iterator<int[]> {
			private final int[] source;
			private boolean isFirst = true;

			private Iter(int size) {
				source = new int[size];
				for(int i = 0; i < size; ++i) {
					source[i] = i;
				}
			}

			/**
			 * <p>反復子がまだ順列を返しうるか調べる。
			 * 本メソッドは{@link Iter#next()}呼び出し前に必ず1回ずつ実行する必要がある。
			 * @return 順列が存在する場合はtrue
			 */
			public boolean hasNext() {
				if (isFirst) {
					isFirst = false;
					return true;
				}

				int n = source.length;
				for (int i = n - 1; i > 0; i--) {
					if (source[i - 1] < source[i]) {
						int j = n;
						while (source[i - 1] >= source[--j]);
						swap(source, i - 1, j);
						reverse(source, i, n);
						return true;
					}
				}
				reverse(source, 0, n);
				return false;
			}

			/**
			 * <p>次の順列を表すint型配列を返す。
			 * <p>このメソッドが返す参照は常に同じ配列に対する参照である。
			 * このため配列の要素を変更したり次回の{@link Iter#next()}呼び出し後も参照する場合は、
			 * クラスの呼び出し側が配列のクローンを生成して利用する必要がある。
			 * @return 順列を表すint型配列
			 */
			public int[] next() {
				return source;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

			private void swap(int[] is, int i, int j) {
				int t = is[i];
				is[i] = is[j];
				is[j] = t;
			}

			private void reverse(int[] is, int s, int t) {
				while (s < --t) swap(is, s++, t);
			}
		}
	}

	/**
	 * 数論クラス。
	 */
	public static class NumberTheory {
		/**
		 * ユークリッドの互除法で最大公約数を求める
		 */
		public static int gcd(int a, int b){
			if(b==0) return a;
			return gcd(b, a%b);
		}
		
		/**
		 * エラトステネスの篩でn以下の素数の数を求める
		 */
		static final int MAX = 10000;
		static int[] prime = new int[MAX];
		static boolean[] isPrime = new boolean[MAX];
		public static int sieve(int n){
			int p=0;
			for(int i=0; i<=n; i++){
				isPrime[i] = true;
			}
			isPrime[0] = false;
			isPrime[1] = false;
			for(int i=2; i<=n; i++){
				if(isPrime[i]){
					prime[p++] = i;
					for(int j=2*i; j<=n; j+=i){
						isPrime[j] = false;
					}
				}
			}
			return p;
		}
		
		/**
		 * 繰り返し二乗法でべき乗を計算する
		 * @return x^nをmodで割った余り
		 */
		public static long modPow(long x, long n, long mod){
			if(n==0) return 1;
			long res = modPow(x * x % mod, n / 2, mod);
			if((n & 1) != 0){
				res = res * x % mod;
			}
			return res;
		}
	}
	
	/**
	 * 便利処理を集めたサンプルクラス。
	 */
	public static class Sample {
		/**
		 * 順列組み合わせ（NextPermutation）を使うサンプル。
		 */
		public static void nextPermutation(){
			for(int[] permutation : new Permutation(5)) {
				System.out.println(Arrays.toString(permutation));
			}
		}
		
		/**
		 * 任意のオブジェクトのコレクションをソートするサンプル。
		 */
		public static void sortCollection(){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(new Integer(5));
			list.add(new Integer(1));
			list.add(new Integer(8));
			
			Collections.sort(list, new Comparator<Integer>(){
			      public int compare(Integer t1, Integer t2) {
			          return t1 - t2;	// 昇順ソート
			          //return t2 - t1;	// 降順ソート
			        }
			      });
			
			for(Integer i : list){
				System.out.println(i);
			}
		}
		
		/**
		 * 小数点以下n桁まで出力するサンプル
		 */
		public static void printDoubleWithDigit(){
			double num = 1.23456789012345;
			System.out.println(String.format("%.2f", num));
			System.out.println(String.format("%.3f", num));
			System.out.println(String.format("%.4f", num));
			System.out.println(String.format("%.5f", num));
		}
		
		/**
		 * 基本的な数学関数のサンプル
		 */
		public static void printBasicCalc(){
			double num = 1.456;
		    System.out.println("abs : " + Math.abs(num));		// 絶対値
		    System.out.println("ceil : " + Math.ceil(num));		// 切り上げ
		    System.out.println("floor : " + Math.floor(num));	// 切り捨て
		    System.out.println("round : " + Math.round(num));	// 四捨五入
		    System.out.println("rint : " + Math.rint(num));		// 最も近い整数値
		    System.out.println("pow : " + Math.pow(num, num));	// 累乗
		}
		
		/**
		 * 桁数を計算するサンプル
		 */
		public static void printDigit(){
			int num = 123456;

			// パターンA
			System.out.println((int)Math.log10(num) + 1);
			
			// パターンB
			//System.out.println(String.valueOf(num).length());
		}
		
		
		/**
		 * 最大公約数を求めるサンプル
		 */
		public static void printGcd(){
			System.out.println(NumberTheory.gcd(12,15));
		}
		
		
		/**
		 * 素数を計算するサンプル
		 */
		public static void printSieve(){
			int n = 13;
			System.out.println("num of prime : " + NumberTheory.sieve(n));	// n以下の素数の数
			System.out.println(n + " is " + (NumberTheory.isPrime[n] ? "Prime" : "Not Prime"));	// nが素数かどうか判定
		}
		
		/**
		 * べき乗を算出するサンプル
		 */
		public static void printModPow(){
			System.out.println("modPow : " + NumberTheory.modPow(3, 50, 19));
		}
		
		/**
		 * プリミティブ型の値の範囲を出力するサンプル
		 */
		public static void printRangeOfPrimitives(){
			// Integer : -2147483648 -> 2147483647
			// Long : -9223372036854775808 -> 9223372036854775807
			// Float : 1.4E-45 -> 3.4028235E38
			// Double : 4.9E-324 -> 1.7976931348623157E308
			System.out.println("Integer : " + Integer.MIN_VALUE + " -> " + Integer.MAX_VALUE);
			System.out.println("Long : " + Long.MIN_VALUE + " -> " + Long.MAX_VALUE);
			System.out.println("Float : " + Float.MIN_VALUE + " -> " + Float.MAX_VALUE);
			System.out.println("Double : " + Double.MIN_VALUE + " -> " + Double.MAX_VALUE);
		}
		
		/**
		 * BigIntegerのサンプル
		 * longの範囲を超える任意精度の整数値が必要な場合に利用する
		 */
		public static void testBigInteger() {
			BigInteger a  = new BigInteger( "123456789012345678901234567890" );
			BigInteger b  = new BigInteger( "987654321098765432109876543210" );

			BigInteger c;

			//足し算
			c  = a.add( b );
			System.out.println( a + " + " + b + " = " + c );

			//引き算
			c  = a.subtract( b );
			System.out.println( a + " - " + b + " = " + c );

			//掛け算
			c  = a.multiply( b );
			System.out.println( a + " * " + b + " = " + c );

			//割り算
			c  = b.divide( a );
			System.out.println( b + " / " + a + " = " + c );
		}

		/**
		 * BigDecimalのサンプル
		 * doubleの範囲を超える任意精度の実数値が必要な場合に利用する
		 */
		public static void testBigDecimal() {
			//BigDecimalの使い方
			BigDecimal a  = new BigDecimal( "12345678901234567890.12345678901234567890" );
			BigDecimal b  = new BigDecimal( "98765432109876543210.98765432109876543210" );

			BigDecimal c;

			//足し算
			c  = a.add( b );
			System.out.println( a + " + " + b + " = " + c );

			//引き算
			c  = a.subtract( b );
			System.out.println( a + " - " + b + " = " + c );

			//掛け算
			c  = a.multiply( b );
			System.out.println( a + " * " + b + " = " + c );

			//割り算(数値の丸め方を必ず指定すること)
			c  = b.divide( a, BigDecimal.ROUND_HALF_EVEN );
			System.out.println( b + " / " + a + " = " + c );
			
			//小数点以下n桁で切り上げ
			System.out.println("ceil 0 : " + a.setScale(0, RoundingMode.CEILING));
			System.out.println("ceil 1 : " + a.setScale(1, RoundingMode.CEILING));
			System.out.println("ceil 2 : " + a.setScale(2, RoundingMode.CEILING));
			System.out.println("ceil 3 : " + a.setScale(3, RoundingMode.CEILING));
			
			//小数点以下n桁で切り捨て
			System.out.println("floor 0 : " + a.setScale(0, RoundingMode.FLOOR));
			System.out.println("floor 1 : " + a.setScale(1, RoundingMode.FLOOR));
			System.out.println("floor 2 : " + a.setScale(2, RoundingMode.FLOOR));
			System.out.println("floor 3 : " + a.setScale(3, RoundingMode.FLOOR));

			//小数点以下n桁で四捨五入
			System.out.println("round 0 : " + a.setScale(0, RoundingMode.HALF_UP));
			System.out.println("round 1 : " + a.setScale(1, RoundingMode.HALF_UP));
			System.out.println("round 2 : " + a.setScale(2, RoundingMode.HALF_UP));
			System.out.println("round 3 : " + a.setScale(3, RoundingMode.HALF_UP));
		}
	}

	/**
	 * 入力値を読み込む。
	 */
	static int N;
	static int[] S;
	
	static void read(Scanner sc){
        N = sc.nextInt();
        S = new int[N];
        
		for (int i = 0; i < N; i++) {
			S[i] = sc.nextInt();
		}
	}
	
	/**
	 * 問題に回答する。
	 * 
	 * @return ケースごとの回答
	 */
	static String solve(){
		// write answer
		return null;
	}
	
	/**
	 * 入力をパースしてメイン処理に渡す。
	 * 
	 */
	public static void main(String[] args){
        try{
        	String inputFileName = "test.in";
            String outputFileName = inputFileName + "." + new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime()) + ".out";

            File inputFile = new File(inputFileName);
            Scanner sc = new Scanner(inputFile);
            
            FileOutputStream fos = new FileOutputStream(outputFileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos , "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            
            int numOfCases = sc.nextInt();
            for(int caseIndex=1; caseIndex <= numOfCases; caseIndex++){
            	// 問題に合わせて入力値をパースする
            	read(sc);
                // パースした入力値を回答用メソッドに渡す
                String ret = solve();
                
                System.out.println("Case #" + caseIndex + ": " + ret);
                
            	if(caseIndex==numOfCases){
                	bw.write("Case #" + caseIndex + ": " + ret);
            	}else{
                	bw.write("Case #" + caseIndex + ": " + ret + "\n");
            	}
            }
            bw.close();
            osw.close();
            fos.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

	}
}
