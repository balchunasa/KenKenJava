public class KenKenDriver {
	public static void main(String[] args) {
		KenKen kkGame = new KenKen();

		while (!kkGame.isSolved()) {
			System.out.print(kkGame);
			System.out.print("Give row col num: ");
			int row = sc.nextInt();
			int col = sc.nextInt();
			int val = sc.nextInt();
		}
	}
}