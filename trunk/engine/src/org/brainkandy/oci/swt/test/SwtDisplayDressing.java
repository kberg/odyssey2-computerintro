package org.brainkandy.oci.swt.test;

import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class SwtDisplayDressing {

	private final SwtDisplay swtDisplay;

	private Menu menuBar;
	private Menu fileMenu;
	private Menu testMenu;
	private Menu runMenu;

	private MenuItem fileOpenItem;
	private MenuItem testAllItem;
	private MenuItem testClearItem;
	private MenuItem testEchoItem;
	private MenuItem runRunMenuItem;

	private boolean echo = false;

	public SwtDisplayDressing(Shell shell, SwtDisplay swtDisplay) {
		setMenu(shell);
		this.swtDisplay = swtDisplay;
	}

	private Menu createMenu(Shell shell, Menu menuBar, String text) {
		MenuItem header = new MenuItem(menuBar, SWT.CASCADE);
		header.setText(text);
		Menu menu = new Menu(shell, SWT.DROP_DOWN);
		header.setMenu(menu);
		return menu;
	}

	private MenuItem createMenuItem(Menu menu, String text, SelectionListener listener) {
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(text);
		if (listener != null) {
		  item.addSelectionListener(listener);
		}
		return item;
	}

	private void setMenu(Shell shell) {
		menuBar = new Menu(shell, SWT.BAR);
		fileMenu = createMenu(shell, menuBar, "&File");
		testMenu = createMenu(shell, menuBar, "&Test");
		runMenu = createMenu(shell, menuBar, "&Run");
	
		fileOpenItem = createMenuItem(fileMenu, "&Open...", new FileOpenItemListener());
		testAllItem = createMenuItem(testMenu, "&Show All", new TestAllItemListener());
		testClearItem = createMenuItem(testMenu, "&Clear", new TestClearListener());
		testEchoItem = createMenuItem(testMenu, "Toggle &Echo", new TestEchoListener());
		runRunMenuItem = createMenuItem(runMenu, "&Run", null);

		shell.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				System.out.println(e);
				if (echo) {
					char c = Character.toUpperCase(e.character);
					for (int i = 0; i < Chars.asciiMap.length; i++) {
						if (c == Chars.asciiMap[i]) {
							swtDisplay.print(Chars.chars[i]);
						}
					}
				}
      }
		});
		shell.setMenuBar(menuBar);
	}

	class TestAllItemListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			swtDisplay.setPosition(0, 0);
			int j = 0;
			for (int i = 0; i < 1000; i++) {
				swtDisplay.print(Chars.chars[j]);
				j = (j + 1) % Chars.chars.length;
			}
		}
	}

	class TestClearListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			swtDisplay.setPosition(0, 0);
			for (int i = 0; i < 1000; i++) {
				swtDisplay.print(null);
			}
		}
	}

	class TestEchoListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			echo = !echo;
		}
	}

	class FileOpenItemListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
		}
	}
}
