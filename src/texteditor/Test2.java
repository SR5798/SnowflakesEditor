package texteditor;
import java.util.*;
public class Test2 {
	public static Scanner sc=new Scanner(System.in);
	public static void main(String args[]) {
		int len, i;
		char ch, ch2;
		System.out.println("Enter String");
		String getText=sc.nextLine();
		
		len = getText.length();
		
		getText=Character.toUpperCase(getText.charAt(0))+getText.substring(1, len);
		
		
		String upcase="";
		
		
		
		for(i=0; i<len-1; i++) {
			
			ch=getText.charAt(i);
			if(i!=len-1) {
							if(ch==' ' && getText.charAt(i+1) !=' ') {
				
				ch2=getText.charAt(i+1);
				upcase=getText.substring(0, i+1)+Character.toUpperCase(ch2)+getText.substring(i+2, len);
			}
		}
		}
		System.out.println(upcase);
	}
}
