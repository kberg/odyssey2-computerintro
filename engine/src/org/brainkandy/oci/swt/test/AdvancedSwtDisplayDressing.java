package org.brainkandy.oci.swt.test;

import java.util.ArrayList;
import java.util.List;

import org.brainkandy.oci.context.IBitmap;
import org.brainkandy.oci.display.Char;
import org.brainkandy.oci.display.Chars;
import org.brainkandy.oci.engine.DebugCode;
import org.brainkandy.oci.engine.IBuzzer;
import org.brainkandy.oci.engine.IComputer;
import org.brainkandy.oci.engine.IComputerListener;
import org.brainkandy.oci.engine.IContext;
import org.brainkandy.oci.engine.IDebugger;
import org.brainkandy.oci.engine.IInput;
import org.brainkandy.oci.engine.IOutput;
import org.brainkandy.oci.engine.impl.Computer;
import org.brainkandy.oci.engine.impl.Debugger;
import org.brainkandy.oci.math.Bytes;
import org.brainkandy.oci.math.UnsignedByte;
import org.brainkandy.oci.samples.Sample;
import org.brainkandy.oci.swt.SwtDisplay;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class AdvancedSwtDisplayDressing {

  private final SwtDisplay swtDisplay;

  private boolean echo = false;

  private final IComputer computer;
  private final IContext context;
  private final DebuggerView debugView;
  private volatile Thread executionThread;
  private final Shell shell;
  private String program;

  private IDebugger debugger;

  public AdvancedSwtDisplayDressing(Shell shell, SwtDisplay swtDisplay) {
    setMenu(shell);
    this.shell = shell;
    this.swtDisplay = swtDisplay;
    this.computer = new Computer();
    this.debugger = new Debugger(computer);
    this.debugView = new DebuggerView(shell, swtDisplay);
    this.context = createContext(shell, swtDisplay);
    computer.setListener(new ComputerListener());
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
    createMenuItem(computerMenu, "&Enter Program", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        enterProgram();
      }
    });
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
    createMenuItem(computerMenu, "&Step", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {
        debugger.step();
      }
    });
    createMenuItem(computerMenu, "&Pause", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {
        debugger.pause();
      }
    });
    createMenuItem(computerMenu, "Breakpoints", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {
        setBreakpoints();
      }
    });
    Menu samplesMenu = createMenu(shell, menuBar, "&Samples");
    for (Sample sample : Sample.values()) {
      createSample(samplesMenu, sample.getName(), sample.getSource());
    }

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

  private void createSample(Menu samplesMenu, String title, final String program) {
    createMenuItem(samplesMenu, title, new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {
        resetComputer(program);
      }
    });
  }

  private void showAllPattern() {
    swtDisplay.setPosition(0, 0);
    int j = 0;
    for (int i = 0; i < 1000; i++) {
      swtDisplay.print(Chars.getChar(j));
      j = (j + 1) % Chars.getChars().length;
    }
  }

  private void clearScreen() {
    swtDisplay.setPosition(0, 0);
    for (int i = 0; i < 1000; i++) {
      swtDisplay.print(null);
    }
  }

  private IContext createContext(final Shell shell, final SwtDisplay swtDisplay) {

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
          final UnsignedByte[] holder = new UnsignedByte[1];

          final KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
              int idx = Chars.getCharIndex(e.character);
              if (idx >= 0) {
                holder[0] = UnsignedByte.get(idx);
              }
            }
          };

          shell.getDisplay().syncExec(new Runnable() {
            public void run() {
              shell.addKeyListener(keyAdapter);
            }
          });

          while (holder[0] == null && computer.isRunning()) {
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }
          shell.getDisplay().syncExec(new Runnable() {
            public void run() {
              shell.removeKeyListener(keyAdapter);
            }
          });
          return holder[0];
        }
      };

      private final IOutput output = new IOutput() {
        private int position;

        public void put(final UnsignedByte datum) {
          shell.getDisplay().syncExec(new Runnable() {
            public void run() {
              try {
                swtDisplay.setPosition(position, 10);
                int integer = datum.toInteger();
                IBitmap bitmap = Chars.getChar(integer);
                swtDisplay.print(bitmap);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
        }

        public void setPosition(UnsignedByte position) {
          this.position = position.bcdGet();
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

  private void enterProgram() {
    shell.getDisplay().syncExec(new Runnable() {
      public void run() {
        InputDialog dialog = new InputDialog(shell, "Enter program", "Enter your program here.",
            program, null);
        if (dialog.open() == InputDialog.OK) {
          AdvancedSwtDisplayDressing.this.program = dialog.getValue();
          resetComputer(program);
        }
      }
    });
  }

  private void setBreakpoints() {
    shell.getDisplay().syncExec(new Runnable() {
      public void run() {
        int[] breakpoints = debugger.getBreakpoints();
        StringBuilder sb = new StringBuilder();
        for (int i : breakpoints) {
          if (sb.length() > 0) {
            sb.append(", ");
          }
          sb.append(i);
        }
        InputDialog dialog = new InputDialog(shell, "Enter breakpoints",
            "Enter breakpoints, separated by commas",
            sb.toString(), null);
        if (dialog.open() == InputDialog.OK) {
          List<Integer> list = new ArrayList<Integer>();
          for(String entry : dialog.getValue().split(" *, *")) {
            Integer i = Integer.parseInt(entry.trim());
            list.add(i);
          }
          debugger.clearBreakpoints();
          for (Integer integer : list) {
            debugger.addBreakpoint(integer);
          }
        }
      }
    });        
  }

  private void startComputer() {
    if (executionThread != null) {
      throw new IllegalStateException("Already running");
    }
    executionThread = new Thread(new Runnable() {
      public void run() {
        try {
          computer.setContext(context);
          computer.reset();
          debugger.reset();
          debugger.run();
        } catch (Throwable t) {
          t.printStackTrace();
        }
        executionThread = null;
      }
    }, "Computer execution thread");
    executionThread.start();
  }

  private void stopComputer() {
    if (executionThread == null) {
      throw new IllegalStateException("Not running");
    }
    computer.halt();
  }

  private void resetComputer(String program) {
    computer.halt();
    // Should wait for halt, actually.
    computer.reset();
    this.program = program;
    computer.setProgram(Bytes.codify(program));
  }

  private class ComputerListener implements IComputerListener {
    public void announce(DebugCode code, Object data) {
      switch (code) {
      case HALT:
        debugView.showRunningState("Stopped");
        break;

      case RUN:
        debugView.showRunningState("Running");
        break;

      case ACCUM_SET:
        debugView.showAccum(computer.getAccumulator());
        break;

      case PROGRAM_COUNTER_CHANGE:
        debugView.showProgramCounter(computer.getProgramCounter());
        break;

      case REGISTER_SET:
        int i = (Integer) data;
        debugView.showReigster(i, computer.getRegister(i));
        break;
      }
    }
  }
}
