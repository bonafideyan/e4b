 
package com.github.e4b.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;

public class Hello {
	@Execute
	public void execute() {
	  MessageDialog.openInformation(null, "Hello", "World");
	}
		
}