package test;


import data.Spieler;

public class namen
{
    String name;
    String name2;
	public static void main(String[] args)
	{
		new namen();
	}
    public namen(){
        final Spieler[] spieler = new Spieler[2];
     
		name = "Erika";
		name2 = "Klaus";
		spieler[0] = new Spieler(name);
		spieler[1] = new Spieler(name2);
		System.out.println("Name Spieler 1: " + spieler[0].getName());
		System.out.println("Name Spieler 2: " + spieler[1].getName());
		
	
		
		
    }
}
