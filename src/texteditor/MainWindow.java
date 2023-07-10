package texteditor;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;

public class MainWindow extends javax.swing.JFrame{


		/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Color mbg= new Color(0xFFFFFF);
	public ImageIcon icon= new ImageIcon("res/c_icon.png");
	public ImageIcon open= new ImageIcon("res/open.png");
	public ImageIcon save= new ImageIcon("res/save.png");
	public ImageIcon tcut= new ImageIcon("res/cut.png");
	public ImageIcon tcopy= new ImageIcon("res/copy.png");
	public ImageIcon tpaste= new ImageIcon("res/paste.png");
	public ImageIcon boticon= new ImageIcon("res/boticon.png");
	public ImageIcon Aa= new ImageIcon("res/Aa.png");
	public ImageIcon AA= new ImageIcon("res/UAA.png");
	public ImageIcon aa= new ImageIcon("res/Laa.png");
	public ImageIcon moon= new ImageIcon("res/moon.png");
	public ImageIcon exitapp= new ImageIcon("res/exit.png");
	public ImageIcon undo= new ImageIcon("res/undo.png");
	public ImageIcon redo= new ImageIcon("res/redo.png");
	public JTextPane textPane = new JTextPane();
	public String selectedText, getText, fileName, text, dictionaries = "dictionaries/";
	public UndoManager undoManager = new UndoManager();
	public JFileChooser jfile_open=new JFileChooser("Open");
	public File file;
	public JMenuItem file_save = new JMenuItem("Save");
	public JButton bar_save=new JButton();
	public JButton undoButton = new JButton();
    public JButton redoButton = new JButton();
	public static int i, j;
	public static int len;
	public static char ch;
	public static char ch2;
	public int ifile=1;
	public static String Laf="com.formdev.flatlaf.themes.FlatMacLightLaf";


	//MAIN FUNCTIONS
		public void initComponents() {
			System.out.println("initComponents Responding");
			SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
			ClassLoader classLoader = MainWindow.class.getClassLoader();
			URL url = classLoader.getResource("dictionaries/dictionary_en.ortho");
			SpellChecker.registerDictionaries(url, "en", "en");

			SpellChecker.register(textPane);
	        SpellCheckerOptions sco = new SpellCheckerOptions();
	        sco.setCaseSensitive(false);
	        sco.setSuggestionsLimitMenu(15);
	        sco.setIgnoreAllCapsWords(true);
	        sco.setIgnoreWordsWithNumbers(true);
	        sco.setIgnoreCapitalization(true);
	        SpellChecker.getOptions();


		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setSize(1300, 800);
        setTitle("SnowflakesEditor");
        setIconImage(icon.getImage());


        JScrollPane textScroll=new JScrollPane(textPane);
        getContentPane().add(textScroll, BorderLayout.CENTER);

        sco.setSuggestionsLimitMenu(5);

        //SPELL CHECKER POPUP
        JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
        textPane.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    popup.show(textPane, e.getX(), e.getY());
                }
            }
        });


        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);

        JToggleButton tglTheme = new JToggleButton();
        tglTheme.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if (tglTheme.isSelected()) {
                    try {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                SwingUtilities.updateComponentTreeUI(MainWindow.this);
            }
        });
        toolBar.add(tglTheme);
        tglTheme.setIcon(moon);
        tglTheme.setToolTipText("Dark Mode");

        JButton cut = new JButton("");
        cut.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    textPane.replaceSelection("");
                }

        	}
        });
        cut.setToolTipText("Cut");

        cut.setIcon(tcut);
        toolBar.add(cut);

        JButton copy = new JButton("");
        copy.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

        	}
        });
        copy.setToolTipText("Copy");

        copy.setIcon(tcopy);
        toolBar.add(copy);

        JButton paste = new JButton("");
        paste.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();

                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);
                    if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {

                            try {
								text = (String) contents.getTransferData(DataFlavor.stringFlavor);
							} catch (UnsupportedFlavorException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        textPane.replaceSelection(text);
                    }


        	}
        });
        paste.setToolTipText("Paste");

        paste.setIcon(tpaste);
        toolBar.add(paste);

      //UNDO&REDO
        Document document = textPane.getDocument();

        document.addUndoableEditListener(undoManager);

        undoButton.addActionListener(e -> {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        });

        redoButton.addActionListener(e -> {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        });
        undoButton.setIcon(undo);
        redoButton.setIcon(redo);
        undoButton.setToolTipText("Undo");
        redoButton.setToolTipText("Redo");
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        //UNDO KEY STROKES
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                }

                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                }
            }
        });
