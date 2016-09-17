import javax.swing.JOptionPane;

public class Exceptions {
	public static void main(String[] args) {
		String pesel = askUserForPesel();
	}

	//metoda powinna pytac uzytkownika o pesel tak dlugo az bedzie to wartosc poprawna
	private static String askUserForPesel() {
		String pesel = null;
		boolean valid = false;
		while(!valid){
			//za pomoc¹ wikipedii dowiedz sie jak wygl¹da poprawany pesel
			//1) musi mieæ 11 znakow <- rzuæ InvalidPeselLengthException
			//2) musi mieæ same cyfry <- rzuæ InvalidCharactersInPeselException
			//3) musi zgadzaæ siê data w numerze pesel  <- InvalidDateInPeselException
			//4) musi byæ poprawna suma kontrolona (wiki) <- InvalidControlSumException

			//4 rodzaje wyjatków walidacji i chcamy ka¿dego z nich z³apaæ osobno
			try {
				pesel = JOptionPane.showInputDialog("Wprowadz pesel");
				String month = pesel.substring(4, 6);
				System.out.println(month);
				int monthNr = Integer.parseInt(month);
				isValid(pesel);
				//cos z tymi catchami bedzie inaczej
			} catch (CustomException e) {
				//cos
				//np: jak siê pjawi invvalid pesel length exception to wypiszesz komunikat ze: zla ilosc znakow
				e.printStackTrace();
			}
		}
		return pesel;
	}

	private static void isValid(String pesel)throws CustomException{
		

		if(pesel.length()!=11){
			throw new InvalidPeselLengthException("Incorrect String length");
			//	System.out.println("Incorrect String length");
		}else{
			String year = pesel.substring(0, 1);
			int yearNr = Integer.parseInt(year);
			String month = pesel.substring(2, 4);
			int monthNr = Integer.parseInt(month);
			String day = pesel.substring(4, 6);
			int dayNr = Integer.parseInt(day);
		
		for (int i = 0; i < pesel.length(); i++) {
			if (!Character.isDigit(pesel.charAt(i)))
				throw new InvalidCharactersInPeselException("Not all values are numeric!");
		}
		if (!(monthNr > 0 && monthNr < 13)) {
			throw new InvalidDateInPeselException("Incorrect month");
		}
		if (!((dayNr >0 && dayNr < 32) && (monthNr == 1 || monthNr == 3 || monthNr == 5 ||
				monthNr == 7 || monthNr == 8 || monthNr == 10 || monthNr == 12))) {
			throw new InvalidDateInPeselException("Incorrect day");
		}
		else if (!((dayNr >0 && dayNr < 31) && (monthNr == 4 || monthNr == 6 || monthNr == 9 ||
				monthNr == 11))) {
			throw new InvalidDateInPeselException("Incorrect day");
		}
		else if (!((dayNr >0 && dayNr < 30 && leapYear(yearNr)) ||
				(dayNr >0 && dayNr < 29 && !leapYear(yearNr)))) {
			throw new InvalidDateInPeselException("Incorrect day");
		}
		int sum =0;
		int[] weigth = {1, 3, 7, 9, 1, 3, 7 ,9 ,1 ,3};
		for (int i = 0; i < 10; i++)
			sum += Integer.parseInt(pesel.substring(i, i+1)) * weigth[i];
			int controlNr = Integer.parseInt(pesel.substring(10, 11));
			sum %= 10;
			sum = 10 - sum;
			sum %= 10;
			if (sum != controlNr){
				throw new InvalidControlSumException("Invalid control sum");
			}
		}
	}
	private static boolean leapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return true;
		else
			return false;
	}
}
