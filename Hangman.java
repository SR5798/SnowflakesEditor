import java.util.*;
public class Hangman {
	public  Scanner sc=new Scanner(System.in);
	public  int ia=0, i,j,len=0, won, clc=1, points=10;
    public  char go, inpt, ch;
    public  String word="", DispWord="";
    public  boolean cv=false, cl=true;
    public  String clues[]=new String[3];

	public  void Start() {
		System.out.println("HangMan - 3 Clues, 6 Attempts");
        System.out.println("Enter the word");
        word=sc.nextLine();
        word=word.toUpperCase();
        for(i=0; i<3; i++){
            System.out.println("Enter the clues (3 Clues)");
            clues[i]=sc.nextLine();
        }
    }
	
    public  void cl(){
        for(i=0; i<=30; i++) System.out.println();
    }

    public  boolean cVowel(){
        if(inpt=='A' || inpt=='E' || inpt=='I' || inpt=='O' || inpt=='U'){
            cv=true;
        }
        else cv=false;
        return cv;
    }

    public  boolean cSymbol(){
        if(inpt>='A' && inpt<='Z' || inpt=='#'){
            cl=false;
        }
        else cv=false;
        return cl;
    }

    public  String removeCons(){
        String remcon="";
        
        for(i=0; i<len; i++){
            ch=word.charAt(i);
            
            if(ch=='A' || ch=='E' || ch=='I' || ch=='O' || ch=='U') {
            	remcon=remcon+ch;
            }
            else remcon=remcon+'_';
        }
        
        return remcon;
    }

    public  void addLet(){
        boolean cv2, cl2; int Wrong=0;
        
        cv2=cVowel();
        cl2=cSymbol();
        if(cv2==false && cl2==false){
            for(i=0; i<len; i++) {
            	if(word.charAt(i)==inpt) {
            		DispWord=DispWord.substring(0, i)+inpt+DispWord.substring(i+1);
            		Wrong=1;
            	}
            }
            if(inpt=='#') {
        		if(clc>=3) {
        			System.out.println("Max Clues Reached");
        		}
        		else
        			clc++;
        		Wrong=1;
        	}
            if(Wrong==0) {
            	ia++;
            }
        }
        else System.out.println("Vowel/Symbols/NotAlphaCharacters found\nERROR REPORT:\nIsA_Vowel: "+cv+"\nIsA_NonAlphaCharacter: "+cl);
        win();
    }

	public  void GO() {
		System.out.println("Chances Left: 0\n"
				+ " O \n"
				+ "/|\\\n"
				+ " | \n"
				+ "/ \\\n"+"Game Over");
	}
	
	public  void h1() {
		System.out.println("Chances Left: "+(6-ia)+"\n"
				+ " O \n"
				);
	}
	
	public  void h2() {
		System.out.println("Chances Left: "+(6-ia)+"\n"
				+ " O \n"
				+ "/\n"
				);
	}
	
	public  void h3() {
		System.out.println("Chances Left: "+(6-ia)+"\n"
				+ " O \n"
				+ "/ \\\n"
				);
	}
	
	public  void h4() {
		System.out.println("Chances Left: "+(6-ia)+"\n"
				+ " O \n"
				+ "/|\\\n"
				+ " | \n"
				);
	}
	
	public  void h5() {
		System.out.println("Chances Left: "+(6-ia)+"\n"
				+ " O \n"
				+ "/|\\\n"
				+ " | \n"
				+ "/ ");
	}
	
	public  void win() {
		
		int winvar=0;
		for(i=0; i<len; i++) {
			if(DispWord.charAt(i)=='_') {
				winvar=1;
			}
		}
		
		if(winvar==0) {
			won=1;
			System.out.println("WORD="+DispWord);
			points-=(clc-1)+ia;
			System.out.println("CONGRADULATIONS!\nYOU WIN!"+"\nPoints Gained="+points+"/10");
		}
	}
	
	public void RG() {
		
		Start();
		len=word.length();
		cl();
		DispWord=removeCons();
		cl();
		while(ia!=6 && won!=1) {
			cl();
			switch (ia){
				case 1: h1(); break;
				case 2: h2(); break;
				case 3: h3(); break;
				case 4: h4(); break;
				case 5: h5(); break;
				default: System.out.println("Wow! You didn't guess anything wrong!");
			}
			System.out.println("Word="+DispWord+"\nIA="+ia);
			for(i=0; i<clc; i++) {
				System.out.println("Clue:"+clues[i]);
			}
			System.out.println("Enter the letter");
			if(clc<3) System.out.println("Enter # to get an extra clue for 1 point");
			inpt=sc.next().charAt(0);
			inpt=Character.toUpperCase(inpt);
			addLet();
			
		}
		if(ia==6) {
			GO();
			System.out.println("Sorry! \nYou lost!");
			System.out.println("The Word was :"+word);
		}
		
	}
	
	public static void main(String args[]) {
		Hangman h=new Hangman();
		h.RG();
	}
}