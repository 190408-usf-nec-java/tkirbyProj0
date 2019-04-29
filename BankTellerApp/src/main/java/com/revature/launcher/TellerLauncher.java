package com.revature.launcher;

import com.revature.views.MainMenu;
import com.revature.views.View;

public class TellerLauncher {
	public static void main(String[] args) {
		
		View view = new MainMenu();
		while(view != null) {
			view = view.printOptions();
		}
		
		System.out.println("Thanks bye!");
	}
}