//end of undo redo

        JButton wordUpcase = new JButton("");
        wordUpcase.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		selectedText = textPane.getSelectedText();
        		if (selectedText != null) {
        			System.out.println("Aa ACTIVATED");
        			text=MainWindow.upCase(selectedText);
        			textPane.replaceSelection(text);
                }

        	}
        });
        wordUpcase.setToolTipText("Capitalize First Letter");

        wordUpcase.setIcon(Aa);
        toolBar.add(wordUpcase);


        //RC MENU
        JPopupMenu rightClickMenu = new JPopupMenu();
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.cut();
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.copy();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.paste();
            }
        });

        rightClickMenu.add(cutMenuItem);
        rightClickMenu.add(copyMenuItem);
        rightClickMenu.add(pasteMenuItem);

        textPane.setComponentPopupMenu(rightClickMenu);
        //END OF RC MENU

	}

		public MainWindow() {

		JMenuBar menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);

		JMenu menu_file = new JMenu("File");
		menu_bar.add(menu_file);

		JMenuItem file_open = new JMenuItem("Open");
		//OPEN FILE
		file_open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ifile= jfile_open.showOpenDialog(null);
				if(ifile== JFileChooser.APPROVE_OPTION) {
					file_save.setEnabled(true);
					bar_save.setEnabled(true);
				file=new File(jfile_open.getSelectedFile().toString());
				fileName=file.getName();
				setTitle("Snowflakes Editor - "+fileName);
				try {
					FileReader reader = new FileReader(file);
					BufferedReader b_reader= new BufferedReader(reader);
					try {
						textPane.read(b_reader, null);
						b_reader.close();
						textPane.requestFocus();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else {
					JOptionPane.showMessageDialog(null, "Snowflaked Editor: Uhm, I didn't get any file! \n Please try again! \n ERROR CODE: NOFILE001", "ERROR - No file selected", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//end of OPEN FILE

		file_open.setIcon(open);
		menu_file.add(file_open);

		//FILE SAVE

		file_save.setEnabled(false); //default state
		file_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ifile==JFileChooser.APPROVE_OPTION) {
				try {
			        FileWriter writer = new FileWriter(file);
			        writer.write(textPane.getText());
			        writer.close();
			    } catch (IOException e1) {
			        e1.printStackTrace();
			    }
				}
				else {
					JOptionPane.showMessageDialog(null, "Snowflaked Editor: Uhm, no file is open! \n Please try again! \n ERROR CODE: NOFILE002", "ERROR - No file opened", JOptionPane.ERROR_MESSAGE);
				}
				}

		});

		file_save.setIcon(save);
		menu_file.add(file_save);


		bar_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
		            Robot robot = new Robot();
		            robot.keyPress(KeyEvent.VK_CONTROL);
		            robot.keyPress(KeyEvent.VK_S);
		            robot.keyRelease(KeyEvent.VK_S);
		            robot.keyRelease(KeyEvent.VK_CONTROL);
		        } catch (AWTException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		menu_bar.add(bar_save);
		bar_save.setIcon(save);
		bar_save.setEnabled(false);



		//SAVE AS
		JMenuItem file_saveas = new JMenuItem("Save As");
		file_saveas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser Jfile_save = new JFileChooser();
				//FILTER TO TEXT FILE AND DEFAULT FILE NAME
				Jfile_save.setSelectedFile(new File("TextFile.txt"));
				Jfile_save.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
				int result = Jfile_save.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					file_save.setEnabled(true);
					bar_save.setEnabled(true);
				    file = Jfile_save.getSelectedFile();
				    ifile=0;
				    fileName=file.getName();
					setTitle("Snowflakes Editor - "+fileName);
				    try {
				        FileWriter writer = new FileWriter(file);
				        writer.write(textPane.getText());
				        writer.close();
				    } catch (IOException e2) {
				        e2.printStackTrace();
				    }
				}
				}
		});

		file_saveas.setIcon(save);
		menu_file.add(file_saveas);

		JMenuItem file_exit = new JMenuItem("Exit");
		file_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			}
		});
		file_exit.setIcon(exitapp);
		menu_file.add(file_exit);

		//SHORTCUTS
		file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		file_saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		file_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));


        System.out.println("MainWindow Constructor Responding - STAGE 1 - 3");
        initComponents(); //initComponents Call
    }

	public static void main(String args[]) {
		FlatLaf.registerCustomDefaultsSource("style");
		try {
			UIManager.setLookAndFeel(Laf);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MainWindow main Function responding - STAGE 1 - 2");
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new MainWindow().setVisible(true);
            }
        });
	}

	//GENERAL FUNCTIONS
	public static String upCase(String getText) {

		int len = getText.length();
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
		System.out.println("getText");
		return upcase;
	}

}