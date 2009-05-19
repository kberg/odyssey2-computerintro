package org.brainkandy.oci.swt.test;

import org.brainkandy.oci.context.IBitmap;
import org.brainkandy.oci.display.Char;
import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.engine.IBuzzer;
import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.IInput;
import org.brainkandy.oci.engine.IOutput;
import org.brainkandy.oci.math.UnsignedByte;
import org.brainkandy.oci.samples.CreepyCrawler;
import org.brainkandy.oci.samples.CreepyCrawler2;
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

public class AdvancedSwtDisplayDressing {

	private final SwtDisplay swtDisplay;

	private boolean echo = false;

	private final IComputer computer;
	private final IContext context;
	private volatile Thread executionThread;

	public AdvancedSwtDisplayDressing(Shell shell, SwtDisplay swtDisplay) {
		setMenu(shell);
		this.swtDisplay = swtDisplay;
		this.computer = new Debugger(shell, swtDisplay);
		this.context = createContext(shell, swtDisplay);
	}

	private Menu createMenu(Shell shell, Menu menuBar, String text) {
		MenuItem header = new MenuItem(menuBar, SWT.CASCADE);
		header.setText(text);
		Menu menu = new Menu(shell, SWT.DROP_DOWN);
		header.setMenu(menu);
		return menu;
	}

	private MenuItem createMenuItem(Menu menu, String text,
			SelectionListener listener) {
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(text);
		if (listener != null) {
			item.addSelectionListener(listener);
		}
		return item;
	}

	private void setMenu(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		Menu fileMenu = createMenu(shell, menuBar, "&File");

		createMenuItem(fileMenu, "&Open...", new SelectionAdapter() {
		});

		Menu testMenu = createMenu(shell, menuBar, "&Test");
		createMenuItem(testMenu, "&Show All", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				showAllPattern();
			}
		});
		createMenuItem(testMenu, "&Clear Screen", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				clearScreen();
			}
		});
		createMenuItem(testMenu, "Toggle &Echo", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				echo = !echo;
			}
		});

		Menu computerMenu = createMenu(shell, menuBar, "&Computer");
		createMenuItem(computerMenu, "&Start", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				startComputer();
			}
		});
		createMenuItem(computerMenu, "&Stop", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				stopComputer();
			}
		});
		createMenuItem(computerMenu, "&Reset", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				resetComputer();
			}
		});

		shell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (echo) {
					char c = Character.toUpperCase(e.character);
					Char ch = Chars.getChar(c);
					swtDisplay.print(ch);
				}
			}
		});
		shell.setMenuBar(menuBar);
	}

	private void showAllPattern() {
		swtDisplay.setPosition(0, 0);
		int j = 0;
		for (int i = 0; i < 1000; i++) {
			swtDisplay.print(Chars.chars[j]);
			j = (j + 1) % Chars.chars.length;
		}
	}

	private void clearScreen() {
		swtDisplay.setPosition(0, 0);
		for (int i = 0; i < 1000; i++) {
			swtDisplay.print(null);
		}
	}

	private IContext createContext(final Shell shell,
			final SwtDisplay swtDisplay) {
		return new IContext() {

			private final IBuzzer buzzer = new IBuzzer() {
				public void buzz() {
					shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							shell.getDisplay().beep();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
						}
					});
				}
			};

			private final IInput input = new IInput() {
				public UnsignedByte read() {
					// return 0;
					throw new RuntimeException("IInput.read not implemented");
				}
			};

			private final IOutput output = new IOutput() {
				private int position;

				public void put(final UnsignedByte datum) {
					shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							try {
								swtDisplay.setPosition(position + 3, 10);
								IBitmap bitmap = Chars.chars[datum.toInteger()];
								swtDisplay.print(bitmap);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}

				public void setPosition(UnsignedByte position) {
					this.position = position.toInteger();
				}
			};

			public IBuzzer getBuzzer() {
				return buzzer;
			}

			public IInput getInput() {
				return input;
			}

			public IOutput getOutput() {
				return output;
			}

		};
	}

	private void startComputer() {
		if (executionThread != null) {
			throw new IllegalStateException("Already running");
		}
		executionThread = new Thread(new Runnable() {
			public void run() {
				try {
					computer.run(context);
					System.err.println("Foo");
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		executionThread.start();
	}

	private void stopComputer() {
		if (executionThread == null) {
			throw new IllegalStateException("Not running");
		}
		computer.halt();
	}

	private void resetComputer() {
		computer.halt();
		computer.setProgram(new CreepyCrawler2().getProgram());
	}
}
